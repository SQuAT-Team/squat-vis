package org.squat_team.vis.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.data.CGoal;
import org.squat_team.vis.connector.data.CLevel;
import org.squat_team.vis.connector.data.CProject;
import org.squat_team.vis.connector.data.CStatus;
import org.squat_team.vis.connector.data.CToolConfiguration;
import org.squat_team.vis.connector.exceptions.ConnectionFailure;
import org.squat_team.vis.connector.exceptions.HostUnreachableException;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;
import org.squat_team.vis.connector.protocols.LevelResponseClientProtocol;
import org.squat_team.vis.connector.protocols.NewLevelClientProtocol;
import org.squat_team.vis.connector.protocols.NewProjectClientProtocol;
import org.squat_team.vis.connector.protocols.ProjectTerminatedClientProtocol;
import org.squat_team.vis.connector.protocols.UpdateStatusClientProtocol;
import org.squat_team.vis.test.exporter.CsvExporter;

public class TestClient {
	private static final String EXPORT_DIRECTORY_PATH = "." + File.separator + "SquatVisExports";
	private static ProjectConnector projectConnector;

	public static void main(String[] args) throws HostUnreachableException, ConnectionFailure, ProtocolFailure,
			InvalidRequestException, InterruptedException, IOException {
		exportData();
		System.out.println("STARTING TEST CLIENT");
		runStandardProcedure();
		runUpdateStuckProcedure();
		System.out.println("SHUTTING DOWN TEST CLIENT");
	}

	private static void exportData() throws IOException {
		System.out.println("EXPORT TO OTHER FORMATS");
		TestNewProjectDataProvider testProjectDataProvider = new TestNewProjectDataProvider();
		TestNewLevelDataProvider testLevelDataProvider = new TestNewLevelDataProvider();
		CsvExporter csvExporter = new CsvExporter(EXPORT_DIRECTORY_PATH);
		csvExporter.export(testProjectDataProvider.getProject(), testProjectDataProvider.getGoal(),
				testLevelDataProvider.getAllLevels(), testProjectDataProvider.getConfiguration());
	}

	private static void runStandardProcedure() throws HostUnreachableException, ConnectionFailure, ProtocolFailure,
			InvalidRequestException, InterruptedException {
		makeNewProjectRequest();
		sleep(1000);
		makeStatusUpdate1();
		sleep(1000);
		sendLevel0();
		sleep(1000);
		makeStatusUpdate1();
		sleep(1000);
		sendLevel1();
		sleep(5000);
		terminateProject();
	}

	private static void runUpdateStuckProcedure() throws HostUnreachableException, ConnectionFailure, ProtocolFailure,
			InvalidRequestException, InterruptedException {
		makeNewProjectRequest();
		sleep(10000);
		makeStatusUpdate1();
		sleep(10000);
		makeStatusUpdate2();
		sleep(10000);
		makeStatusUpdate3();
		sleep(10000);
		makeStatusUpdate4();
		sleep(10000);
		makeStatusUpdate5();
	}

	private static void makeNewProjectRequest()
			throws HostUnreachableException, ConnectionFailure, ProtocolFailure, InvalidRequestException {
		System.out.println("REQUESTING NEW PROJECT");
		TestNewProjectDataProvider testDataProvider = new TestNewProjectDataProvider();
		CProject project = testDataProvider.getProject();
		CGoal goal = testDataProvider.getGoal();
		CToolConfiguration configuration = testDataProvider.getConfiguration();
		NewProjectClientProtocol protocol = new NewProjectClientProtocol(project, configuration, goal);
		projectConnector = protocol.call();
		System.out.println("FINSIHED REQUEST");
		System.out.println("RECEIVED CONNECTION WITH ID: " + projectConnector.getProjectId());
	}

	private static void makeStatusUpdate1() throws HostUnreachableException, ConnectionFailure, ProtocolFailure {
		try {
			System.out.println("UPDATING STATUS");
			TestStatusUpdateDataProvider testDataProvider = new TestStatusUpdateDataProvider();
			CStatus status = testDataProvider.getStatus1of5();
			UpdateStatusClientProtocol protocol = new UpdateStatusClientProtocol(status, projectConnector);
			boolean success = protocol.call();
			System.out.println("UPDATE STATUS SUCCESSFUL: " + success);
		} catch (InvalidRequestException e) {
			e.printStackTrace();
		}
	}

