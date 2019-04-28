package org.squat_team.vis.test;

import org.squat_team.vis.connector.Connection;
import org.squat_team.vis.connector.data.CGoal;
import org.squat_team.vis.connector.data.CLevel;
import org.squat_team.vis.connector.data.CProject;
import org.squat_team.vis.connector.data.CStatus;
import org.squat_team.vis.connector.data.CToolConfiguration;
import org.squat_team.vis.connector.exceptions.ConnectionFailure;
import org.squat_team.vis.connector.exceptions.HostUnreachableException;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;
import org.squat_team.vis.connector.protocols.NewLevelClientProtocol;
import org.squat_team.vis.connector.protocols.NewProjectClientProtocol;
import org.squat_team.vis.connector.protocols.UpdateStatusClientProtocol;

public class TestClient {

	private static Connection connection;

	public static void main(String[] args) throws HostUnreachableException, ConnectionFailure, ProtocolFailure,
			InvalidRequestException, InterruptedException {
		System.out.println("STARTING TEST CLIENT");
		runStandardProcedure();
		runUpdateStuckProcedure();
		System.out.println("SHUTTING DOWN TEST CLIENT");
	}
	
	private static void runStandardProcedure() throws HostUnreachableException, ConnectionFailure, ProtocolFailure, InvalidRequestException, InterruptedException {
		makeNewProjectRequest();
		sleep(1000);
		makeStatusUpdate1();
		sleep(1000);
		sendLevel0();
		sleep(1000);
		sendLevel1();
	}
	
	private static void runUpdateStuckProcedure() throws HostUnreachableException, ConnectionFailure, ProtocolFailure, InvalidRequestException, InterruptedException {
		makeNewProjectRequest();
		sleep(1000);
		makeStatusUpdate1();
	}

	private static void makeNewProjectRequest()
			throws HostUnreachableException, ConnectionFailure, ProtocolFailure, InvalidRequestException {
		System.out.println("REQUESTING NEW PROJECT");
		TestNewProjectDataProvider testDataProvider = new TestNewProjectDataProvider();
		CProject project = testDataProvider.getProject();
		CGoal goal = testDataProvider.getGoal();
		CToolConfiguration configuration = testDataProvider.getConfiguration();
		NewProjectClientProtocol protocol = new NewProjectClientProtocol(project, configuration, goal);
		connection = protocol.call();
		System.out.println("FINSIHED REQUEST");
		System.out.println("RECEIVED CONNECTION WITH ID: " + connection.getProjectId());
	}

	private static void makeStatusUpdate1()
			throws HostUnreachableException, ConnectionFailure, ProtocolFailure, InvalidRequestException {
		System.out.println("UPDATING STATUS");
		TestStatusUpdateDataProvider testDataProvider = new TestStatusUpdateDataProvider();
		CStatus status = testDataProvider.getStatus1of5();
		UpdateStatusClientProtocol protocol = new UpdateStatusClientProtocol(status, connection);
		boolean success = protocol.call();
		System.out.println("UPDATE STATUS SUCCESSFUL: " + success);
	}

	private static void sleep(long timeInMillis) throws InterruptedException {
		System.out.println("SLEEP FOR " + timeInMillis + " MILLISECONDS");
		Thread.sleep(timeInMillis);
		System.out.println("FINISHED SLEEP");
	}

	private static void sendLevel0()
			throws HostUnreachableException, ConnectionFailure, ProtocolFailure, InvalidRequestException {
		System.out.println("SEND NEW LEVEL");
		TestNewLevelDataProvider testDataProvider = new TestNewLevelDataProvider();
		CLevel level = testDataProvider.getLevel0();
		NewLevelClientProtocol protocol = new NewLevelClientProtocol(level, connection);
		boolean success = protocol.call();
		System.out.println("SENDING LEVEL SUCCESSFUL: " + success);
	}

	private static void sendLevel1()
			throws HostUnreachableException, ConnectionFailure, ProtocolFailure, InvalidRequestException {
		System.out.println("SEND NEW LEVEL");
		TestNewLevelDataProvider testDataProvider = new TestNewLevelDataProvider();
		CLevel level = testDataProvider.getLevel1();
		NewLevelClientProtocol protocol = new NewLevelClientProtocol(level, connection);
		boolean success = protocol.call();
		System.out.println("SENDING LEVEL SUCCESSFUL: " + success);
	}

}
