package org.squat_team.vis.test;

import org.squat_team.vis.connector.data.CGoal;
import org.squat_team.vis.connector.data.CProject;
import org.squat_team.vis.connector.data.CRange;
import org.squat_team.vis.connector.data.CToolConfiguration;
import org.squat_team.vis.connector.data.DefaultToolConfigurations;

public class TestNewProjectDataProvider {
	private static final String PROJECT_NAME = "Default Test Project";

	private static final String QUALITY_ATTRIBUTE_1_NAME = "Performance";
	private static final String QUALITY_ATTRIBUTE_1_DESCRIPTION = "Performance";
	private static final String QUALITY_ATTRIBUTE_2_NAME = "Modifiability";
	private static final String QUALITY_ATTRIBUTE_2_DESCRIPTION = "Modifiability";
	private static final String SCENARIO_1_QA_1_NAME = "P1";
	private static final String SCENARIO_1_QA_1_DESCRIPTION = "Server Cluster Failure";
	private static final String SCENARIO_2_QA_1_NAME = "P2";
	private static final String SCENARIO_2_QA_1_DESCRIPTION = "General Usage Increase 10%";
	private static final String SCENARIO_1_QA_2_NAME = "M1";
	private static final String SCENARIO_1_QA_2_DESCRIPTION = "Add NFC Payment Method";
	private static final String SCENARIO_2_QA_2_NAME = "M2";
	private static final String SCENARIO_2_QA_2_DESCRIPTION = "Add Component for Statistical Analysis";

	private static final double SCENARIO_1_QA_1_RESPONSE = 1.0;
	private static final double SCENARIO_2_QA_1_RESPONSE = 1.3;
	private static final double SCENARIO_1_QA_2_RESPONSE = 400;
	private static final double SCENARIO_2_QA_2_RESPONSE = 530;

	public CToolConfiguration getConfiguration() {
		return DefaultToolConfigurations.getInstance().getTestDefaultConfiguration();
	}

	public CProject getProject() {
		CProject project = new CProject(PROJECT_NAME);
		return project;
	}

	public CGoal getGoal() {
		CGoal rootGoal = getRootGoal();
		CGoal goal1_1 = createGoalLevel1_1(rootGoal);
		CGoal goal1_2 = createGoalLevel1_2(rootGoal);
		createGoalLevel2_1(goal1_1);
		createGoalLevel2_2(goal1_1);
		createGoalLevel2_3(goal1_2);
		createGoalLevel2_4(goal1_2);
		return rootGoal;
	}

	private CGoal getRootGoal() {
		CGoal rootGoal = new CGoal();
		rootGoal.setName("Test Root Goal");
		rootGoal.setDescription("This is the root goal that should never be shown");
		return rootGoal;
	}

	private CGoal createGoalLevel1_1(CGoal parent) {
		CGoal goal = parent.addChild();
		goal.setName(QUALITY_ATTRIBUTE_1_NAME);
		goal.setDescription(QUALITY_ATTRIBUTE_1_DESCRIPTION);
		return goal;
	}

	private CGoal createGoalLevel1_2(CGoal parent) {
		CGoal goal = parent.addChild();
		goal.setName(QUALITY_ATTRIBUTE_2_NAME);
		goal.setDescription(QUALITY_ATTRIBUTE_2_DESCRIPTION);
		return goal;
	}

	private CGoal createGoalLevel2_1(CGoal parent) {
		CGoal goal = parent.addChild();
		goal.setName(SCENARIO_1_QA_1_NAME);
		goal.setDescription(SCENARIO_1_QA_1_DESCRIPTION);
		goal.setExpectedResponse(SCENARIO_1_QA_1_RESPONSE);
		goal.setRange(createRangeLevel2_1());
		return goal;
	}

	private CGoal createGoalLevel2_2(CGoal parent) {
		CGoal goal = parent.addChild();
		goal.setName(SCENARIO_2_QA_1_NAME);
		goal.setDescription(SCENARIO_2_QA_1_DESCRIPTION);
		goal.setExpectedResponse(SCENARIO_2_QA_1_RESPONSE);
		goal.setRange(createRangeLevel2_2());
		return goal;
	}

	private CGoal createGoalLevel2_3(CGoal parent) {
		CGoal goal = parent.addChild();
		goal.setName(SCENARIO_1_QA_2_NAME);
		goal.setDescription(SCENARIO_1_QA_2_DESCRIPTION);
		goal.setExpectedResponse(SCENARIO_1_QA_2_RESPONSE);
		goal.setRange(createRangeLevel2_3());
		return goal;
	}

	private CGoal createGoalLevel2_4(CGoal parent) {
		CGoal goal = parent.addChild();
		goal.setName(SCENARIO_2_QA_2_NAME);
		goal.setDescription(SCENARIO_2_QA_2_DESCRIPTION);
		goal.setExpectedResponse(SCENARIO_2_QA_2_RESPONSE);
		goal.setRange(createRangeLevel2_4());
		return goal;
	}

	private CRange createRangeLevel2_1() {
		CRange range = new CRange();
		range.setRangeMin(0.0);
		range.setRangeMax(10.0);
		range.setComputeMin(false);
		range.setComputeMax(false);
		return range;
	}

	private CRange createRangeLevel2_2() {
		CRange range = new CRange();
		range.setRangeMin(0.0);
		range.setRangeMax(0.0);
		range.setComputeMin(false);
		range.setComputeMax(true);
		return range;
	}

	private CRange createRangeLevel2_3() {
		CRange range = new CRange();
		range.setRangeMin(0.0);
		range.setRangeMax(1000.0);
		range.setComputeMin(true);
		range.setComputeMax(false);
		return range;
	}

	private CRange createRangeLevel2_4() {
		CRange range = new CRange();
		range.setRangeMin(0.0);
		range.setRangeMax(0.0);
		range.setComputeMin(true);
		range.setComputeMax(true);
		return range;
	}

}
