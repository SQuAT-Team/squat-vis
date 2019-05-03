package org.squat_team.vis.connector.protocols;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.data.CStatus;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;
import org.squat_team.vis.connector.importers.StatusImporter;
import org.squat_team.vis.connector.server.ConnectorService;

/**
 * This protocol handles an incoming status update. <br/>
 * It is the counterpart to {@link UpdateStatusClientProtocol}.
 */
public class UpdateStatusServerProtocol extends AbstractSimpleServerProtocol {
	private CStatus cStatus;

	/**
	 * Creates a new protocol.
	 * 
	 * @param in               the connection from client to server.
	 * @param out              the connection from server to client.
	 * @param connectorService Provides daos for the import
	 * @param projectConnector Specifies the project the import belongs to
	 */
	public UpdateStatusServerProtocol(ObjectInputStream in, ObjectOutputStream out, ConnectorService connectorService,
			ProjectConnector projectConnector) {
		super(in, out, connectorService, projectConnector);
	}

	@Override
	protected void receive() throws ProtocolFailure, IOException, InvalidRequestException {
		cStatus = receive(CStatus.class);
	}

	@Override
	protected void transform() throws InvalidRequestException {
		(new StatusImporter(connectorService, projectConnector)).transform(cStatus);
	}

	@Override
	public IPostProtocolHandler getPostProtocolHandler() {
		return new EmptyPostProtocolHandler();
	}
}
