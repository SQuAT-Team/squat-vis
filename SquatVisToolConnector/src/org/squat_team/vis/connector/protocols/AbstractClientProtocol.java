package org.squat_team.vis.connector.protocols;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;

import org.squat_team.vis.connector.ServerConfiguration;
import org.squat_team.vis.connector.exceptions.ConnectionFailure;
import org.squat_team.vis.connector.exceptions.HostUnreachableException;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;

import lombok.NonNull;
import lombok.extern.java.Log;

/**
 * Standard implementation of {@link IClientProtocol} that handles the
 * connection to the server. Data can be send via {@link #out} and read via
 * {@link #in}.<br/>
 * <br/>
 * If not specified other in
 * {@link #setServerConfiguration(ServerConfiguration)}, communication is
 * established to the default {@link ServerConfiguration}.
 * 
 * @param <R> type that should be returned at the end of the protocol execution.
 */
@Log
public abstract class AbstractClientProtocol<R> extends AbstractProtocolHelper implements IClientProtocol<R> {
	private ServerConfiguration serverConfiguration = ServerConfiguration.getDefault();
	private Socket clientSocket;
	protected ObjectOutputStream out;
	protected ObjectInputStream in;
	protected OutputStream outRaw;

	@Override
	public ServerConfiguration getServerConfiguration() {
		return serverConfiguration;
	}

	@Override
	public void setServerConfiguration(@NonNull ServerConfiguration serverConfiguration) {
		this.serverConfiguration = serverConfiguration;
	}

	@Override
	public R call() throws ConnectionFailure, ProtocolFailure, InvalidRequestException {
		R result;
		try {
			establishConnection();
			initializeStreams();
			result = executeProtocol();
		} finally {
			close();
		}
		return result;
	}

	/**
	 * The protocol that should be executed.
	 * 
	 * @return
	 * @throws ProtocolFailure
	 * @throws InvalidRequestException
	 */
	protected abstract R executeProtocol() throws ProtocolFailure, InvalidRequestException, ConnectionFailure;

	/**
	 * Establishes the connection to the server. If a local server exists, it will
	 * connect to the local server.
	 * 
	 * @throws ConnectionFailure If connection could not be established.
	 */
	private void establishConnection() throws ConnectionFailure {
		try {
			this.clientSocket = tryLocalHostConnection(serverConfiguration);
		} catch (UnknownHostException e) {
			this.clientSocket = tryRemoteHostConnection(serverConfiguration);
		}
	}

	/**
	 * Tries to establish a connection to a local server.
	 * 
	 * @param configuration the server configuration.
	 * @return the socket that connects to the server.
	 * @throws UnknownHostException If there is no local server.
	 * @throws ConnectionFailure    If connection could not be established.
	 */
	private Socket tryLocalHostConnection(ServerConfiguration configuration)
			throws UnknownHostException, ConnectionFailure {
		Socket socket = null;
		try {
			socket = new Socket(configuration.getLocalHost(), configuration.getPort());
		} catch (IOException e) {
			throwConnectionFailure(e);
		}
		return socket;
	}

	/**
	 * Tries to establish a connection to a remote server.
	 * 
	 * @param configuration the server configuration.
	 * @return the socket that connects to the server.
	 * @throws ConnectionFailure If connection could not be established.
	 */
	private Socket tryRemoteHostConnection(ServerConfiguration configuration) throws ConnectionFailure {
		Socket socket = null;
		try {
			socket = new Socket(configuration.getRemoteHost(), configuration.getPort());
		} catch (UnknownHostException e) {
			throw new HostUnreachableException("Can not find a server, neither local nor remote");
		} catch (IOException e) {
			throwConnectionFailure(e);
		}
		return socket;
	}

	/**
	 * Initializes the input and output streams for the communication with the
	 * server.
	 * 
	 * @throws ConnectionFailure If streams could not be initialized.
	 */
	private void initializeStreams() throws ConnectionFailure {
		try {
			this.outRaw = clientSocket.getOutputStream();
			this.out = new ObjectOutputStream(new BufferedOutputStream(outRaw));
			this.in = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
			this.initializeProtocolHelper(in, out);
		} catch (IOException e) {
			throwConnectionFailure(e);
		}
	}

	/**
	 * Closes the connection.
	 */
	private void close() {
		try {
			out.close();
			in.close();
			clientSocket.close();
		} catch (IOException e) {
			log.log(Level.WARNING, "Could not close connection after Protocol finished", e);
		}
	}

	/**
	 * Throws a connection exception.
	 * 
	 * @param exception the originating exception
	 * @throws ConnectionFailure If streams could not be initialized.
	 */
	private void throwConnectionFailure(Exception exception) throws ConnectionFailure {
		throw new ConnectionFailure("No connection to the visualization tool established", exception);
	}

}
