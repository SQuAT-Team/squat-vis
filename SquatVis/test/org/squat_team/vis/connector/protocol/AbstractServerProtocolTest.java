package org.squat_team.vis.connector.protocol;

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
import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.protocols.AbstractServerProtocol;
import org.squat_team.vis.connector.server.ConnectorService;
import org.squat_team.vis.data.daos.CandidateDao;
import org.squat_team.vis.data.daos.GoalDao;
import org.squat_team.vis.data.daos.LevelDao;
import org.squat_team.vis.data.daos.ProjectDao;
import org.squat_team.vis.data.daos.ToolConfigurationDao;
import org.squat_team.vis.data.data.Project;

/**
 * Provides default methods and fields for tests on classes that extend
 * {@link AbstractServerProtocol}s.
 */
@RunWith(PowerMockRunner.class)
public class AbstractServerProtocolTest {
	protected static final long PROJECT_MOCK_ID = 123;

	protected ObjectInputStream in;
	protected ObjectOutputStream out;
	protected ProjectConnector projectConnector;
	protected ConnectorService connectorService;
	protected List<Object> output = new ArrayList<Object>();

	/**
	 * Mocks the input and output streams.
	 * 
	 * @throws IOException should never happen
	 */
	@Before
	public void mockStreams() throws IOException {
		in = PowerMockito.mock(ObjectInputStream.class);
		out = PowerMockito.mock(ObjectOutputStream.class);
		PowerMockito.doAnswer(new OutputTracker(output)).when(out).writeObject(Mockito.any());
	}

	/**
	 * Creates a project connector for a mocked project.
	 */
	@Before
	public void setProjectConnector() {
		projectConnector = new ProjectConnector(PROJECT_MOCK_ID);
	}

	/**
	 * Creates a project service that provides mocked DAOs.
	 */
	@Before
	public void mockProjectService() {
		connectorService = new ConnectorService();
		connectorService.setProjectDao(mockProjectDao());
		connectorService.setToolConfigurationDao(PowerMockito.mock(ToolConfigurationDao.class));
		connectorService.setCandidateDao(PowerMockito.mock(CandidateDao.class));
		connectorService.setGoalDao(PowerMockito.mock(GoalDao.class));
		connectorService.setLevelDao(PowerMockito.mock(LevelDao.class));
	}

	/**
	 * Mocks the {@link ProjectDao}. Thus, it sets the id of projects and returns
	 * stored projects.
	 * 
	 * @return the mocked DAO
	 */
	private ProjectDao mockProjectDao() {
		ProjectDao projectDao = PowerMockito.mock(ProjectDao.class);
		ProjectDaoMocker projectDaoMocker = new ProjectDaoMocker();
		PowerMockito.doAnswer(projectDaoMocker).when(projectDao).save(Mockito.any());
		PowerMockito.doAnswer(projectDaoMocker).when(projectDao).update(Mockito.any());
		PowerMockito.doAnswer(projectDaoMocker).when(projectDao).find(PROJECT_MOCK_ID);
		return projectDao;
	}

	/**
	 * Clears the test-case specific state of the test class.
	 */
	@Before
	public void clear() {
		output.clear();
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

	/**
	 * Sets the id of the projects and stores the projects.
	 */
	private class ProjectDaoMocker implements Answer<Project> {
		private Project project;

		@Override
		public Project answer(InvocationOnMock invocation) throws Throwable {
			Object object = invocation.getArguments()[0];
			if (object instanceof Project) {
				project = (Project) object;
				project.setId(PROJECT_MOCK_ID);
			}
			return project;
		}
	}

}
