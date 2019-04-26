package org.squat_team.vis.connector.protocols;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.squat_team.vis.connector.Connection;
import org.squat_team.vis.connector.Message;
import org.squat_team.vis.connector.MessageType;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;
import org.squat_team.vis.connector.server.ServerService;

public class ServerProtocolDispatcher implements IServerProtocolDispatcher {
	protected ObjectInputStream in;
	protected ObjectOutputStream out;
	protected ServerService serverService;

	public ServerProtocolDispatcher(ObjectInputStream in, ObjectOutputStream out, ServerService serverService) {
		this.serverService = serverService;
		this.in = in;
		this.out = out;
	}

	@Override
	public IServerProtocol dispatch(Message message) throws ProtocolFailure {
		MessageType type = message.getType();
		Connection connection = message.getConnection();
		IServerProtocol protocol = null;
		switch (type) {
		case REQUEST_NEW_PROJECT:
			protocol = new NewProjectServerProtocol(in, out, serverService);
			break;
		case SEND_STATUS_UPDATE:
			protocol = new UpdateStatusServerProtocol(in, out, serverService, connection);
			break;
		case SEND_NEW_LEVEL:
			protocol = new NewLevelServerProtocol(in, out, serverService, connection);
			break;
		default:
			throw new ProtocolFailure("Unknown request type " + type);
		}
		return protocol;
	}

}
