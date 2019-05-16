package org.squat_team.vis.connector.importers;

import java.util.Date;

import org.squat_team.vis.connector.data.CProject;
import org.squat_team.vis.connector.server.ConnectorService;
import org.squat_team.vis.data.daos.ProjectDao;
import org.squat_team.vis.data.daos.StatusLogDao;
import org.squat_team.vis.data.data.Project;
import org.squat_team.vis.data.data.Status;
import org.squat_team.vis.data.data.StatusLog;

/**
 * Imports {@link CProject}s and returns {@link Project} objects, which are then
 * stored in the database.
 */
public class ProjectImporter extends AbstractImporter<CProject, Project> {

	/**
	 * Creates a new importer.
	 * 
	 * @param connectorService Provides daos for the import
	 */
	public ProjectImporter(ConnectorService connectorService) {
		super(connectorService, null);
	}

	@Override
	public Project transform(CProject cproject) {
		Project project = transformProject(cproject);
		ProjectDao dao = connectorService.getProjectDao();
		initializeStatusLog(project);
		dao.save(project);
		return project;
	}

	/**
	 * Applies the transformation on the object level.
	 * 
	 * @param cproject the project to transform.
	 * @return the transformed project.
	 */
	private Project transformProject(CProject cproject) {
		Project project = new Project();
		project.setName(cproject.getName());
		handleStatus(project);
		return project;
	}

	/**
	 * Stores the {@link StatusLog} in the database.
	 * 
	 * @param project the project the log belongs to.
	 * @return the stored log
	 */
	private StatusLog initializeStatusLog(Project project) {
		StatusLogDao statusLogDao = connectorService.getStatusLogDao();
		StatusLog statusLog = project.getStatus().getStatusLog();
		statusLogDao.save(statusLog);
		return statusLog;
	}

	/**
	 * Updates the status accordingly.
	 * 
	 * @param project
	 * @return
	 */
	private Status handleStatus(Project project) {
		Date currentDate = new Date();
		Status status = findStatus(project);
		status.setCreationTime(currentDate);
		status.setLevelStarted(currentDate);
		status.update();
		return status;
	}

	/**
	 * Finds the status of a project or creates a new one.
	 * 
	 * @param project the project that contains the status.
	 * @return the status.
	 */
	private Status findStatus(Project project) {
		Status status = project.getStatus();
		if (status == null) {
			status = new Status();
			project.setStatus(status);
		}
		return status;
	}

}
