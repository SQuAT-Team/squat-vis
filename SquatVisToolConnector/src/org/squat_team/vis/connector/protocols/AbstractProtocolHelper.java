package org.squat_team.vis.connector.protocols;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.squat_team.vis.connector.Message;
import org.squat_team.vis.connector.MessageType;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;

public abstract class AbstractProtocolHelper {
	private ObjectInputStream in;
	private ObjectOutputStream out;

	protected void initializeProtocolHelper(ObjectInputStream in, ObjectOutputStream out) {
		this.in = in;
		this.out = out;
	}

	protected void send(Object object) throws IOException {
		out.writeObject(object);
		out.flush();
	}

	@SuppressWarnings("unchecked")
	protected <CData> CData receive(Class<CData> clazz) throws ProtocolFailure, IOException, InvalidRequestException {
		CData cData;
		Object receivedObject;
		try {
			receivedObject = in.readObject();
			if (isErrorResponse(receivedObject)) {
				Message message = (Message) receivedObject;
				throw new InvalidRequestException("Received error response from visualization tool",
						message.getException());
			} else if (clazz.isInstance(receivedObject)) {
				cData = (CData) receivedObject;
			} else {
				throw new ProtocolFailure("Expected a " + clazz.getName() + ", but received " + receivedObject);
			}
		} catch (ClassNotFoundException e) {
			throw new ProtocolFailure(
					"Received response could not be interpreted. Do you use different versions of the connector project?",
					e);
		}
		return cData;
	}

	private boolean isErrorResponse(Object receivedObject) {
		boolean isMessage = receivedObject instanceof Message;
		boolean isErrorMessage = false;
		boolean containsExceptions = false;
		if (isMessage) {
			Message message = (Message) receivedObject;
			isErrorMessage = message.getType().equals(MessageType.DECLINE);
			containsExceptions = message.getException() != null;
		}
		return isMessage && isErrorMessage && containsExceptions;
	}
}
