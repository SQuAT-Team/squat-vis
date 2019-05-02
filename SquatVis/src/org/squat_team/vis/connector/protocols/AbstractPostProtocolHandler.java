package org.squat_team.vis.connector.protocols;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.server.ConnectorService;

public abstract class AbstractPostProtocolHandler implements IPostProtocolHandler {
	protected ConnectorService connectorService;
	protected ProjectConnector projectConnector;

	public AbstractPostProtocolHandler(ConnectorService connectorService, ProjectConnector projectConnector) {
		this.connectorService = connectorService;
		this.projectConnector = projectConnector;
	}

}
