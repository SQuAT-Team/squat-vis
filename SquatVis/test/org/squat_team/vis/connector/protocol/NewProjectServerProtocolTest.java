package org.squat_team.vis.connector.protocol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.squat_team.vis.connector.data.CGoal;
import org.squat_team.vis.connector.data.CProject;
import org.squat_team.vis.connector.data.CRange;
import org.squat_team.vis.connector.data.CToolConfiguration;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.exceptions.ProtocolFailure;
import org.squat_team.vis.connector.protocols.NewProjectServerProtocol;
import org.squat_team.vis.data.daos.GoalDao;
import org.squat_team.vis.data.daos.ProjectDao;
import org.squat_team.vis.data.daos.ToolConfigurationDao;
import org.squat_team.vis.data.data.Goal;
import org.squat_team.vis.data.data.Project;
import org.squat_team.vis.data.data.Status;
import org.squat_team.vis.data.data.Status.StatusType;
import org.squat_team.vis.data.data.ToolConfiguration;

/**
 * Tests the {@link NewProjectServerProtocol} and its importers.
 */
public class NewProjectServerProtocolTest extends AbstractServerProtocolTest {
	// Project settings
	private static final String PROJECT_NAME = "Project Name";

	// Goal settings
	private static final String GOAL_NAME = "Goal";
	private static final String GOAL_CHILD_1_NAME = "Goal Child 1";
	private static final String GOAL_CHILD_1_DESCRIPTION = "Goal Child 1 Description";
	private static final Double GOAL_CHILD_1_EXPECTED_RESPONSE = 5d;
	private static final String GOAL_CHILD_2_NAME = "Goal Child 2";
	private static final String GOAL_CHILD_2_DESCRIPTION = "Goal Child 2 Description";
	private static final Double GOAL_CHILD_2_EXPECTED_RESPONSE = 12d;

	// Configuration settings
	private static final String CONFIGURATION_NAME = "Configuration Name";
	private static final String CONFIGURATION_TOOL_NAME = "Test Tool";
	private static final boolean CONFIGURATION_HAS_ARCHITECTURES = false;
	private static final boolean CONFIGURATION_HAS_PARENTS = true;
	private static final boolean CONFIGURATION_HAS_REAL_VALUES = true;
	private static final boolean CONFIGURATION_HAS_UTITLITES = true;

	/**
	 * Tests a protocol run in which a project and its configuration and goals are
	 * imported.
	 */
	@Test
	@PrepareForTest(NewProjectServerProtocol.class)
	public void defaultProtocolExecutionTest()
			throws ClassNotFoundException, IOException, ProtocolFailure, InvalidRequestException {
		mockProtocolInput(initializeProject(), initializeConfiguration(), initializeGoals());
		NewProjectServerProtocol protocol = new NewProjectServerProtocol(in, out, connectorService);
		protocol.execute();
		checkProjectAndContents();
	}

	/**
	 * Mocks incoming data to the protocol.
	 * 
	 * @param project       the project that will be received
	 * @param configuration the configuration that will be received
	 * @param goal          the goal that will be received
	 */
	private void mockProtocolInput(CProject project, CToolConfiguration configuration, CGoal goal)
			throws ClassNotFoundException, IOException {
		PowerMockito.when(in.readObject()).thenReturn(project).thenReturn(configuration).thenReturn(goal);
	}

	/**
	 * Initializes a project that will be imported by the protocol.
	 * 
	 * @return the project
	 */
	private CProject initializeProject() {
		return new CProject(PROJECT_NAME);
	}

	/**
	 * Initializes a configuration that will be imported by the protocol.
	 * 
	 * @return the configuration
	 */
	private CToolConfiguration initializeConfiguration() {
		CToolConfiguration configuration = new CToolConfiguration(CONFIGURATION_NAME);
		configuration.setHasArchitectures(CONFIGURATION_HAS_ARCHITECTURES);
		configuration.setHasParents(CONFIGURATION_HAS_PARENTS);
		configuration.setHasRealValues(CONFIGURATION_HAS_REAL_VALUES);
		configuration.setHasUtilities(CONFIGURATION_HAS_UTITLITES);
		configuration.setToolName(CONFIGURATION_TOOL_NAME);
		return configuration;
	}

	/**
	 * Initializes a goal that will be imported by the protocol.
	 * 
	 * @return the goal
	 */
	private CGoal initializeGoals() {
		CGoal goal = new CGoal();
		goal.setName(GOAL_NAME);

		CGoal child1 = goal.addChild();
		child1.setName(GOAL_CHILD_1_NAME);
		child1.setDescription(GOAL_CHILD_1_DESCRIPTION);
		child1.setExpectedResponse(GOAL_CHILD_1_EXPECTED_RESPONSE);
		child1.setRange(initializeRange());

		CGoal child2 = goal.addChild();
		child2.setName(GOAL_CHILD_2_NAME);
		child2.setDescription(GOAL_CHILD_2_DESCRIPTION);
		child2.setExpectedResponse(GOAL_CHILD_2_EXPECTED_RESPONSE);
		child2.setRange(initializeRange());
		return goal;
	}

