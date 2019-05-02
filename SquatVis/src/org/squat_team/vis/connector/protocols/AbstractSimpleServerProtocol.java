package org.squat_team.vis.connector.protocols;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;
import org.squat_team.vis.connector.importers.IImporter;
import org.squat_team.vis.connector.server.ConnectorService;

/**
 * Provides a standard implementation for simple server protocols that only
 * receive data and transforms it.
 */
public abstract class AbstractSimpleServerProtocol extends AbstractServerProtocol {

	/**
	 * Default constructor.
	 * 
	 * @param in               input stream from client to server
	 * @param out              output stream from server to client
	 * @param connectorService the connector server
	 * @param projectConnector identifies the project
	 */
	public AbstractSimpleServerProtocol(ObjectInputStream in, ObjectOutputStream out, ConnectorService connectorService,
			ProjectConnector projectConnector) {
		super(in, out, connectorService, projectConnector);
	}

	@Override
	public void execute() throws ProtocolFailure, IOException, InvalidRequestException {
		try {
			receive();
			transform();
			respondAccept(projectConnector);
		} catch (InvalidRequestException e) {
			respondDeclineWithException(projectConnector, e);
		}
	}

	/**
	 * Receive data from the client.
	 * 
	 * @throws ProtocolFailure         If the communication deviates from the
	 *                                 defined protocol
	 * @throws IOException             If there is and error caused by the
	 *                                 communication
	 * @throws InvalidRequestException If the received data violates constraints
	 */
	protected abstract void receive() throws ProtocolFailure, IOException, InvalidRequestException;

	/**
	 * Transform the data, usually by using some implementation of
	 * {@link IImporter}.
	 * 
	 * @throws InvalidRequestException If the received data violates constraints
	 */
	protected abstract void transform() throws InvalidRequestException;

}
