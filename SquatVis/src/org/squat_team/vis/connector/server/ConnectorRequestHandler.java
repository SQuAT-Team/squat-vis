package org.squat_team.vis.connector.server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;

import org.squat_team.vis.connector.Message;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;
import org.squat_team.vis.connector.protocols.IServerProtocol;
import org.squat_team.vis.connector.protocols.IServerProtocolDispatcher;
import org.squat_team.vis.connector.protocols.ServerProtocolDispatcher;

import lombok.extern.java.Log;

/**
 * Handles an incoming requests by a client that implements the Connector
 * interface.
 */
@Log
public class ConnectorRequestHandler extends Thread {
	private Socket socket = null;
	private ConnectorService connectorService;

	/**
	 * Creates a new request handler
	 * 
	 * @param socket           the socket that connects to the client.
	 * @param connectorService contains daos to access the database
	 */
	public ConnectorRequestHandler(Socket socket, ConnectorService connectorService) {
		super("RequestHandler");
		this.socket = socket;
		this.connectorService = connectorService;
	}

	/**
	 * Executes the correct protocol and initializes the analysis of the imported
	 * data.
	 */
	@Override
	public void run() {
		try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				InputStream inRaw = socket.getInputStream();
				ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(inRaw));) {
			IServerProtocol protocol = determineCorrectProtocol(in, inRaw, out);
			protocol.execute();
			protocol.getPostProtocolHandler().handle();
		} catch (IOException | ProtocolFailure | InvalidRequestException e) {
			log.log(Level.SEVERE, "An exception occurred during request handling", e);
		} finally {
			close(socket);
		}
	}

	/**
	 * Determines which protocol to use.
	 * 
	 * @param in  the input connection to the client
	 * @param inRaw  the input connection to the client
	 * @param out the output connection to the client
	 * @return the determined protocol
	 * @throws ProtocolFailure if the actual communication deviates from the
	 *                         protocol
	 * @throws IOException     if an exception related to the connection occurred.
	 */
	private IServerProtocol determineCorrectProtocol(ObjectInputStream in, InputStream inRaw, ObjectOutputStream out)
			throws ProtocolFailure, IOException {
		Message message = receiveMessage(in);
		IServerProtocolDispatcher protocolDispatcher = new ServerProtocolDispatcher(in, inRaw, out, connectorService);
		return protocolDispatcher.dispatch(message);
	}

	/**
	 * Receives a message.
	 * 
	 * @param in the input connection to the client
	 * @return the received message
	 * @throws ProtocolFailure if the actual communication deviates from the
	 *                         protocol
	 * @throws IOException     if an exception related to the connection occurred.
	 */
	private Message receiveMessage(ObjectInputStream in) throws ProtocolFailure, IOException {
		Message message;
		Object receivedObject;
		try {
			receivedObject = in.readObject();
			if (receivedObject instanceof Message) {
				message = (Message) receivedObject;
				log.log(Level.INFO, "Received message of type " + message.getType());
			} else {
				throw new ProtocolFailure("Expected to receive a Message object, but did received " + receivedObject);
			}
		} catch (ClassNotFoundException e) {
			throw new ProtocolFailure(
					"Received request could not be interpreted. Do you use different versions of the connector project?",
					e);
		} catch (IOException e) {
			log.log(Level.WARNING, "Could not get message from established connection", e);
			throw e;
		}
		return message;
	}

	/**
	 * Closes the socket or logs if not possible.
	 * 
	 * @param socket the socket to close.
	 */
	private void close(Socket socket) {
		try {
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			log.log(Level.WARNING, "An exception occurred while trying to close the socket", e);
		}
	}
}
