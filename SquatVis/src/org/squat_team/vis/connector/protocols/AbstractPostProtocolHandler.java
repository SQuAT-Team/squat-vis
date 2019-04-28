package org.squat_team.vis.connector.protocols;

import org.squat_team.vis.connector.Connection;
import org.squat_team.vis.connector.server.ServerService;

public abstract class AbstractPostProtocolHandler implements IPostProtocolHandler {
	protected ServerService serverService;
	protected Connection connection;

	public AbstractPostProtocolHandler(ServerService serverService, Connection connection) {
		this.serverService = serverService;
		this.connection = connection;
	}

}
