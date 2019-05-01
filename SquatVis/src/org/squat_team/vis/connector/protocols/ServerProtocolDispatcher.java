package org.squat_team.vis.connector.protocols;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.squat_team.vis.connector.Connection;
import org.squat_team.vis.connector.Message;
import org.squat_team.vis.connector.MessageType;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;
import org.squat_team.vis.connector.server.ConnectorService;

public class ServerProtocolDispatcher implements IServerProtocolDispatcher {
	protected ObjectInputStream in;
	protected ObjectOutputStream out;
	protected ConnectorService connectorService;

	public ServerProtocolDispatcher(ObjectInputStream in, ObjectOutputStream out, ConnectorService connectorService) {
		this.connectorService = connectorService;
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
			protocol = new NewProjectServerProtocol(in, out, connectorService);
			break;
		case SEND_STATUS_UPDATE:
			protocol = new UpdateStatusServerProtocol(in, out, connectorService, connection);
			break;
		case SEND_NEW_LEVEL:
			protocol = new NewLevelServerProtocol(in, out, connectorService, connection);
			break;
		default:
			throw new ProtocolFailure("Unknown request type " + type);
		}
		return protocol;
	}

}
