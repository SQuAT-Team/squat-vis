package org.squat_team.vis.connector.protocols;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.server.ConnectorService;
import org.squat_team.vis.data.daos.ProjectDao;
import org.squat_team.vis.data.data.Project;
import org.squat_team.vis.data.data.Status;

public class NewLevelPostProtocolHandler extends AbstractPostProtocolHandler {
	private ProjectDao projectDao;
	private Project project;

	public NewLevelPostProtocolHandler(ConnectorService connectorService, ProjectConnector projectConnector) {
		super(connectorService, projectConnector);
		this.projectDao = connectorService.getProjectDao();
		this.project = projectDao.find(projectConnector.getProjectId());
	}

	@Override
	public void handle() {
		finishLevel();
		finishLevelImport();
	}

	private void finishLevelImport() {
		Status status = project.getStatus();
		status.notifyLevelImported();
		projectDao.update(project);
	}

	private void finishLevel() {
		Status status = project.getStatus();
		status.notifyLevelFinished();
		projectDao.update(project);
	}

}
