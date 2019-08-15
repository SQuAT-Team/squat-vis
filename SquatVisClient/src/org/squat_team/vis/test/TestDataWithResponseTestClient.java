package org.squat_team.vis.test;

import java.io.IOException;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.exceptions.ConnectionFailure;
import org.squat_team.vis.connector.exceptions.HostUnreachableException;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;

public class TestDataWithResponseTestClient extends AbstractTestData {

	public TestDataWithResponseTestClient(ProjectConnector projectConnector) {
		super(projectConnector);
	}

	public static void main(String[] args) throws HostUnreachableException, ConnectionFailure, ProtocolFailure,
			InvalidRequestException, InterruptedException, IOException {
		run();
	}

	public static void run() throws HostUnreachableException, ConnectionFailure, ProtocolFailure,
			InvalidRequestException, InterruptedException, IOException {
		System.out.println("STARTING TEST CLIENT");
		runStandardProcedureWithResponse();
		System.out.println("SHUTTING DOWN TEST CLIENT");
	}

	private static void runStandardProcedureWithResponse() throws HostUnreachableException, ConnectionFailure,
			ProtocolFailure, InvalidRequestException, InterruptedException {
		makeNewProjectRequest();
		sleep(1000);
		makeStatusUpdate1();
		sleep(1000);
		sendLevel0(false);
		waitForResponse(0);
		sleep(1000);
		makeStatusUpdate1();
		sleep(1000);
		sendLevel1(false);
		waitForResponse(1);
		sleep(5000);
		terminateProject();
	}

}
