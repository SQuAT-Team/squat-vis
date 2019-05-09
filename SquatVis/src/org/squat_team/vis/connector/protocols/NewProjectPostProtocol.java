package org.squat_team.vis.connector.protocols;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.server.ConnectorService;

/**
 * This handler should be called after the creation of a new project.
 */
public class NewProjectPostProtocol extends AbstractStatusUpdatingPostProtocolHandler {
	/**
	 * Creates a new handler.
	 * 
	 * @param connectorService the connector server
	 * @param projectConnector identifies the project
	 */
	public NewProjectPostProtocol(ConnectorService connectorService, ProjectConnector projectConnector) {
		super(connectorService, projectConnector);
	}

	@Override
	public void handle() {
		updateStatusStartLevel();
	}
}
