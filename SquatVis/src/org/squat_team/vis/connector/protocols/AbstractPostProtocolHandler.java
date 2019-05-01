package org.squat_team.vis.connector.protocols;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.server.ConnectorService;

public abstract class AbstractPostProtocolHandler implements IPostProtocolHandler {
	protected ConnectorService connectorService;
	protected ProjectConnector connection;

	public AbstractPostProtocolHandler(ConnectorService connectorService, ProjectConnector connection) {
		this.connectorService = connectorService;
		this.connection = connection;
	}

}
