package org.squat_team.vis.connector.protocol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;

import org.jboss.weld.junit4.WeldInitiator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;
import org.squat_team.vis.connector.protocols.IServerProtocol;
import org.squat_team.vis.connector.protocols.LevelResponseServerProtocol;
import org.squat_team.vis.data.controllers.LevelController;
import org.squat_team.vis.data.controllers.ProjectController;
import org.squat_team.vis.data.daos.LevelDao;
import org.squat_team.vis.data.daos.ProjectDao;
import org.squat_team.vis.data.data.Candidate;
import org.squat_team.vis.data.data.Level;
import org.squat_team.vis.data.data.Project;
import org.squat_team.vis.session.LevelResponseService;
import org.squat_team.vis.session.SessionInfo;
import org.squat_team.vis.util.ResponseManager;

/**
 * Tests the {@link LevelResponseServerProtocol} and its importers.
 */
@PrepareForTest(LevelResponseServerProtocol.class)
public class LevelResponseServerProtocolTest extends AbstractServerProtocolTest {
	private static final int MAX_WAITING_COUNT = 100;
	private static final int LEVEL_INDEX = 1;
	private static Project project;
	private ProjectDao projectDao;
	private LevelDao levelDao;

	// Initiates CDI classes
	@Rule
	public WeldInitiator weld = WeldInitiator
			.from(ProjectController.class, LevelController.class, ResponseManager.class, SessionInfo.class,
					LevelResponseService.class, LevelResponseServerProtocolTest.class)
			.activate(SessionScoped.class, RequestScoped.class).build();

	/**
	 * The currently active project in {@link SessionInfo} have to be set.
	 */
	@Before
	public void setSessionInfoProject() {
		weld.select(SessionInfo.class).get().setSelectedProject(PROJECT_MOCK_ID);
	}

	/**
	 * Extends the mocked {@link AbstractServerProtocolTest#connectorService}.
	 */
	@Before
	public void extendConnectorServiceMock() {
		connectorService.setResponseManager(weld.select(ResponseManager.class).get());
		connectorService.setProjectDao(weld.select(ProjectDao.class).get());
	}

	/**
	 * Creates mocked CDI {@link ProjectDao}
	 * 
	 * @return the mocked dao
	 */
	@Produces
	public ProjectDao produceProjectDao() {
		if (project == null) {
			createProject();
		}
		projectDao = Mockito.mock(ProjectDao.class);
		Mockito.when(projectDao.find(PROJECT_MOCK_ID)).thenReturn(project);
		return projectDao;
	}

	/**
	 * Creates mocked CDI {@link LevelDao}
	 * 
	 * @return the mocked dao
	 */
	@Produces
	public LevelDao produceLevelDao() {
		levelDao = Mockito.mock(LevelDao.class);
		return levelDao;
	}

	/**
	 * Clean the static variable between tests. This is necessary to have different
	 * objects between the tests.
	 */
	@Before
	public void clean() {
		project = null;
	}

	/**
	 * Mocks input data
	 */
	private void mockInputStream() throws ClassNotFoundException, IOException {
		PowerMockito.when(in.readObject()).thenReturn(LEVEL_INDEX);
	}

	/**
	 * A test case in which the response is not ready yet when the protocol gets
	 * started, but is available after some time.
	 */
	@Test
	public synchronized void protocolHasToWaitForResponseTest()
			throws InterruptedException, ClassNotFoundException, IOException {
		mockInputStream();
		executeProtocolWithLateResponse();
		checkResults();
	}

	/**
	 * A test case in which the response is already available in the database when
	 * the protocol gets started.
	 */
	@Test
	public synchronized void protocolAlreadyHasResponseTest()
			throws InterruptedException, ClassNotFoundException, IOException {
		mockInputStream();
		executeProtocolWithEarlyResponse();
		checkResults();
	}

	/**
	 * First executes the protocol, then provide the response when the protocol
	 * waits for it.
	 */
	private void executeProtocolWithLateResponse() throws InterruptedException {
		LevelResponseServerProtocol protocol = new LevelResponseServerProtocol(in, out, connectorService,
				projectConnector);
		ProtocolThread protocolThread = new ProtocolThread(protocol);
		protocolThread.start();
		waitUntilThreadWaits(protocolThread);
		assertEquals(0, output.size());
		weld.select(LevelResponseService.class).get().respondCompleteLastLevel();
		waitUntilThreadTerminates(protocolThread);
	}

