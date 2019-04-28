package org.squat_team.vis.connector.protocols;

import org.squat_team.vis.connector.Connection;
import org.squat_team.vis.connector.server.ServerService;
import org.squat_team.vis.data.daos.ProjectDao;
import org.squat_team.vis.data.data.Project;
import org.squat_team.vis.data.data.Status;

public class NewLevelPostProtocolHandler extends AbstractPostProtocolHandler {
	private ProjectDao projectDao;
	private Project project;

	public NewLevelPostProtocolHandler(ServerService serverService, Connection connection) {
		super(serverService,  connection);
		projectDao = serverService.getProjectDao();
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
