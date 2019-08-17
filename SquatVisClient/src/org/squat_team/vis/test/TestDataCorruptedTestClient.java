package org.squat_team.vis.test;

import java.io.IOException;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.exceptions.ConnectionFailure;
import org.squat_team.vis.connector.exceptions.HostUnreachableException;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;

public class TestDataCorruptedTestClient extends AbstractTestData {

	public TestDataCorruptedTestClient(ProjectConnector projectConnector) {
		super(projectConnector);
	}

	public static void main(String[] args) throws HostUnreachableException, ConnectionFailure, ProtocolFailure,
			InvalidRequestException, InterruptedException, IOException {
		run();
	}

	public static void run() throws HostUnreachableException, ConnectionFailure, ProtocolFailure,
			InvalidRequestException, InterruptedException, IOException {
		System.out.println("STARTING TEST CLIENT");
		runUpdateStuckProcedure();
		System.out.println("SHUTTING DOWN TEST CLIENT");
	}

	private static void runUpdateStuckProcedure() throws HostUnreachableException, ConnectionFailure, ProtocolFailure,
			InvalidRequestException, InterruptedException {
		makeNewProjectRequest(" Corrupted");
		sleep(2000);
		makeStatusUpdate1();
		sleep(2000);
		makeStatusUpdate2();
		sleep(2000);
		makeStatusUpdate3();
		sleep(2000);
		makeStatusUpdate4();
		sleep(2000);
		makeStatusUpdate5();
	}

}