	/**
	 * First provide the response, then execute the protocol.
	 */
	private void executeProtocolWithEarlyResponse() throws InterruptedException {
		weld.select(LevelResponseService.class).get().respondCompleteLastLevel();
		LevelResponseServerProtocol protocol = new LevelResponseServerProtocol(in, out, connectorService,
				projectConnector);
		ProtocolThread protocolThread = new ProtocolThread(protocol);
		protocolThread.start();
		waitUntilThreadTerminates(protocolThread);
	}

	/**
	 * Checks the results, if they are returned correctly.
	 */
	private void checkResults() {
		assertEquals(1, output.size());
		Object object = output.get(0);
		assertTrue(object instanceof List);
		@SuppressWarnings("unchecked")
		List<Long> list = (List<Long>) object;
		assertEquals(2, list.size());
		assertEquals(1l, list.get(0), 0.0001);
		assertEquals(2l, list.get(1), 0.0001);
	}

	/**
	 * Lets the current thread wait until the specified thread waits.
	 * 
	 * @param thread the thread that should be waited for.
	 */
	private void waitUntilThreadWaits(Thread thread) throws InterruptedException {
		int waitedCounter = 0;
		while (!thread.getState().equals(Thread.State.WAITING)) {
			checkTimeout(waitedCounter);
			wait(10);
			waitedCounter++;
		}
	}

	/**
	 * Lets the current thread wait until the specified thread terminates.
	 * 
	 * @param thread the thread that should be waited for.
	 */
	private void waitUntilThreadTerminates(Thread thread) throws InterruptedException {
		int waitedCounter = 0;
		while (thread.isAlive()) {
			checkTimeout(waitedCounter);
			wait(10);
			waitedCounter++;
		}
	}

	/**
	 * Checks if thread waited too often.
	 * 
	 * @param waited how often the thread waited.
	 */
	private void checkTimeout(int waited) {
		if (waited > MAX_WAITING_COUNT) {
			fail();
		}
	}

	/**
	 * Creates a (mocked) project with id
	 * {@link AbstractServerProtocolTest#PROJECT_MOCK_ID} and two levels.
	 */
	private void createProject() {
		List<Level> levels = createLevels();
		project = PowerMockito.mock(Project.class);
		PowerMockito.when(project.getId()).thenReturn(PROJECT_MOCK_ID);
		PowerMockito.when(project.getLevels()).thenReturn(levels);
	}

	/**
	 * Creates two levels.
	 * 
	 * @return the levels
	 */
	private List<Level> createLevels() {
		List<Level> levels = new ArrayList<Level>();
		levels.add(createLevel0());
		levels.add(createLevel1());
		return levels;
	}

	/**
	 * Creates a level with one candidate.
	 * 
	 * @return the created level
	 */
	private Level createLevel0() {
		Level level0 = new Level();
		Candidate candidate1 = new Candidate();
		candidate1.setCandidateId(0l);
		level0.getCandidates().add(candidate1);
		return level0;
	}

	/**
	 * Creates a level with two candidates.
	 * 
	 * @return the created level
	 */
	private Level createLevel1() {
		Level level1 = new Level();
		Candidate candidate2 = new Candidate();
		Candidate candidate3 = new Candidate();
		candidate2.setCandidateId(1l);
		candidate3.setCandidateId(2l);
		level1.getCandidates().add(candidate2);
		level1.getCandidates().add(candidate3);
		return level1;
	}

	/**
	 * Executes a protocol as own thread.
	 */
	private class ProtocolThread extends Thread {
		private final IServerProtocol protocol;

		public ProtocolThread(IServerProtocol protocol) {
			this.protocol = protocol;
		}

		@Override
		public void run() {
			try {
				protocol.execute();
			} catch (ProtocolFailure | IOException | InvalidRequestException e) {
				e.printStackTrace();
				fail();
			}
		}
	}
}
