package org.squat_team.vis.connector.protocols;

import java.io.IOException;

import org.squat_team.vis.connector.Message;
import org.squat_team.vis.connector.MessageType;
import org.squat_team.vis.connector.exceptions.ConnectionFailure;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;

/**
 * A simple protocol only sends data and receives an acceptance message.
 */
public abstract class AbstractSimpleClientProtocol extends AbstractClientProtocol<Boolean> {

	/**
	 * Sends the requests to the server.
	 * 
	 * @throws IOException
	 */
	protected abstract void sendRequests() throws IOException;

	@Override
	protected Boolean executeProtocol() throws ProtocolFailure, InvalidRequestException, ConnectionFailure {
		boolean success = false;
		try {
			sendRequests();
			Message response = receive(Message.class);
			if (response.getType().equals(MessageType.ACCEPT)) {
				success = true;
			}
		} catch (IOException e) {
			throw new ConnectionFailure("Unexpected I/O based error during protocol execution", e);
		}
		return success;
	}

}
