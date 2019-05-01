package org.squat_team.vis.connector.importers;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.data.CToolConfiguration;
import org.squat_team.vis.connector.server.ConnectorService;
import org.squat_team.vis.data.daos.ProjectDao;
import org.squat_team.vis.data.daos.ToolConfigurationDao;
import org.squat_team.vis.data.data.Project;
import org.squat_team.vis.data.data.ToolConfiguration;

public class ToolConfigurationImporter extends AbstractImporter<CToolConfiguration, ToolConfiguration> {

	public ToolConfigurationImporter(ConnectorService connectorService, ProjectConnector connection) {
		super(connectorService, connection);
	}

	@Override
	public ToolConfiguration transform(CToolConfiguration cconfiguration) {
		ToolConfiguration configuration = transformConfiguration(cconfiguration);
		updateProject(configuration);
		return configuration;
	}

	private ToolConfiguration transformConfiguration(CToolConfiguration cconfiguration) {
		ToolConfiguration configuration = new ToolConfiguration();
		configuration.setName(cconfiguration.getName());
		configuration.setToolName(cconfiguration.getToolName());
		configuration.setHasUtilities(cconfiguration.getHasUtilities());
		configuration.setHasRealValues(cconfiguration.getHasRealValues());
		configuration.setHasArchitectures(cconfiguration.getHasArchitectures());

		ToolConfigurationDao dao = connectorService.getToolConfigurationDao();
		dao.save(configuration);

		return configuration;
	}

	private void updateProject(ToolConfiguration configuration) {
		ProjectDao dao = connectorService.getProjectDao();
		Project project = dao.find(connection.getProjectId());
		project.setConfiguration(configuration);
		dao.update(project);
	}

}
