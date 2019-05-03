package org.squat_team.vis.connector.protocols;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.server.ConnectorService;
import org.squat_team.vis.data.daos.ProjectDao;
import org.squat_team.vis.data.data.Project;
import org.squat_team.vis.data.data.Status;

/**
 * This handler should be called after the creation of a new project.
 */
public class NewProjectPostProtocol extends AbstractPostProtocolHandler {
	private ProjectDao projectDao;
	private Project project;

	/**
	 * Creates a new handler.
	 * 
	 * @param connectorService the connector server
	 * @param projectConnector identifies the project
	 */
	public NewProjectPostProtocol(ConnectorService connectorService, ProjectConnector projectConnector) {
		super(connectorService, projectConnector);
		this.projectDao = connectorService.getProjectDao();
		this.project = projectDao.find(projectConnector.getProjectId());
	}

	@Override
	public void handle() {
		updateStatusStartLevel();
	}

	/**
	 * Notifies the status of the project that a new level will be created.
	 */
	private void updateStatusStartLevel() {
		Status status = project.getStatus();
		status.notifyNewLevel();
		projectDao.update(project);
	}
}
