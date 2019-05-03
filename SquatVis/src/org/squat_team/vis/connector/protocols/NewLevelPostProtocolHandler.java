package org.squat_team.vis.connector.protocols;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.server.ConnectorService;
import org.squat_team.vis.data.daos.ProjectDao;
import org.squat_team.vis.data.data.Project;
import org.squat_team.vis.data.data.Status;

/**
 * This handler should be called after the import of a new level of search.
 */
public class NewLevelPostProtocolHandler extends AbstractPostProtocolHandler {
	private ProjectDao projectDao;
	private Project project;

	/**
	 * Creates a new handler.
	 * 
	 * @param connectorService Provides daos for the import
	 * @param projectConnector Specifies the project the import belongs to
	 */
	public NewLevelPostProtocolHandler(ConnectorService connectorService, ProjectConnector projectConnector) {
		super(connectorService, projectConnector);
		this.projectDao = connectorService.getProjectDao();
		this.project = projectDao.find(projectConnector.getProjectId());
	}

	@Override
	public void handle() {
		updateStatusFinishLevel();
		updateStatusFinishLevelImport();
	}

	/**
	 * Notifies the status of the project that all post import tasks are finished.
	 */
	private void updateStatusFinishLevelImport() {
		Status status = project.getStatus();
		status.notifyLevelImported();
		projectDao.update(project);
	}

	/**
	 * Notifies the status of the project that the level is imported and is now
	 * analyzed.
	 */
	private void updateStatusFinishLevel() {
		Status status = project.getStatus();
		status.notifyLevelFinished();
		projectDao.update(project);
	}

}
