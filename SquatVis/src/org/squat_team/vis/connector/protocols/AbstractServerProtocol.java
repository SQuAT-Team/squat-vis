package org.squat_team.vis.connector.protocols;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.Message;
import org.squat_team.vis.connector.MessageType;
import org.squat_team.vis.connector.server.ConnectorService;

public abstract class AbstractServerProtocol extends AbstractProtocolHelper implements IServerProtocol {
	protected ObjectInputStream in;
	protected ObjectOutputStream out;
	protected ConnectorService connectorService;
	protected ProjectConnector connection;

	public AbstractServerProtocol(ObjectInputStream in, ObjectOutputStream out, ConnectorService connectorService, ProjectConnector connection) {
		super();
		this.in = in;
		this.out = out;
		this.connectorService = connectorService;
		this.connection = connection;
		this.initializeProtocolHelper(in, out);
	}
	
	protected void respondAccept(ProjectConnector connection) throws IOException {
		Message response = new Message(MessageType.ACCEPT, connection);
		send(response);
	}

	protected void respondDeclineWithException(ProjectConnector connection, Exception e) throws IOException {
		Message response = new Message(MessageType.DECLINE, connection);
		response.setException(e);
		send(response);
	}

}
