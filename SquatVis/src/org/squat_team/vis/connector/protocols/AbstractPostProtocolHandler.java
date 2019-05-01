package org.squat_team.vis.connector.protocols;

import org.squat_team.vis.connector.Connection;
import org.squat_team.vis.connector.server.ConnectorService;

public abstract class AbstractPostProtocolHandler implements IPostProtocolHandler {
	protected ConnectorService connectorService;
	protected Connection connection;

	public AbstractPostProtocolHandler(ConnectorService connectorService, Connection connection) {
		this.connectorService = connectorService;
		this.connection = connection;
	}

}
