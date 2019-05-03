package org.squat_team.vis.connector.protocols;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.server.ConnectorService;

/**
 * Default implementation of {@link IPostProtocolHandler} that provides useful
 * methods and fields.
 */
public abstract class AbstractPostProtocolHandler implements IPostProtocolHandler {
	protected ConnectorService connectorService;
	protected ProjectConnector projectConnector;

	/**
	 * Default constructor
	 * 
	 * @param connectorService Provides daos for the import
	 * @param projectConnector Specifies the project the import belongs to
	 */
	public AbstractPostProtocolHandler(ConnectorService connectorService, ProjectConnector projectConnector) {
		this.connectorService = connectorService;
		this.projectConnector = projectConnector;
	}

}