	/**
	 * Initializes a new default range.
	 * 
	 * @return the range
	 */
	private CRange initializeRange() {
		CRange range = new CRange();
		range.setRangeMin(0d);
		range.setRangeMax(100d);
		range.setComputeMin(false);
		range.setComputeMax(false);
		return range;
	}

	/**
	 * Checks the imported project and its properties
	 */
	private void checkProjectAndContents() {
		Project storedProject = findTestProject();
		check(storedProject);
		check(storedProject.getGoal());
		check(storedProject.getConfiguration());
		check(storedProject.getStatus());
	}

	/**
	 * Checks a project
	 * 
	 * @param project the project
	 */
	private void check(Project project) {
		checkProjectDao(project);
		assertEquals(PROJECT_NAME, project.getName());
	}

	/**
	 * Checks the top goal
	 * 
	 * @param goal the goal
	 */
	private void check(Goal goal) {
		checkGoalDao(goal);
		assertEquals(GOAL_NAME, goal.getName());
		assertEquals(2, goal.getChildren().size());

		Goal child1 = goal.getChildren().get(0);
		assertEquals(goal, child1.getParent());
		assertEquals(GOAL_CHILD_1_NAME, child1.getName());
		assertEquals(GOAL_CHILD_1_DESCRIPTION, child1.getDescription());
		assertEquals(GOAL_CHILD_1_EXPECTED_RESPONSE, child1.getExpectedResponse());
		assertNotNull(child1.getRange());

		Goal child2 = goal.getChildren().get(1);
		assertEquals(goal, child2.getParent());
		assertEquals(GOAL_CHILD_2_NAME, child2.getName());
		assertEquals(GOAL_CHILD_2_DESCRIPTION, child2.getDescription());
		assertEquals(GOAL_CHILD_2_EXPECTED_RESPONSE, child2.getExpectedResponse());
		assertNotNull(child2.getRange());
	}

	/**
	 * Checks a status
	 * 
	 * @param status the status
	 */
	private void check(Status status) {
		assertEquals(0d, status.getToolProgress(), 0.0001);
		assertEquals(0d, status.getVisProgress(), 0.0001);
		assertEquals(0, status.getTotalProgress());
		assertEquals(StatusType.RUNNING, status.getType());
		assertNotNull(status.getLastUpdate());
		assertNotNull(status.getLevelStarted());
		assertNotNull(status.getCreationTime());
		assertNotNull(status.getMessage());
	}

	/**
	 * Checks a configuration
	 * 
	 * @param configuration the configuration
	 */
	private void check(ToolConfiguration configuration) {
		checkToolConfigurationDao(configuration);
		assertEquals(CONFIGURATION_NAME, configuration.getName());
		assertEquals(CONFIGURATION_TOOL_NAME, configuration.getToolName());
		assertEquals(CONFIGURATION_HAS_ARCHITECTURES, configuration.getHasArchitectures());
		assertEquals(CONFIGURATION_HAS_PARENTS, configuration.getHasParents());
		assertEquals(CONFIGURATION_HAS_REAL_VALUES, configuration.getHasRealValues());
		assertEquals(CONFIGURATION_HAS_UTITLITES, configuration.getHasUtilities());
	}

	/**
	 * Checks whether the project DAO stored the project.
	 * 
	 * @param storedProject the project
	 */
	private void checkProjectDao(Project storedProject) {
		ProjectDao projectDao = this.connectorService.getProjectDao();
		Mockito.verify(projectDao, Mockito.atLeastOnce()).find(PROJECT_MOCK_ID);
		Mockito.verify(projectDao, Mockito.atLeastOnce()).save(storedProject);
		Mockito.verify(projectDao, Mockito.atLeastOnce()).update(storedProject);
	}

	/**
	 * Checks whether the configuration DAO stored the configuration.
	 * 
	 * @param configuration the configuration
	 */
	private void checkToolConfigurationDao(ToolConfiguration configuration) {
		ToolConfigurationDao configurationDao = this.connectorService.getToolConfigurationDao();
		Mockito.verify(configurationDao).save(configuration);
	}

	/**
	 * Checks whether the goal DAO stored the goal.
	 * 
	 * @param goal the goal
	 */
	private void checkGoalDao(Goal goal) {
		GoalDao goalDao = this.connectorService.getGoalDao();
		Mockito.verify(goalDao).save(goal);
	}

	/**
	 * Finds the default test project.
	 * 
	 * @return the project
	 */
	private Project findTestProject() {
		ProjectDao projectDao = this.connectorService.getProjectDao();
		return projectDao.find(PROJECT_MOCK_ID);
	}

}
