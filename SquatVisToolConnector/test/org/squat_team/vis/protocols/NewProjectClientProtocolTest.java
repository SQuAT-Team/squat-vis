package org.squat_team.vis.protocols;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.squat_team.vis.connector.Message;
import org.squat_team.vis.connector.MessageType;
import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.data.CGoal;
import org.squat_team.vis.connector.data.CProject;
import org.squat_team.vis.connector.data.CToolConfiguration;
import org.squat_team.vis.connector.exceptions.ConnectionFailure;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;
import org.squat_team.vis.connector.protocols.NewProjectClientProtocol;

/**
 * Tests {@link NewProjectClientProtocol}.
 */
@PrepareForTest(NewProjectClientProtocol.class)
public class NewProjectClientProtocolTest extends ClientProtocolTest {
	private CProject project;
	private CToolConfiguration configuration;
	private CGoal goal;
	private ProjectConnector projectConnector;

	/**
	 * Cleans test-case-specific fields of the test class.
	 */
	@Before
	public void clean() {
		this.project = null;
		this.configuration = null;
		this.goal = null;
	}

	/**
	 * Tests the standard case in which the protocol executes successfully.
	 * 
	 * @throws Exception
	 */
	@Test
	public void successfulProtocolRunTest() throws Exception {
		initializeProtocol();
		mockProtocol();
		mockCorrectInputStreamResponses();
		runAndAssert();
	}

	/**
	 * Tests the case in which the protocol receives something else than expected.
	 * 
	 * @throws Exception
	 */
	@Test(expected = ProtocolFailure.class)
	public void receiveWrongResponseTest() throws Exception {
		initializeProtocol();
		mockProtocol();
		mockWrongInputStreamResponses();
		protocol.call();
	}

	/**
	 * Tests the case in which the protocol receives an exception from the server.
	 * 
	 * @throws Exception
	 */
	@Test(expected = InvalidRequestException.class)
	public void receiveExceptionResponseTest() throws Exception {
		initializeProtocol();
		mockProtocol();
		mockExceptionInputStreamResponses();
		protocol.call();
	}

	private void initializeProtocol() {
		project = new CProject("Test Project");
		configuration = new CToolConfiguration("Test Configuration");
		goal = new CGoal();
		protocol = PowerMockito.spy(new NewProjectClientProtocol(project, configuration, goal));
	}

	private void mockCorrectInputStreamResponses() throws ClassNotFoundException, IOException {
		projectConnector = new ProjectConnector(42);
		PowerMockito.when(in.readObject()).thenReturn(projectConnector);
	}

	private void mockWrongInputStreamResponses() throws ClassNotFoundException, IOException {
		Message message = new Message(MessageType.DECLINE, null);
		PowerMockito.when(in.readObject()).thenReturn(message);
	}

	private void mockExceptionInputStreamResponses() throws ClassNotFoundException, IOException {
		Message message = new Message(MessageType.DECLINE, null);
		message.setException(new Exception("Test"));
		PowerMockito.when(in.readObject()).thenReturn(message);
	}

	private void checKMessage(Object object) {
		assertTrue(object instanceof Message);
		Message message = (Message) object;
		assertEquals(MessageType.REQUEST_NEW_PROJECT, message.getType());
	}

	private void runAndAssert() throws ConnectionFailure, ProtocolFailure, InvalidRequestException {
		ProjectConnector receivedProjectConnector = protocol.call();
		assertEquals(projectConnector, receivedProjectConnector);
		assertEquals(4, output.size());
		checKMessage(output.get(0));
		assertEquals(this.project, output.get(1));
		assertEquals(this.configuration, output.get(2));
		assertEquals(this.goal, output.get(3));
	}

}
