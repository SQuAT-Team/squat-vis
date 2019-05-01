package org.squat_team.vis.connector.importers;

import org.squat_team.vis.connector.Connection;
import org.squat_team.vis.connector.server.ConnectorService;

public abstract class AbstractImporter<CData, Data> implements IImporter<CData, Data> {

	protected ConnectorService connectorService;
	protected Connection connection;

	public AbstractImporter(ConnectorService connectorService, Connection connection) {
		this.connectorService = connectorService;
		this.connection = connection;
	}

}
