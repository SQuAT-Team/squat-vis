package org.squat_team.vis.connector.protocols;

import java.io.IOException;

import org.squat_team.vis.connector.Connection;
import org.squat_team.vis.connector.Message;
import org.squat_team.vis.connector.MessageType;
import org.squat_team.vis.connector.data.CStatus;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;

public class UpdateStatusClientProtocol extends AbstractClientProtocol<Boolean> {
	private CStatus status;
	private Connection connection;

	public UpdateStatusClientProtocol(CStatus status, Connection connection) {
		super();
		if (status == null) {
			throw new IllegalArgumentException("Provided status must not be null");
		}
		this.status = status;
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
		Message request = new Message(MessageType.SEND_STATUS_UPDATE, connection);
		send(request);
		send(status);
	}

}
