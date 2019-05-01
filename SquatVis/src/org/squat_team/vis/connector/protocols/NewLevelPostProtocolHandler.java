package org.squat_team.vis.connector.protocols;

import org.squat_team.vis.connector.Connection;
import org.squat_team.vis.connector.server.ConnectorService;
import org.squat_team.vis.data.daos.ProjectDao;
import org.squat_team.vis.data.data.Project;
import org.squat_team.vis.data.data.Status;

public class NewLevelPostProtocolHandler extends AbstractPostProtocolHandler {
	private ProjectDao projectDao;
	private Project project;

	public NewLevelPostProtocolHandler(ConnectorService connectorService, Connection connection) {
		super(connectorService,  connection);
		this.projectDao = connectorService.getProjectDao();
		this.project = projectDao.find(connection.getProjectId());
	}

	@Override
	public void handle() {
		finishLevel();
	}

	private void finishLevel() {
		Status status = project.getStatus();
		status.notifyLevelImported();
		projectDao.update(project);
	}
	
}
