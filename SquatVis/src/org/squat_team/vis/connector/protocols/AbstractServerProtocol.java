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
	protected ProjectConnector projectConnector;

	public AbstractServerProtocol(ObjectInputStream in, ObjectOutputStream out, ConnectorService connectorService,
			ProjectConnector projectConnector) {
		super();
		this.in = in;
		this.out = out;
		this.connectorService = connectorService;
		this.projectConnector = projectConnector;
		this.initializeProtocolHelper(in, out);
	}

	protected void respondAccept(ProjectConnector projectConnector) throws IOException {
		Message response = new Message(MessageType.ACCEPT, projectConnector);
		send(response);
	}

	protected void respondDeclineWithException(ProjectConnector projectConnector, Exception e) throws IOException {
		Message response = new Message(MessageType.DECLINE, projectConnector);
		response.setException(e);
		send(response);
	}

}
