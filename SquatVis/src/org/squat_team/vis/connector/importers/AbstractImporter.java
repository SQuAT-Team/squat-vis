package org.squat_team.vis.connector.importers;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.server.ConnectorService;

public abstract class AbstractImporter<CData, Data> implements IImporter<CData, Data> {

	protected ConnectorService connectorService;
	protected ProjectConnector connection;

	public AbstractImporter(ConnectorService connectorService, ProjectConnector connection) {
		this.connectorService = connectorService;
		this.connection = connection;
	}

}
