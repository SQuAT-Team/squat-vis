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
import org.squat_team.vis.test.testData.TestNewLevelDataProvider;
import org.squat_team.vis.test.testData.TestNewProjectDataProvider;
import org.squat_team.vis.test.testData.TestStatusUpdateDataProvider;

public abstract class AbstractTestData {
	private static final String EXPORT_DIRECTORY_PATH = "." + File.separator + "SquatVisExports";

	private static ProjectConnector projectConnector;

	public AbstractTestData(ProjectConnector projectConnector) {
		projectConnector = projectConnector;
	}

	protected static void makeNewProjectRequest()
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

	protected static void makeStatusUpdate1() throws HostUnreachableException, ConnectionFailure, ProtocolFailure {
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

	protected static void makeStatusUpdate2() throws HostUnreachableException, ConnectionFailure, ProtocolFailure {
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

	protected static void makeStatusUpdate3() throws HostUnreachableException, ConnectionFailure, ProtocolFailure {
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

	protected static void makeStatusUpdate4() throws HostUnreachableException, ConnectionFailure, ProtocolFailure {
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

	protected static void makeStatusUpdate5() throws HostUnreachableException, ConnectionFailure, ProtocolFailure {
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

	protected static void sleep(long timeInMillis) throws InterruptedException {
		System.out.println("SLEEP FOR " + timeInMillis + " MILLISECONDS");
		Thread.sleep(timeInMillis);
		System.out.println("FINISHED SLEEP");
	}

	protected static void sendLevel0(boolean noResponse)
			throws HostUnreachableException, ConnectionFailure, ProtocolFailure, InvalidRequestException {
		System.out.println("SEND NEW LEVEL");
		TestNewLevelDataProvider testDataProvider = new TestNewLevelDataProvider();
		CLevel level = testDataProvider.getLevel0();
		NewLevelClientProtocol protocol = new NewLevelClientProtocol(level, projectConnector, noResponse);
		boolean success = protocol.call();
		System.out.println("SENDING LEVEL SUCCESSFUL: " + success);
	}

	protected static void sendLevel1(boolean noResponse)
			throws HostUnreachableException, ConnectionFailure, ProtocolFailure, InvalidRequestException {
		System.out.println("SEND NEW LEVEL");
		TestNewLevelDataProvider testDataProvider = new TestNewLevelDataProvider();
		CLevel level = testDataProvider.getLevel1();
		NewLevelClientProtocol protocol = new NewLevelClientProtocol(level, projectConnector, noResponse);
		boolean success = protocol.call();
		System.out.println("SENDING LEVEL SUCCESSFUL: " + success);
	}

	protected static void terminateProject() throws ConnectionFailure, ProtocolFailure, InvalidRequestException {
		System.out.println("TERMINATE PROJECT");
		ProjectTerminatedClientProtocol protocol = new ProjectTerminatedClientProtocol(projectConnector);
		boolean success = protocol.call();
		System.out.println("TERMINATE PROJECT SUCCESSFUL: " + success);
	}

	protected static void waitForResponse(int levelNumber)
			throws ConnectionFailure, ProtocolFailure, InvalidRequestException {
		System.out.println("WAIT FOR RESPONSE");
		LevelResponseClientProtocol protocol = new LevelResponseClientProtocol(levelNumber, projectConnector);
		List<Long> success = protocol.call();
		System.out.println("RECEIVED RESPONSE");
		System.out.println("LEVEL RESPONSES: " + success);
	}

	protected static void exportData() throws IOException {
		System.out.println("EXPORT TO OTHER FORMATS");
		TestNewProjectDataProvider testProjectDataProvider = new TestNewProjectDataProvider();
		TestNewLevelDataProvider testLevelDataProvider = new TestNewLevelDataProvider();
		CsvExporter csvExporter = new CsvExporter(EXPORT_DIRECTORY_PATH);
		csvExporter.export(testProjectDataProvider.getProject(), testProjectDataProvider.getGoal(),
				testLevelDataProvider.getAllLevels(), testProjectDataProvider.getConfiguration());
	}
}
