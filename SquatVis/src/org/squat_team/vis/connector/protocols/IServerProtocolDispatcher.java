package org.squat_team.vis.connector.protocols;

import org.squat_team.vis.connector.Message;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;

/**
 * Selects the correct {@link IServerProtocol} based on the incoming
 * {@link Message}.
 */
public interface IServerProtocolDispatcher {

	/**
	 * Returns the correct {@link IServerProtocol} that can answer the incoming
	 * message(s).
	 * 
	 * @param message the initial, received message
	 * @return the protocol
	 * @throws ProtocolFailure If no protocol fits
	 */
	public IServerProtocol dispatch(Message message) throws ProtocolFailure;
}