	private static void makeStatusUpdate2() throws HostUnreachableException, ConnectionFailure, ProtocolFailure {
		try {
			System.out.println("UPDATING STATUS");
			TestStatusUpdateDataProvider testDataProvider = new TestStatusUpdateDataProvider();
			CStatus status = testDataProvider.getStatus2of5();
			UpdateStatusClientProtocol protocol = new UpdateStatusClientProtocol(status, projectConnector);
			boolean success = protocol.call();
			System.out.println("UPDATE STATUS SUCCESSFUL: " + success);
		} catch (InvalidRequestException e) {
			e.printStackTrace();
		}
	}

	private static void makeStatusUpdate3() throws HostUnreachableException, ConnectionFailure, ProtocolFailure {
		try {
			System.out.println("UPDATING STATUS");
			TestStatusUpdateDataProvider testDataProvider = new TestStatusUpdateDataProvider();
			CStatus status = testDataProvider.getStatus3of5();
			UpdateStatusClientProtocol protocol = new UpdateStatusClientProtocol(status, projectConnector);
			boolean success = protocol.call();
			System.out.println("UPDATE STATUS SUCCESSFUL: " + success);
		} catch (InvalidRequestException e) {
			e.printStackTrace();
		}
	}

	private static void makeStatusUpdate4() throws HostUnreachableException, ConnectionFailure, ProtocolFailure {
		try {
			System.out.println("UPDATING STATUS");
			TestStatusUpdateDataProvider testDataProvider = new TestStatusUpdateDataProvider();
			CStatus status = testDataProvider.getStatus4of5();
			UpdateStatusClientProtocol protocol = new UpdateStatusClientProtocol(status, projectConnector);
			boolean success = protocol.call();
			System.out.println("UPDATE STATUS SUCCESSFUL: " + success);
		} catch (InvalidRequestException e) {
			e.printStackTrace();
		}
	}

	private static void makeStatusUpdate5() throws HostUnreachableException, ConnectionFailure, ProtocolFailure {
		try {
			System.out.println("UPDATING STATUS");
			TestStatusUpdateDataProvider testDataProvider = new TestStatusUpdateDataProvider();
			CStatus status = testDataProvider.getStatus5of5();
			UpdateStatusClientProtocol protocol = new UpdateStatusClientProtocol(status, projectConnector);
			boolean success = protocol.call();
			System.out.println("UPDATE STATUS SUCCESSFUL: " + success);
		} catch (InvalidRequestException e) {
			e.printStackTrace();
		}
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
		NewLevelClientProtocol protocol = new NewLevelClientProtocol(level, projectConnector, true);
		boolean success = protocol.call();
		System.out.println("SENDING LEVEL SUCCESSFUL: " + success);
	}

	private static void sendLevel1()
			throws HostUnreachableException, ConnectionFailure, ProtocolFailure, InvalidRequestException {
		System.out.println("SEND NEW LEVEL");
		TestNewLevelDataProvider testDataProvider = new TestNewLevelDataProvider();
		CLevel level = testDataProvider.getLevel1();
		NewLevelClientProtocol protocol = new NewLevelClientProtocol(level, projectConnector, false);
		boolean success = protocol.call();
		System.out.println("SENDING LEVEL SUCCESSFUL: " + success);
	}

	private static void terminateProject() throws ConnectionFailure, ProtocolFailure, InvalidRequestException {
		System.out.println("TERMINATE PROJECT");
		ProjectTerminatedClientProtocol protocol = new ProjectTerminatedClientProtocol(projectConnector);
		boolean success = protocol.call();
		System.out.println("TERMINATE PROJECT SUCCESSFUL: " + success);
	}

	private static void waitForResponse(int levelNumber)
			throws ConnectionFailure, ProtocolFailure, InvalidRequestException {
		System.out.println("WAIT FOR RESPONSE");
		LevelResponseClientProtocol protocol = new LevelResponseClientProtocol(levelNumber, projectConnector);
		List<Long> success = protocol.call();
		System.out.println("RECEIVED RESPONSE");
		System.out.println("LEVEL RESPONSES: " + success);
	}

}