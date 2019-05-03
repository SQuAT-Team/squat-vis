package org.squat_team.vis.connector.protocols;

import java.io.IOException;

import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;

/**
 * These protocols are the counterpart to {@link IClientProtocol}s. Each
 * {@link IClientProtocol} is executed on the client side and answered by its
 * corresponding {@link IServerProtocol} at the server side.<br/>
 * <br/>
 * The protocol is executed by calling {@link #execute()}. In addition, a
 * {@link IPostProtocolHandler} can be obtained by calling
 * {@link #getPostProtocolHandler()}, which executes additional tasks that do
 * not need to communicate with the client.
 */
public interface IServerProtocol {

	/**
	 * Runs the protocol.
	 * 
	 * @throws ProtocolFailure         If the actual communication deviates fromthe
	 *                                 specified protocol specification.
	 * @throws IOException             If the protocol fails because of a problem
	 *                                 with the communication.
	 * @throws InvalidRequestException If the sent requests caused exceptions,e.g.,
	 *                                 because of missing values orconstraint
	 *                                 violations.
	 */
	public void execute() throws ProtocolFailure, IOException, InvalidRequestException;

	/**
	 * Gets the responsible {@link IPostProtocolHandler} that should be executed
	 * after the protocol.
	 * 
	 * @return the handler
	 */
	public IPostProtocolHandler getPostProtocolHandler();
}
