package org.squat_team.vis.data.data;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.squat_team.vis.data.data.Status.StatusType;

/**
 * Tests the {@link Status}
 */
public class StatusTest {

	/**
	 * Simulates a normal run of the visualization tool in which one level is sent
	 * and then the project terminates.
	 */
	@Test
	public void statusTypeTest() {
		Status status = new Status();
		assertEquals(StatusType.RUNNING, status.getType());
		assertEquals(0d, status.getToolProgress().doubleValue(), 0.0001);
		assertEquals(0d, status.getVisProgress().doubleValue(), 0.0001);
		status.notifyNewLevel();
		assertEquals(StatusType.RUNNING, status.getType());
		status.setToolProgress(0.2d);
		status.setToolMessage("Test run");
		assertEquals(StatusType.RUNNING, status.getType());
		status.notifyLevelFinished();
		assertEquals(StatusType.RUNNING, status.getType());
		assertEquals(1d, status.getToolProgress().doubleValue(), 0.0001);
		assertEquals(0d, status.getVisProgress().doubleValue(), 0.0001);
		status.notifyLevelImported();
		assertEquals(StatusType.WAITING, status.getType());
		assertEquals(1d, status.getToolProgress().doubleValue(), 0.0001);
		assertEquals(1d, status.getVisProgress().doubleValue(), 0.0001);
		assertEquals(100, status.getTotalProgress());
		status.notifyNewLevel();
		assertEquals(StatusType.RUNNING, status.getType());
		status.notifyTerminated();
		assertEquals(StatusType.TERMINATED, status.getType());
		assertEquals(1d, status.getToolProgress().doubleValue(), 0.0001);
		assertEquals(1d, status.getVisProgress().doubleValue(), 0.0001);
		assertEquals(100, status.getTotalProgress());
	}

	/**
	 * Simulates the situation in which an exception is reported and the project is
	 * marked as failed
	 */
	@Test
	public void statusExceptionTest() {
		Status status = new Status();
		status.notifyException();
		assertEquals(StatusType.EXCEPTION, status.getType());
	}

	/**
	 * Simulates the situation in which the project did not receive an update within
	 * the last 3 minutes.
	 */
	@Test
	public void statusTimeoutTest() {
		Status status = new Status();
		assertEquals(StatusType.RUNNING, status.getType());
		status.notifyNewLevel();
		assertEquals(StatusType.RUNNING, status.getType());
		Date now = new Date();
		long threeMinutes = 180000;
		Date threeMinutesBefore = new Date(now.getTime() - threeMinutes);
		status.setLastUpdate(threeMinutesBefore);
		assertEquals(StatusType.EXCEPTION, status.getType());
	}

}
