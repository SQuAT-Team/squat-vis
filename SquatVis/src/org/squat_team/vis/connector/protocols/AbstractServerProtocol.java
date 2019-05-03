package org.squat_team.vis.connector.protocols;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.Message;
import org.squat_team.vis.connector.MessageType;
import org.squat_team.vis.connector.server.ConnectorService;

/**
 * Default implementation of {@link IPostProtocolHandler} that provides useful
 * methods and fields.
 */
public abstract class AbstractServerProtocol extends AbstractProtocolHelper implements IServerProtocol {
	protected ObjectInputStream in;
	protected ObjectOutputStream out;
	protected ConnectorService connectorService;
	protected ProjectConnector projectConnector;

	/**
	 * Default Constructor.
	 * 
	 * @param in               the connection from client to server.
	 * @param out              the connection from server to client.
	 * @param connectorService Provides daos for the import
	 * @param projectConnector Specifies the project the import belongs to
	 */
	public AbstractServerProtocol(ObjectInputStream in, ObjectOutputStream out, ConnectorService connectorService,
			ProjectConnector projectConnector) {
		super();
		this.in = in;
		this.out = out;
		this.connectorService = connectorService;
		this.projectConnector = projectConnector;
		this.initializeProtocolHelper(in, out);
	}

	/**
	 * Sends a message to the client that indicates that the request was accepted.
	 * 
	 * @param projectConnector Specifies the project the communication belongs to
	 * @throws IOException if something went wrong with the communication
	 */
	protected void respondAccept(ProjectConnector projectConnector) throws IOException {
		Message response = new Message(MessageType.ACCEPT, projectConnector);
		send(response);
	}

	/**
	 * Sends a message to the client that indicates that the request was declined.
	 * 
	 * @param projectConnector Specifies the project the communication belongs to
	 * @param exception        the exception that caused the decline
	 * @throws IOException if something went wrong with the communication
	 */
	protected void respondDeclineWithException(ProjectConnector projectConnector, Exception exception)
			throws IOException {
		Message response = new Message(MessageType.DECLINE, projectConnector);
		response.setException(exception);
		send(response);
	}

}
