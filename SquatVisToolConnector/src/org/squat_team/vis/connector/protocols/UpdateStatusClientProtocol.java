package org.squat_team.vis.connector.protocols;

import java.io.IOException;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.Message;
import org.squat_team.vis.connector.MessageType;
import org.squat_team.vis.connector.data.CStatus;

/**
 * A protocol that sends updates about the optimization progress to the SquatVis
 * tool. Updates are optional, but SquatVis might assume that the optimization
 * failed if it does not receive new data for a while.
 */
public class UpdateStatusClientProtocol extends AbstractSimpleClientProtocol {
	private CStatus status;
	private ProjectConnector connection;

	/**
	 * Initializes the protocol.
	 * 
	 * @param status           the new status
	 * @param projectConnector identifies the project to update
	 */
	public UpdateStatusClientProtocol(CStatus status, ProjectConnector projectConnector) {
		super();
		if (status == null) {
			throw new IllegalArgumentException("Provided status must not be null");
		}
		this.status = status;
		this.connection = projectConnector;
	}

	protected void sendRequests() throws IOException {
		Message request = new Message(MessageType.SEND_STATUS_UPDATE, connection);
		send(request);
		send(status);
	}

}
