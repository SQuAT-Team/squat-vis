package org.squat_team.vis.connector.protocols;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.Message;
import org.squat_team.vis.connector.MessageType;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;
import org.squat_team.vis.connector.server.ConnectorService;

/**
 * The default implementation of the {@link IServerProtocolDispatcher}.
 */
public class ServerProtocolDispatcher implements IServerProtocolDispatcher {
	protected ObjectInputStream in;
	protected ObjectOutputStream out;
	protected ConnectorService connectorService;

	/**
	 * Creates a new dispatcher.
	 * 
	 * @param in               the connection from client to server.
	 * @param out              the connection from server to client.
	 * @param connectorService Provides daos for the import
	 */
	public ServerProtocolDispatcher(ObjectInputStream in, ObjectOutputStream out, ConnectorService connectorService) {
		this.connectorService = connectorService;
		this.in = in;
		this.out = out;
	}

	@Override
	public IServerProtocol dispatch(Message message) throws ProtocolFailure {
		MessageType type = message.getType();
		ProjectConnector projectConnector = message.getProjectConnector();
		IServerProtocol protocol = null;
		switch (type) {
		case REQUEST_NEW_PROJECT:
			protocol = new NewProjectServerProtocol(in, out, connectorService);
			break;
		case SEND_STATUS_UPDATE:
			protocol = new UpdateStatusServerProtocol(in, out, connectorService, projectConnector);
			break;
		case SEND_NEW_LEVEL:
			protocol = new NewLevelServerProtocol(in, out, connectorService, projectConnector);
			break;
		case SEND_PROJECT_TERMINATED:
			protocol = new ProjectTerminatedServerProtocol(in, out, connectorService, projectConnector);
			break;
		case REQUEST_LEVEL_RESPONSE:
			protocol = new LevelResponseServerProtocol(in, out, connectorService, projectConnector);
			break;
		default:
			throw new ProtocolFailure("Unknown request type " + type);
		}
		return protocol;
	}

}
