package org.squat_team.vis.protocols;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import org.squat_team.vis.connector.protocols.LevelResponseClientProtocol;

/**
 * Tests the {@link LevelResponseClientProtocol}
 */
@PrepareForTest(LevelResponseClientProtocol.class)
public class LevelResponseClientProtocolTest extends ClientProtocolTest {
	private ProjectConnector projectConnector;
	private Integer levelNumber;
	private List<Long> candidateIds;

	/**
	 * Cleans test-case-specific fields of the test class.
	 */
	@Before
	public void clean() {
		this.projectConnector = null;
		this.levelNumber = null;
		this.candidateIds = null;
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
		levelNumber = 0;
		projectConnector = new ProjectConnector(123);
		protocol = PowerMockito.spy(new LevelResponseClientProtocol(levelNumber, projectConnector));
	}

	private void mockCorrectInputStreamResponses() throws ClassNotFoundException, IOException {
		candidateIds = new ArrayList<>();
		candidateIds.add(0l);
		candidateIds.add(1l);
		candidateIds.add(2l);
		PowerMockito.when(in.readObject()).thenReturn(candidateIds);
	}

	private void mockWrongInputStreamResponses() throws ClassNotFoundException, IOException {
		Message message = new Message(MessageType.REQUEST_LEVEL_RESPONSE, null);
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
		assertEquals(MessageType.REQUEST_LEVEL_RESPONSE, message.getType());
	}

	private void runAndAssert() throws ConnectionFailure, ProtocolFailure, InvalidRequestException {
		Object response = protocol.call();
		assertEquals(candidateIds, response);
		assertEquals(2, output.size());
		checKMessage(output.get(0));
		assertEquals(levelNumber, output.get(1));
	}

}
