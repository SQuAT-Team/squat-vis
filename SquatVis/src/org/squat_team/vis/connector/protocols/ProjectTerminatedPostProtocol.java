package org.squat_team.vis.connector.protocols;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.server.ConnectorService;

/**
 * This handler should be called when a project finally terminated.
 */
public class ProjectTerminatedPostProtocol extends AbstractPostProtocolHandler {

	/**
	 * Creates a new handler.
	 * 
	 * @param connectorService Provides daos for the import
	 * @param projectConnector Specifies the project the import belongs to
	 */
	public ProjectTerminatedPostProtocol(ConnectorService connectorService, ProjectConnector projectConnector) {
		super(connectorService, projectConnector);
	}

	@Override
	public void handle() {
		// do nothing
	}

}
