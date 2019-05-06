package org.squat_team.vis.connector.protocols;

import java.io.IOException;

import org.squat_team.vis.connector.Message;
import org.squat_team.vis.connector.MessageType;
import org.squat_team.vis.connector.ProjectConnector;

/**
 * A protocol that terminated a project. No further data should be send to this
 * project after this operation is called.
 */
public class ProjectTerminatedClientProtocol extends AbstractSimpleClientProtocol {
	private ProjectConnector projectConnector;

	/**
	 * Initializes the protocol.
	 * 
	 * @param projectConnector the project the level will be pushed to.
	 */
	public ProjectTerminatedClientProtocol(ProjectConnector projectConnector) {
		this.projectConnector = projectConnector;
	}

	@Override
	protected void sendRequests() throws IOException {
		Message message = new Message(MessageType.SEND_PROJECT_TERMINATED, projectConnector);
		send(message);
	}

}
