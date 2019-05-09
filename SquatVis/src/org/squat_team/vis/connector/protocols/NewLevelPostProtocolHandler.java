package org.squat_team.vis.connector.protocols;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.server.ConnectorService;

/**
 * This handler should be called after the import of a new level of search.
 */
public class NewLevelPostProtocolHandler extends AbstractStatusUpdatingPostProtocolHandler {

	/**
	 * Creates a new handler.
	 * 
	 * @param connectorService Provides daos for the import
	 * @param projectConnector Specifies the project the import belongs to
	 */
	public NewLevelPostProtocolHandler(ConnectorService connectorService, ProjectConnector projectConnector) {
		super(connectorService, projectConnector);
	}

	@Override
	public void handle() {
		updateStatusFinishLevel();
		updateStatusFinishLevelImport();
	}

}
