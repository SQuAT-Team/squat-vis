package org.squat_team.vis.connector.protocols;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;
import org.squat_team.vis.connector.server.ConnectorService;
import org.squat_team.vis.data.daos.ProjectDao;
import org.squat_team.vis.data.daos.StatusLogDao;
import org.squat_team.vis.data.data.Project;
import org.squat_team.vis.data.data.Status;
import org.squat_team.vis.data.data.StatusLog;

/**
 * This protocol handles an incoming request to terminate an existing project.
 * <br/>
 * It is the counterpart to {@link ProjectTerminatedClientProtocol}.
 */
public class ProjectTerminatedServerProtocol extends AbstractSimpleServerProtocol {
	private ProjectDao projectDao;
	private StatusLogDao statusLogDao;

	/**
	 * Creates a new protocol.
	 * 
	 * @param in               the connection from client to server.
	 * @param out              the connection from server to client.
	 * @param connectorService Provides daos for the import
	 * @param projectConnector Specifies the project the import belongs to
	 */
	public ProjectTerminatedServerProtocol(ObjectInputStream in, ObjectOutputStream out,
			ConnectorService connectorService, ProjectConnector projectConnector) {
		super(in, out, connectorService, projectConnector);
	}

	@Override
	public IPostProtocolHandler getPostProtocolHandler() {
		return new ProjectTerminatedPostProtocol(connectorService, projectConnector);
	}

	@Override
	protected void receive() throws ProtocolFailure, IOException, InvalidRequestException {
		// no further information to receive
	}

	@Override
	protected void transform() throws InvalidRequestException {
		findDaos();
		Project project = findProject();
		Status projectStatus = project.getStatus();
		projectStatus.notifyTerminated();
		update(project);
		update(project.getStatus().getStatusLog());
	}

	/**
	 * Finds the project that should be terminated.
	 * 
	 * @return the project
	 * @throws InvalidRequestException if project does not exist
	 */
	private Project findProject() throws InvalidRequestException {
		long projectId = projectConnector.getProjectId();
		Project project = projectDao.find(projectId);
		if (project != null) {
			return project;
		} else {
			throw new InvalidRequestException("Project with id " + projectId + " could not be found!");
		}
	}

	/**
	 * Finds the required DAOs.
	 */
	private void findDaos() {
		projectDao = connectorService.getProjectDao();
		statusLogDao = connectorService.getStatusLogDao();
	}

	/**
	 * Updates the project in the database.
	 * 
	 * @param project the project to update
	 */
	private void update(Project project) {
		projectDao.update(project);
	}

	/**
	 * Updates the status log in the database.
	 * 
	 * @param statusLog the project to update
	 */
	private void update(StatusLog statusLog) {
		statusLogDao.update(statusLog);
	}

}
