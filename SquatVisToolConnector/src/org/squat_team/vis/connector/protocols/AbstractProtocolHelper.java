package org.squat_team.vis.connector.protocols;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.squat_team.vis.connector.Message;
import org.squat_team.vis.connector.MessageType;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;

/**
 * A helper for server side protocols and client side {@link IClientProtocol}.
 * Contains standard methods that can be used by the protocols.
 */
public abstract class AbstractProtocolHelper {
	private ObjectInputStream in;
	private ObjectOutputStream out;

	protected void initializeProtocolHelper(ObjectInputStream in, ObjectOutputStream out) {
		this.in = in;
		this.out = out;
	}

	/**
	 * Sends an object over the connection.
	 * 
	 * @param object the object that will be send. Must implement
	 *               {@link Serializable}.
	 * @throws IOException If object could not be send.
	 */
	protected void send(Object object) throws IOException {
		this.out.writeObject(object);
		this.out.flush();
	}

	/**
	 * 
	 * Receives an the object of the specified type or throws an exception.
	 * 
	 * @param <C>   Specifies the class that the received object implements.
	 * @param clazz Has to be the *.class of generic C
	 * @return the object of the expected type.
	 * @throws ProtocolFailure         If the received object is of an unexpected or
	 *                                 unknown type.
	 * @throws IOException             If the object could not be read.
	 * @throws InvalidRequestException If a {@link Message} of type
	 *                                 {@link MessageType#DECLINE} is received
	 *                                 instead.
	 */
	@SuppressWarnings("unchecked")
	protected <C> C receive(Class<C> clazz) throws ProtocolFailure, IOException, InvalidRequestException {
		C cData = null;
		Object receivedObject;
		try {
			receivedObject = in.readObject();
			if (isErrorResponse(receivedObject)) {
				handleErrorResponse(receivedObject);
			} else if (clazz.isInstance(receivedObject)) {
				cData = (C) receivedObject;
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

	/**
	 * Determines whether the received object is an error response or not.
	 * 
	 * @param receivedObject the received object
	 * @return true if this is a {@link Message} of type
	 *         {@link MessageType#DECLINE}.
	 */
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

	/**
	 * Handles the case in which an error response is received instead of the
	 * expected object (type).
	 * 
	 * @param receivedObject the received {@link Message} of type
	 *                       {@link MessageType#DECLINE}.
	 * @throws InvalidRequestException thrown in any case.
	 */
	private void handleErrorResponse(Object receivedObject) throws InvalidRequestException {
		Message message = (Message) receivedObject;
		throw new InvalidRequestException("Received error response from visualization tool", message.getException());
	}
}
