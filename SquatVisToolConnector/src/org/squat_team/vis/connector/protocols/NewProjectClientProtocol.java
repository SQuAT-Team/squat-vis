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

import lombok.NonNull;

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
	public NewProjectClientProtocol(@NonNull CProject project, @NonNull CToolConfiguration configuration,
			@NonNull CGoal goal) {
		super();
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
			throw new ConnectionFailure("A problem in the communication occurred", e);
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
