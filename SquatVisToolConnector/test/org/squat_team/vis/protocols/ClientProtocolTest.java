package org.squat_team.vis.protocols;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.squat_team.vis.connector.protocols.AbstractClientProtocol;
import org.squat_team.vis.connector.protocols.IClientProtocol;

/**
 * Provides mocking for remote communication behavior for tests on classes that
 * are based on {@link AbstractClientProtocol}. <br/>
 * Expects the protocol to be created within {@link PowerMockito#spy(Class)}.
 * {@link #mockProtocol()} has to be called before executing the protocol.
 */
@RunWith(PowerMockRunner.class)
public abstract class ClientProtocolTest {
	protected ObjectInputStream in;
	protected ObjectOutputStream out;
	protected IClientProtocol<?> protocol;
	protected List<Object> output = new ArrayList<Object>();

	@Before
	public void initialize() throws Exception {
		clear();
		mockStreams();
	}

	/**
	 * Clears the test-case specific state of the test class.
	 */
	private void clear() {
		in = null;
		out = null;
		protocol = null;
		output.clear();
	}

	/**
	 * Mocks the input and output streams.
	 * 
	 * @throws IOException should never happen
	 */
	private void mockStreams() throws IOException {
		in = PowerMockito.mock(ObjectInputStream.class);
		out = PowerMockito.mock(ObjectOutputStream.class);
		PowerMockito.doAnswer(new OutputTracker(output)).when(out).writeObject(Mockito.any());
	}

	/**
	 * Mocks the remote communication behavior of the protocol. Expects the
	 * {@link #protocol} field to be filled with a PowerMockito-Spy-Object.
	 * 
	 * @throws Exception should never happen
	 */
	protected void mockProtocol() throws Exception {
		PowerMockito.doNothing().when(protocol, "initializeStreams");
		PowerMockito.doNothing().when(protocol, "close");
		PowerMockito.doAnswer(new MockProtocolInitializer(in, out)).when(protocol, "establishConnection");
		Whitebox.invokeMethod(protocol, "initializeProtocolHelper", in, out);
	}

	/**
	 * Initializes mocked Input- and Outputstream in the protocol.
	 */
	private class MockProtocolInitializer implements Answer<Boolean> {
		private ObjectInputStream in;
		private ObjectOutputStream out;

		public MockProtocolInitializer(ObjectInputStream in, ObjectOutputStream out) {
			this.in = in;
			this.out = out;
		}

		@Override
		public Boolean answer(InvocationOnMock invocation) throws Throwable {
			IClientProtocol<?> protocol = (IClientProtocol<?>) invocation.getMock();
			Whitebox.setInternalState(protocol, "in", in);
			Whitebox.setInternalState(protocol, "out", out);
			return true;
		}
	}

	/**
	 * Stores the communication output of the protocol in a list
	 */
	private class OutputTracker implements Answer<Boolean> {
		private List<Object> output = new ArrayList<Object>();

		public OutputTracker(List<Object> output) {
			this.output = output;
		}

		@Override
		public Boolean answer(InvocationOnMock invocation) throws Throwable {
			output.add(invocation.getArguments()[0]);
			return true;
		}

	}

}
