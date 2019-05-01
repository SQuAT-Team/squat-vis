package org.squat_team.vis.connector.server;

import java.io.BufferedInputStream;
import java.io.IOException;
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

	public ConnectorRequestHandler(Socket socket, ConnectorService connectorService) {
		super("RequestHandler");
		this.socket = socket;
		this.connectorService = connectorService;
	}

	public void run() {
		try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));) {
			Message message = getMessage(in);
			IServerProtocolDispatcher protocolDispatcher = new ServerProtocolDispatcher(in, out, connectorService);
			IServerProtocol protocol = protocolDispatcher.dispatch(message);
			protocol.execute();
			socket.close();
			protocol.getPostProtocolHandler().handle();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ProtocolFailure e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Message getMessage(ObjectInputStream in) throws ProtocolFailure, IOException {
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
}
