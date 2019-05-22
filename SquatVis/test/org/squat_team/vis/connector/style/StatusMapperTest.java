package org.squat_team.vis.connector.style;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.squat_team.vis.data.data.Status.StatusType;

/**
 * Test the {@link StatusMapper}
 */
public class StatusMapperTest {
	StatusMapper statusMapper;

	@Before
	public void initialize() {
		statusMapper = new StatusMapper();
	}

	@Test
	public void testStatusRunning() {
		StatusType type = StatusType.RUNNING;
		String cssClass = statusMapper.map(type);
		assertEquals("running", cssClass);
	}

	@Test
	public void testStatusWaiting() {
		StatusType type = StatusType.WAITING;
		String cssClass = statusMapper.map(type);
		assertEquals("waiting", cssClass);
	}

	@Test
	public void testStatusException() {
		StatusType type = StatusType.EXCEPTION;
		String cssClass = statusMapper.map(type);
		assertEquals("exception", cssClass);
	}

	@Test
	public void testStatusTerminated() {
		StatusType type = StatusType.TERMINATED;
		String cssClass = statusMapper.map(type);
		assertEquals("terminated", cssClass);
	}

	@Test
	public void testDescriptionReturnsText() {
		StatusType type = StatusType.RUNNING;
		String cssClass = statusMapper.map(type);
		assertFalse(cssClass.isEmpty());
		type = StatusType.WAITING;
		cssClass = statusMapper.map(type);
		assertFalse(cssClass.isEmpty());
		type = StatusType.EXCEPTION;
		cssClass = statusMapper.map(type);
		assertFalse(cssClass.isEmpty());
		type = StatusType.TERMINATED;
		cssClass = statusMapper.map(type);
		assertFalse(cssClass.isEmpty());
	}

	@Test(expected=NullPointerException.class)
	public void testNullInput() {
		String cssClass = statusMapper.map(null);
		assertNull(cssClass);
	}

}
