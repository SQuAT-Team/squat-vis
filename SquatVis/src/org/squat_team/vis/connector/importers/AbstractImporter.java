package org.squat_team.vis.connector.importers;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.server.ConnectorService;
import org.squat_team.vis.data.daos.ProjectDao;
import org.squat_team.vis.data.data.Project;

/**
 * Provides Standard methods and fields for the implementation of
 * {@link IImporter}.
 * 
 * @param <C> Connector data type that is imported
 * @param <D> Corresponding database value that is ouputed
 */
public abstract class AbstractImporter<C, D> implements IImporter<C, D> {
	protected ConnectorService connectorService;
	protected ProjectConnector projectConnector;
	protected ProjectDao projectDao;

	/**
	 * The default constructor.
	 * 
	 * @param connectorService Provides daos for the import
	 * @param projectConnector Specifies the project the import belongs to
	 */
	public AbstractImporter(ConnectorService connectorService, ProjectConnector projectConnector) {
		this.connectorService = connectorService;
		this.projectConnector = projectConnector;
		this.projectDao = connectorService.getProjectDao();
	}

	/**
	 * Determines the project that the goal belongs to.
	 * 
	 * @return the project
	 * @throws InvalidRequestException If project does not exist
	 */
	protected Project findProject() throws InvalidRequestException {
		Project project = projectDao.find(projectConnector.getProjectId());
		checkProject(project);
		return project;
	}

	/**
	 * Assures that the project exists.
	 * 
	 * @return the project
	 * @throws InvalidRequestException If project does not exist
	 */
	private void checkProject(Project project) throws InvalidRequestException {
		if (project == null) {
			throw new InvalidRequestException("Could not find requested project");
		}
	}
	
	
	protected void update(Project project) {
		projectDao.update(project);
	}

}
