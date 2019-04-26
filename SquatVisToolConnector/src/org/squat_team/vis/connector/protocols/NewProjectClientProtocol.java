package org.squat_team.vis.connector.protocols;

import java.io.IOException;

import org.squat_team.vis.connector.Connection;
import org.squat_team.vis.connector.Message;
import org.squat_team.vis.connector.MessageType;
import org.squat_team.vis.connector.data.CGoal;
import org.squat_team.vis.connector.data.CProject;
import org.squat_team.vis.connector.data.CToolConfiguration;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;

public class NewProjectClientProtocol extends AbstractClientProtocol<Connection> {
	private CProject project;
	private CToolConfiguration configuration;
	private CGoal goal;

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
	protected Connection executeProtocol() throws ProtocolFailure, InvalidRequestException {
		Connection connection = null;
		try {
			sendRequests();
			connection = receive(Connection.class);
		} catch (IOException e) {
			log(e);
		}
		return connection;
	}

	private void sendRequests() throws IOException {
		Message request = new Message(MessageType.REQUEST_NEW_PROJECT, null);
		send(request);
		send(project);
		send(configuration);
		send(goal);
	}

}
