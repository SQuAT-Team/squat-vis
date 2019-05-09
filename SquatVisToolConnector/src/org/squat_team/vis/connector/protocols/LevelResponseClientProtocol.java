package org.squat_team.vis.connector.protocols;

import java.io.IOException;
import java.util.List;

import org.squat_team.vis.connector.Message;
import org.squat_team.vis.connector.MessageType;
import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.exceptions.ConnectionFailure;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;

import lombok.NonNull;

public class LevelResponseClientProtocol extends AbstractClientProtocol<List<Long>> {
	private ProjectConnector projectConnector;
	private int levelNumber;

	public LevelResponseClientProtocol(int levelNumber, @NonNull ProjectConnector projectConnector) {
		super();
		this.projectConnector = projectConnector;
		this.levelNumber = levelNumber;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<Long> executeProtocol() throws ProtocolFailure, InvalidRequestException, ConnectionFailure {
		List<Long> candidateIds = null;
		try {
			sendRequests();
			candidateIds = receive(List.class);
		} catch (IOException e) {
			throw new ConnectionFailure("A problem in the communication occurred", e);
		}
		return candidateIds;
	}

	private void sendRequests() throws IOException {
		Message request = new Message(MessageType.REQUEST_LEVEL_RESPONSE, projectConnector);
		send(request);
		send(levelNumber);
	}

}
