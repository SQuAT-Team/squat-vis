package org.squat_team.vis.connector.importers;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.data.CToolConfiguration;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.server.ConnectorService;
import org.squat_team.vis.data.daos.ToolConfigurationDao;
import org.squat_team.vis.data.data.Project;
import org.squat_team.vis.data.data.ToolConfiguration;

/**
 * Imports {@link CToolConfiguration}s and returns {@link ToolConfiguration}
 * objects, which are then stored in the database.
 */
public class ToolConfigurationImporter extends AbstractImporter<CToolConfiguration, ToolConfiguration> {

	/**
	 * Creates a new importer.
	 * 
	 * @param connectorService Provides daos for the import
	 * @param projectConnector Specifies the project the import belongs to
	 * 
	 */
	public ToolConfigurationImporter(ConnectorService connectorService, ProjectConnector projectConnector) {
		super(connectorService, projectConnector);
	}

	@Override
	public ToolConfiguration transform(CToolConfiguration cconfiguration) throws InvalidRequestException {
		ToolConfiguration configuration = transformConfiguration(cconfiguration);
		store(configuration);
		updateProject(configuration);
		return configuration;
	}

	/**
	 * 
	 * Applies the transformation on the object level.
	 * 
	 * @param cconfiguration the configuration to transform
	 * @return the transformed configuration
	 */
	private ToolConfiguration transformConfiguration(CToolConfiguration cconfiguration) {
		ToolConfiguration configuration = new ToolConfiguration();
		configuration.setName(cconfiguration.getName());
		configuration.setToolName(cconfiguration.getToolName());
		configuration.setHasUtilities(cconfiguration.getHasUtilities());
		configuration.setHasRealValues(cconfiguration.getHasRealValues());
		configuration.setHasArchitectures(cconfiguration.getHasArchitectures());
		return configuration;
	}

	/**
	 * Stores the configuration in the database.
	 * 
	 * @param toolConfiguration the configuration to store.
	 */
	private void store(ToolConfiguration toolConfiguration) {
		ToolConfigurationDao dao = connectorService.getToolConfigurationDao();
		dao.save(toolConfiguration);
	}

	/**
	 * Updates the project and sets the configuration.
	 * 
	 * @param configuration the configuration to set.
	 * @throws InvalidRequestException if project could not be found.
	 */
	private void updateProject(ToolConfiguration configuration) throws InvalidRequestException {
		Project project = findProject();
		project.setConfiguration(configuration);
		update(project);
	}

}
