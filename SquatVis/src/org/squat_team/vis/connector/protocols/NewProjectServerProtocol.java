package org.squat_team.vis.connector.protocols;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.data.CGoal;
import org.squat_team.vis.connector.data.CProject;
import org.squat_team.vis.connector.data.CToolConfiguration;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;
import org.squat_team.vis.connector.importers.GoalImporter;
import org.squat_team.vis.connector.importers.ProjectImporter;
import org.squat_team.vis.connector.importers.ToolConfigurationImporter;
import org.squat_team.vis.connector.server.ConnectorService;
import org.squat_team.vis.data.data.Project;

public class NewProjectServerProtocol extends AbstractServerProtocol {
	private CProject cProject;
	private CToolConfiguration cConfiguration;
	private CGoal cGoal;

	public NewProjectServerProtocol(ObjectInputStream in, ObjectOutputStream out, ConnectorService connectorService) {
		super(in, out, connectorService, null);
	}

	@Override
	public void execute() throws ProtocolFailure, IOException, InvalidRequestException {
		receive();
		transform();
		respond();
	}

	private void receive() throws ProtocolFailure, IOException, InvalidRequestException {
		this.cProject = receive(CProject.class);
		this.cConfiguration = receive(CToolConfiguration.class);
		this.cGoal = receive(CGoal.class);
	}

	private void transform() throws InvalidRequestException {
		Project project = (new ProjectImporter(connectorService)).transform(cProject);
		projectConnector = createProjectConnector(project);
		(new ToolConfigurationImporter(connectorService, projectConnector)).transform(cConfiguration);
		(new GoalImporter(connectorService, projectConnector)).transform(cGoal);
	}

	private void respond() throws IOException {
		send(projectConnector);
	}

	private ProjectConnector createProjectConnector(Project project) {
		long uniqueId = project.getId();
		return new ProjectConnector(uniqueId);
	}

	@Override
	public IPostProtocolHandler getPostProtocolHandler() {
		return new NewProjectPostProtocol(connectorService, projectConnector);
	}

}
