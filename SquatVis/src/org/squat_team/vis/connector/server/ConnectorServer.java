package org.squat_team.vis.connector.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;

import org.squat_team.vis.connector.DefaultServerConfiguration;

import lombok.extern.java.Log;

/**
 * This server runs during the whole lifetime of the application and delegates
 * the client requests to the {@link ConnectorRequestHandler}.
 */
@Log
public class ConnectorServer extends Thread {
	private ConnectorService connectorService;
	private static ConnectorServer instance = null;
	private ServerSocket serverSocket;
	private int portNumber;

	public static ConnectorServer getInstance() {
		if (instance == null) {
			instance = new ConnectorServer();
		}
		return instance;
	}

	private ConnectorServer() {
		// Singleton
	}

	@Override
	public void run() {
		try {
			openSocket();
			waitForAndHandleRequest();
		} catch (IOException e) {
			handle(e);
		} finally {
			closeSocket();
		}
	}

	public ConnectorService getConnectorService() {
		return connectorService;
	}

	public void setConnectorService(ConnectorService connectorService) {
		this.connectorService = connectorService;
	}

	public void shutdown() {
		log.log(Level.INFO, "Shutdown Connector Service");
		this.interrupt();
		closeSocket();
	}

	private void handle(IOException exception) {
		if (!isInterrupted()) {
			log.log(Level.SEVERE, "Could not listen on port " + portNumber, exception);
		}
	}

	private void openSocket() throws IOException {
		log.log(Level.INFO, "Starting Connector Service");
		portNumber = DefaultServerConfiguration.getInstance().getPort();
		serverSocket = new ServerSocket(portNumber);
		log.log(Level.INFO, "Connector Service listening on Port " + portNumber);
	}

	private void waitForAndHandleRequest() throws IOException {
		while (!isInterrupted()) {
			new ConnectorRequestHandler(serverSocket.accept(), connectorService).start();
		}
	}

	private void closeSocket() {
		if (serverSocket != null) {
			try {
				serverSocket.close();
			} catch (IOException e) {
				log.log(Level.WARNING, "Could not close Socket", e);
			}
		}
	}

}
