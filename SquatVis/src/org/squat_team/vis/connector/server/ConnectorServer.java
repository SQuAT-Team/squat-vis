package org.squat_team.vis.connector.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;

import org.squat_team.vis.connector.ServerConfiguration;

import lombok.extern.java.Log;

/**
 * This server runs during the whole lifetime of the application and delegates
 * the client requests to the {@link ConnectorRequestHandler}.<br/>
 * This class implements the Singleton pattern. Call {@link #getInstance()} to
 * get the server.<br/>
 * <br/>
 * THe server can be started by calling {@link #run()} and stopped by calling
 * {@link #shutdown()}.
 */
@Log
public class ConnectorServer extends Thread {
	private ConnectorService connectorService;
	private static ConnectorServer instance = null;
	private ServerSocket serverSocket;
	private int portNumber;

	/**
	 * Returns the (Singleton) server.
	 * 
	 * @return
	 */
	public static ConnectorServer getInstance() {
		if (instance == null) {
			instance = new ConnectorServer();
		}
		return instance;
	}

	/**
	 * Singleton
	 */
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

	/**
	 * Will shutdown the server.
	 */
	public void shutdown() {
		log.log(Level.INFO, "Shutdown Connector Service");
		this.interrupt();
		closeSocket();
	}

	/**
	 * Handles the exception
	 * 
	 * @param exception
	 */
	private void handle(IOException exception) {
		if (!isInterrupted()) {
			log.log(Level.SEVERE, "Could not listen on port " + portNumber, exception);
		}
	}

	/**
	 * Opens a socket that the server will listen on.
	 * 
	 * @throws IOException
	 */
	private void openSocket() throws IOException {
		log.log(Level.INFO, "Starting Connector Service");
		portNumber = ServerConfiguration.getDefault().getPort();
		serverSocket = new ServerSocket(portNumber);
		log.log(Level.INFO, "Connector Service listening on Port " + portNumber);
	}

	/**
	 * Continuously handle the requests that are approaching the server.
	 * 
	 * @throws IOException if an error caused by the connection occurs.
	 */
	private void waitForAndHandleRequest() throws IOException {
		while (!isInterrupted()) {
			new ConnectorRequestHandler(serverSocket.accept(), connectorService).start();
		}
	}

	/**
	 * Tries to close the socket.
	 */
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
