package org.squat_team.vis.connector.protocols;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.server.ConnectorService;
import org.squat_team.vis.data.daos.ProjectDao;
import org.squat_team.vis.data.daos.StatusLogDao;
import org.squat_team.vis.data.data.Project;
import org.squat_team.vis.data.data.Status;

/**
 * Provides methods to update the status of a project.
 */
public abstract class AbstractStatusUpdatingPostProtocolHandler extends AbstractPostProtocolHandler {
	protected ProjectDao projectDao;
	private StatusLogDao statusLogDao;

	public AbstractStatusUpdatingPostProtocolHandler(ConnectorService connectorService,
			ProjectConnector projectConnector) {
		super(connectorService, projectConnector);
		this.projectDao = connectorService.getProjectDao();
		this.statusLogDao = connectorService.getStatusLogDao();
	}

	/**
	 * Notifies the status of the project that a new level will be created.
	 */
	protected void updateStatusStartLevel() {
		Project project = projectDao.find(projectConnector.getProjectId());
		Status status = project.getStatus();
		status.notifyNewLevel();
		projectDao.update(project);
		updateDatabaseStatusLog(project);
	}

	/**
	 * Notifies the status of the project that all post import tasks are finished.
	 */
	protected void updateStatusFinishLevelImport() {
		Project project = projectDao.find(projectConnector.getProjectId());
		Status status = project.getStatus();
		status.notifyLevelImported();
		projectDao.update(project);
		updateDatabaseStatusLog(project);
	}

	/**
	 * Notifies the status of the project that the level is imported and is now
	 * analyzed.
	 */
	protected void updateStatusFinishLevel() {
		Project project = projectDao.find(projectConnector.getProjectId());
		Status status = project.getStatus();
		status.notifyLevelFinished();
		projectDao.update(project);
		updateDatabaseStatusLog(project);
	}

	private void updateDatabaseStatusLog(Project project) {
		statusLogDao.update(project.getStatus().getStatusLog());
	}

}
