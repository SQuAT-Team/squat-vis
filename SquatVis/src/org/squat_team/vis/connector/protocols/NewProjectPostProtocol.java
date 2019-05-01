package org.squat_team.vis.connector.protocols;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.server.ConnectorService;
import org.squat_team.vis.data.daos.ProjectDao;
import org.squat_team.vis.data.data.Project;
import org.squat_team.vis.data.data.Status;

public class NewProjectPostProtocol extends AbstractPostProtocolHandler {
	private ProjectDao projectDao;
	private Project project;

	public NewProjectPostProtocol(ConnectorService connectorService, ProjectConnector connection) {
		super(connectorService, connection);
		this.projectDao = connectorService.getProjectDao();
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
