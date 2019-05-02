package org.squat_team.vis.connector.protocols;

import java.io.IOException;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.Message;
import org.squat_team.vis.connector.MessageType;
import org.squat_team.vis.connector.data.CGoal;
import org.squat_team.vis.connector.data.CProject;
import org.squat_team.vis.connector.data.CToolConfiguration;
import org.squat_team.vis.connector.exceptions.ConnectionFailure;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;

/**
 * A protocol that initializes a new project. Data has to be pushed to the
 * project with {@link NewLevelClientProtocol} after the project has been
 * created.
 */
public class NewProjectClientProtocol extends AbstractClientProtocol<ProjectConnector> {
	private CProject project;
	private CToolConfiguration configuration;
	private CGoal goal;

	/**
	 * Initializes the protocol.
	 * 
	 * @param project       the project to create.
	 * @param configuration the configuration of the used optimization tool.
	 * @param goal          the goals that are set for this project.
	 */
	public NewProjectClientProtocol(CProject project, CToolConfiguration configuration, CGoal goal) {
		super();
		if (project == null) {
			throw new IllegalArgumentException("Provided project must not be null");
		}
		if (goal == null) {
			throw new IllegalArgumentException("Provided goals must not be null");
		}
		this.project = project;
		this.configuration = configuration;
		this.goal = goal;
	}

	@Override
	protected ProjectConnector executeProtocol() throws ProtocolFailure, InvalidRequestException, ConnectionFailure {
		ProjectConnector projectConnector = null;
		try {
			sendRequests();
			projectConnector = receive(ProjectConnector.class);
		} catch (IOException e) {
			throw new ConnectionFailure("", e);
		}
		return projectConnector;
	}

	private void sendRequests() throws IOException {
		Message request = new Message(MessageType.REQUEST_NEW_PROJECT, null);
		send(request);
		send(project);
		send(configuration);
		send(goal);
	}

}
