package org.squat_team.vis.connector.server;

import java.io.IOException;
import java.net.ServerSocket;

import org.squat_team.vis.connector.DefaultServerConfiguration;

public class MainServer extends Thread {

	private ServerService runner;
	private static MainServer instance = null;

	public static MainServer getInstance() {
		if (instance == null) {
			instance = new MainServer();
		}
		return instance;
	}

	private MainServer() {
		// Singleton
	}

	public void run() {
		// TODO: log
		int portNumber = DefaultServerConfiguration.getInstance().getPort();

		boolean listening = true;

		try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
			while (listening) {
				new RequestHandler(serverSocket.accept(), runner).start();
			}
		} catch (IOException e) {
			System.err.println("Could not listen on port " + portNumber);
			System.exit(-1);
		}
	}

	public ServerService getRunner() {
		return runner;
	}

	public void setRunner(ServerService runner) {
		this.runner = runner;
	}

}
