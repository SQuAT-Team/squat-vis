package org.squat_team.vis.connector.protocols;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.squat_team.vis.connector.DefaultServerConfiguration;
import org.squat_team.vis.connector.exceptions.ConnectionFailure;
import org.squat_team.vis.connector.exceptions.HostUnreachableException;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;

public abstract class AbstractClientProtocol<T> extends AbstractProtocolHelper implements IClientProtocol<T> {
	private final static Logger LOGGER = Logger.getLogger(AbstractClientProtocol.class.getName());

	private boolean initialized = false;
	private Socket socket;
	protected ObjectOutputStream out;
	protected ObjectInputStream in;

	@Override
	public T call() throws HostUnreachableException, ConnectionFailure, ProtocolFailure, InvalidRequestException {
		T result;
		try {
			establishConnection();
			initializeStreams();
			checkInitialized();
			result = executeProtocol();
		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}
		return result;
	}

	protected abstract T executeProtocol() throws ProtocolFailure, InvalidRequestException;

	private void establishConnection() throws HostUnreachableException {
		DefaultServerConfiguration configuration = DefaultServerConfiguration.getInstance();
		Socket socket = null;
		try {
			socket = tryLocalHostConnection(configuration);
		} catch (UnknownHostException e) {
			socket = tryRemoteHostConnection(configuration);
		}
		this.socket = socket;
	}

	private Socket tryLocalHostConnection(DefaultServerConfiguration configuration) throws UnknownHostException {
		Socket socket = null;
		try {
			socket = new Socket(configuration.getLocalHost(), configuration.getPort());
			initialized = true;
		} catch (IOException e) {
			initialized = false;
			log(e);
		}
		return socket;
	}

	private Socket tryRemoteHostConnection(DefaultServerConfiguration configuration) throws HostUnreachableException {
		Socket socket = null;
		try {
			socket = new Socket(configuration.getRemoteHost(), configuration.getPort());
			initialized = true;
		} catch (UnknownHostException e) {
			throw new HostUnreachableException("Can not find a server, neither local nor remote");
		} catch (IOException e) {
			initialized = false;
			log(e);
		}
		return socket;
	}

	private void initializeStreams() {
		try {
			this.out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			this.in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			this.initializeProtocolHelper(in, out);
		} catch (IOException e) {
			initialized = false;
			log(e);
		}
	}

	private void checkInitialized() throws ConnectionFailure {
		if (!initialized) {
			throw new ConnectionFailure("No connection to the visualization tool established");
		}
	}

	private void close() {
		try {
			out.close();
			in.close();
			socket.close();
		} catch (IOException e) {
			log(e);
		}
	}

	protected void log(Exception e) {
		LOGGER.log(Level.WARNING, "Protocol execution stopped by unexpected error", e);
	}

}
