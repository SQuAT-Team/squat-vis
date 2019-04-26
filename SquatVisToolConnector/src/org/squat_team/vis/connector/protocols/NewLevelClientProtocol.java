package org.squat_team.vis.connector.protocols;

import java.io.IOException;

import org.squat_team.vis.connector.Connection;
import org.squat_team.vis.connector.Message;
import org.squat_team.vis.connector.MessageType;
import org.squat_team.vis.connector.data.CLevel;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;

public class NewLevelClientProtocol extends AbstractClientProtocol<Boolean> {
	private CLevel level;
	private Connection connection;

	public NewLevelClientProtocol(CLevel level, Connection connection) {
		super();
		if (level == null) {
			throw new IllegalArgumentException("Provided level must not be null");
		}
		this.level = level;
		this.connection = connection;
	}

	@Override
	protected Boolean executeProtocol() throws ProtocolFailure, InvalidRequestException {
		boolean success = false;
		try {
			sendRequests();
			Message response = receive(Message.class);
			if (response.getType().equals(MessageType.ACCEPT)) {
				success = true;
			}
		} catch (IOException e) {
			log(e);
		}
		return success;
	}

	private void sendRequests() throws IOException {
		Message request = new Message(MessageType.SEND_NEW_LEVEL, connection);
		send(request);
		send(level);
	}

}
