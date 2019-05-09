package org.squat_team.vis.connector.protocols;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;
import org.squat_team.vis.connector.server.ConnectorService;
import org.squat_team.vis.data.daos.ProjectDao;
import org.squat_team.vis.data.data.Level;
import org.squat_team.vis.data.data.Project;
import org.squat_team.vis.util.ResponseIdentifier;

import lombok.extern.java.Log;

/**
 * This protocol handles an incoming request for the candidates that should be
 * optimized in the next level of search. <br/>
 * It is the counterpart to {@link LevelResponseClientProtocol}.
 */
@Log
public class LevelResponseServerProtocol extends AbstractServerProtocol {
	private int levelNumber;
	private List<Long> candidateIds;

	/**
	 * Creates a new protocol.
	 * 
	 * @param in               the connection from client to server.
	 * @param out              the connection from server to client.
	 * @param connectorService Provides daos for the import
	 * @param projectConnector Specifies the project the import belongs to
	 * 
	 */
	public LevelResponseServerProtocol(ObjectInputStream in, ObjectOutputStream out, ConnectorService connectorService,
			ProjectConnector projectConnector) {
		super(in, out, connectorService, projectConnector);
	}

	@Override
	public void execute() throws ProtocolFailure, IOException, InvalidRequestException {
		receive();
		prepareResponse();
		respond();
	}

	@Override
	public IPostProtocolHandler getPostProtocolHandler() {
		return new LevelResponsePostProtocolHandler(connectorService, projectConnector);
	}

	/**
	 * Receives the data from the client.
	 * 
	 * @throws ProtocolFailure         if the actual communication deviates from the
	 *                                 protocol.
	 * @throws IOException             if an error occurs because of the
	 *                                 communication.
	 * @throws InvalidRequestException if the received objects are of an unexpected
	 *                                 type.
	 */
	private void receive() throws ProtocolFailure, IOException, InvalidRequestException {
		this.levelNumber = receive(Integer.class);
	}

	/**
	 * Gets the requested response. The thread might wait until it is available.
	 * 
	 * @throws InvalidRequestException if the received data violates constraints.
	 */
	private synchronized void prepareResponse() throws InvalidRequestException {
		boolean responseAlreadyThere;
		// lock so there will be no response notification before the thread is actually
		// waiting
		synchronized (connectorService.getResponseManager()) {
			responseAlreadyThere = tryToFindResponses();
			if (!responseAlreadyThere) {
				waitForResponse();
			}
		}
		if (!responseAlreadyThere) {
			tryToFindResponses();
		}
	}

	/**
	 * Waits until the response is available.
	 */
	private void waitForResponse() {
		connectorService.getResponseManager()
				.waitForResponse(new ResponseIdentifier(projectConnector.getProjectId(), levelNumber), this);
		try {
			this.wait();
		} catch (InterruptedException e) {
			log.log(java.util.logging.Level.WARNING, "Interrupted waiting thread", e);
		}
	}

	/**
	 * Tries to find the response in the database. If found, stores it in
	 * {@link #candidateIds}.
	 * 
	 * @return true if response is found, false if not.
	 * @throws InvalidRequestException if the requested project or level does not
	 *                                 exist.
	 */
	private boolean tryToFindResponses() throws InvalidRequestException {
		Project project = findRequestedProject(projectConnector);
		checkProjectExists(project);
		checkLevelExists(project);
		return tryToGetSelectedCandidatesFromLevel(project);
	}

	/**
	 * Finds the requested project in the database.
	 * 
	 * @param requestedProjectConnector the project to find
	 * @return the project or null if not found
	 */
	private Project findRequestedProject(ProjectConnector requestedProjectConnector) {
		ProjectDao projectDao = connectorService.getProjectDao();
		return projectDao.find(requestedProjectConnector.getProjectId());
	}

	/**
	 * Checks that the project exists, otherwise throws an exception.
	 * 
	 * @param project the project to check.
	 * @throws InvalidRequestException if project does not exist.
	 */
	private void checkProjectExists(Project project) throws InvalidRequestException {
		if (project == null) {
			throw new InvalidRequestException("Project does not exist");
		}
	}

	/**
	 * Tries to find the candidates selected by the user for the next level. Sets
	 * them to {@link #candidateIds} if found.
	 * 
	 * @param project the project to search in.
	 * @return true if found, false if not.
	 */
	private boolean tryToGetSelectedCandidatesFromLevel(Project project) {
		Level level = project.getLevels().get(levelNumber);
		if (level.isCandidatesSelected()) {
			candidateIds = level.getSelectedCandidates();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Assures that the requested level actually exists.
	 * 
	 * @param project the project to search in
	 * @throws InvalidRequestException if the level does not exist.
	 */
	private void checkLevelExists(Project project) throws InvalidRequestException {
		List<Level> levels = project.getLevels();
		boolean levelNumberExists = levels.size() - 1 >= levelNumber;
		if (!levelNumberExists) {
			throw new InvalidRequestException("Level does not exist yet");
		}
	}

	/**
	 * Send response to the client.
	 * 
	 * @throws IOException if an error occurs because of the communication.
	 */
	private void respond() throws IOException {
		send(candidateIds);
	}

}
