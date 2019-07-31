package org.squat_team.vis.test;

import java.io.IOException;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.data.CGoal;
import org.squat_team.vis.connector.data.CLevel;
import org.squat_team.vis.connector.data.CProject;
import org.squat_team.vis.connector.data.CToolConfiguration;
import org.squat_team.vis.connector.exceptions.ConnectionFailure;
import org.squat_team.vis.connector.exceptions.HostUnreachableException;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;
import org.squat_team.vis.connector.protocols.NewLevelClientProtocol;
import org.squat_team.vis.connector.protocols.NewProjectClientProtocol;
import org.squat_team.vis.connector.protocols.ProjectTerminatedClientProtocol;
import org.squat_team.vis.test.importer.IImporter;

public abstract class AbstractTestClient {
	private ProjectConnector projectConnector;

	protected abstract IImporter getImporter();

	protected abstract int getLevelStartIndex();

	protected abstract int getLevelEndIndex();
	
	public void mainMethod() throws HostUnreachableException, ConnectionFailure, ProtocolFailure,
			InvalidRequestException, IOException, InterruptedException {
		System.out.println("STARTING SQUAT TEST CLIENT");
		sendData();
		System.out.println("SHUTTING DOWN SQUAT TEST CLIENT");
	}

	private void sendData() throws HostUnreachableException, ConnectionFailure, ProtocolFailure,
			InvalidRequestException, IOException, InterruptedException {
		makeNewProjectRequest();
		sleep(10000);
		sendLevels();
		terminateProject();
	}

	private void makeNewProjectRequest()
			throws HostUnreachableException, ConnectionFailure, ProtocolFailure, InvalidRequestException, IOException {
		System.out.println("REQUESTING NEW PROJECT");
		CProject project = getImporter().importProject();
		CGoal goal = getImporter().importGoals();
		CToolConfiguration configuration = getImporter().importConfiguration();
		NewProjectClientProtocol protocol = new NewProjectClientProtocol(project, configuration, goal);
		projectConnector = protocol.call();
		System.out.println("FINSIHED REQUEST");
		System.out.println("RECEIVED CONNECTION WITH ID: " + projectConnector.getProjectId());
	}

	private void sleep(long timeInMillis) throws InterruptedException {
		System.out.println("SLEEP FOR " + timeInMillis + " MILLISECONDS");
		Thread.sleep(timeInMillis);
		System.out.println("FINISHED SLEEP");
	}

	private void sendLevels()
			throws IOException, ConnectionFailure, ProtocolFailure, InvalidRequestException, InterruptedException {
		for (CLevel level : getImporter().importLevels().subList(getLevelStartIndex(),
				getLevelEndIndex())) {
			System.out.println("SEND NEW LEVEL");
			NewLevelClientProtocol protocol = new NewLevelClientProtocol(level, projectConnector, true);
			boolean success = protocol.call();
			System.out.println("SENDING LEVEL SUCCESSFUL: " + success);
			sleep(10000);
		}
	}

	private void terminateProject() throws ConnectionFailure, ProtocolFailure, InvalidRequestException {
		System.out.println("TERMINATE PROJECT");
		ProjectTerminatedClientProtocol protocol = new ProjectTerminatedClientProtocol(projectConnector);
		boolean success = protocol.call();
		System.out.println("TERMINATE PROJECT SUCCESSFUL: " + success);
	}
}
