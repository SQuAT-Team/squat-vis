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

/**
 * This protocol handles an incoming request to create a new project. <br/>
 * It is the counterpart to {@link NewProjectClientProtocol}.
 */
public class NewProjectServerProtocol extends AbstractServerProtocol {
	private CProject cProject;
	private CToolConfiguration cConfiguration;
	private CGoal cGoal;

	/**
	 * Creates a new protocol.
	 * 
	 * @param in               the connection from client to server.
	 * @param out              the connection from server to client.
	 * @param connectorService Provides daos for the import
	 */
	public NewProjectServerProtocol(ObjectInputStream in, ObjectOutputStream out, ConnectorService connectorService) {
		super(in, out, connectorService, null);
	}

	@Override
	public void execute() throws ProtocolFailure, IOException, InvalidRequestException {
		receive();
		transform();
		respond();
	}

	/**
	 * Receives the data from the client.
	 * 
	 * @throws ProtocolFailure         if the actual communication deviates from the
	 *                                 protocol.
	 * @throws IOException             if an error occurs because of the
	 *                                 communication.
	 * @throws InvalidRequestException if the received objects are of an unexpected
	 *                                 type.
	 */
	private void receive() throws ProtocolFailure, IOException, InvalidRequestException {
		this.cProject = receive(CProject.class);
		this.cConfiguration = receive(CToolConfiguration.class);
		this.cGoal = receive(CGoal.class);
	}

	/**
	 * Imports the received data.
	 * 
	 * @throws InvalidRequestException if the received data violates constraints.
	 */
	private void transform() throws InvalidRequestException {
		Project project = (new ProjectImporter(connectorService)).transform(cProject);
		projectConnector = createProjectConnector(project);
		(new ToolConfigurationImporter(connectorService, projectConnector)).transform(cConfiguration);
		(new GoalImporter(connectorService, projectConnector)).transform(cGoal);
	}

	/**
	 * Send response to the client.
	 * 
	 * @throws IOException if an error occurs because of the communication.
	 */
	private void respond() throws IOException {
		send(projectConnector);
	}

	/**
	 * Creates a connector based on the imported project.
	 * 
	 * @param project the imported project
	 * @return the connector, identifies the project
	 */
	private ProjectConnector createProjectConnector(Project project) {
		long uniqueId = project.getId();
		return new ProjectConnector(uniqueId);
	}

	@Override
	public IPostProtocolHandler getPostProtocolHandler() {
		return new NewProjectPostProtocol(connectorService, projectConnector);
	}

}
