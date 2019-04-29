package org.squat_team.vis.connector.protocols;

import org.squat_team.vis.connector.Connection;
import org.squat_team.vis.connector.server.ServerService;
import org.squat_team.vis.data.daos.ProjectDao;
import org.squat_team.vis.data.data.Project;
import org.squat_team.vis.data.data.Status;

public class NewProjectPostProtocol extends AbstractPostProtocolHandler {
	private ProjectDao projectDao;
	private Project project;

	public NewProjectPostProtocol(ServerService serverService, Connection connection) {
		super(serverService, connection);
		projectDao = serverService.getProjectDao();
		this.project = projectDao.find(connection.getProjectId());
	}

	@Override
	public void handle() {
		startLevel();
	}

	private void startLevel() {
		Status status = project.getStatus();
		status.notifyNewLevel();
		projectDao.update(project);
	}
}
