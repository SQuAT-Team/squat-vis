package org.squat_team.vis.connector.protocols;

import org.squat_team.vis.connector.Message;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;

public interface IServerProtocolDispatcher {
	public IServerProtocol dispatch(Message message) throws ProtocolFailure;
}
