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
import org.squat_team.vis.connector.exceptions.ConnectionFailure;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;
import org.squat_team.vis.connector.protocols.NewLevelClientProtocol;
import org.squat_team.vis.connector.protocols.ProjectTerminatedClientProtocol;

/**
 * Tests {@link NewLevelClientProtocol}.
 */
@PrepareForTest(ProjectTerminatedClientProtocol.class)
public class ProjectTerminatedClientProtocolTest extends ClientProtocolTest {
	private ProjectConnector projectConnector;

	/**
	 * Cleans test-case-specific fields of the test class.
	 */
	@Before
	public void clean() {
		this.projectConnector = null;
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
		projectConnector = new ProjectConnector(123);
		protocol = PowerMockito.spy(new ProjectTerminatedClientProtocol(projectConnector));
	}

	private void mockCorrectInputStreamResponses() throws ClassNotFoundException, IOException {
		Message message = new Message(MessageType.ACCEPT, projectConnector);
		PowerMockito.when(in.readObject()).thenReturn(message);
	}

	private void mockWrongInputStreamResponses() throws ClassNotFoundException, IOException {
		Message message = new Message(MessageType.SEND_PROJECT_TERMINATED, null);
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
		assertEquals(MessageType.SEND_PROJECT_TERMINATED, message.getType());
	}

	private void runAndAssert() throws ConnectionFailure, ProtocolFailure, InvalidRequestException {
		Boolean response = (Boolean) protocol.call();
		assertEquals(true, response);
		assertEquals(1, output.size());
		checKMessage(output.get(0));
	}
}
