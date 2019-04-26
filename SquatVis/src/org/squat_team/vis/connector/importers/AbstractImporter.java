package org.squat_team.vis.connector.importers;

import org.squat_team.vis.connector.Connection;
import org.squat_team.vis.connector.server.ServerService;

public abstract class AbstractImporter<CData, Data> implements IImporter<CData, Data> {

	protected ServerService serverService;
	protected Connection connection;

	public AbstractImporter(ServerService serverService, Connection connection) {
		this.serverService = serverService;
		this.connection = connection;
	}

}
