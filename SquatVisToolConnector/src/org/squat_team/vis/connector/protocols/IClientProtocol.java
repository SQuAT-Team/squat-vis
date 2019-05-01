package org.squat_team.vis.connector.protocols;

import java.util.concurrent.Callable;

import org.squat_team.vis.connector.ServerConfiguration;
import org.squat_team.vis.connector.exceptions.ConnectionFailure;
import org.squat_team.vis.connector.exceptions.HostUnreachableException;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;

/**
 * A client protocol communicates with the SquatVis Connector server. It should
 * be executed as own thread and can return a value of specified type. Usually
 * the protocol connects to the default server, but this can be changed by
 * setting {@link #setServerConfiguration(ServerConfiguration)}. <br/>
 * <br/>
 * Running a client protocol basically works in two simple steps:<br/>
 * 1) Initialize the values to send in the constructor<br/>
 * 2) Hand over the Protocol to an executor or execute it via {@link #call()}
 * 
 * @param <R> type that should be returned at the end of the protocol execution.
 */
public interface IClientProtocol<R> extends Callable<R> {
	@Override
	public R call() throws HostUnreachableException, ConnectionFailure, ProtocolFailure, InvalidRequestException;

	/**
	 * Gets the current configuration of the server that the protocol communicates
	 * with.
	 * 
	 * @return the configuration of the server.
	 */
	public ServerConfiguration getServerConfiguration();

	/**
	 * Sets the configuration of the server the protocol will communicate with.
	 * 
	 * @param serverConfiguration the configuration of the server.
	 */
	public void setServerConfiguration(ServerConfiguration serverConfiguration);
}
