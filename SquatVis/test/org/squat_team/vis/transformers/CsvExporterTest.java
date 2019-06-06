package org.squat_team.vis.transformers;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;
import org.squat_team.vis.data.data.Candidate;
import org.squat_team.vis.data.data.Goal;
import org.squat_team.vis.data.data.Level;
import org.squat_team.vis.data.data.Project;
import org.squat_team.vis.session.ProjectInfo;

/**
 * Tests the {@link CsvExporter}.
 */
public class CsvExporterTest {

	private final static String expectedResult = "ID,InitialTags,P1,P2,M1,M2\\n0,   ,0.01,0.02,0.03,0.04\\n1,   ,0.11,0.12,0.13,0.14\\n2,   ,0.21,0.22,0.23,0.24\\n3,   ,0.31,0.32,0.33,0.34\\n4,   ,0.41,0.42,0.43,0.44\\n5,   ,0.51,0.52,0.53,0.54\\n6,   ,0.61,0.62,0.63,0.64\\n7,   ,0.71,0.72,0.73,0.74\\n8,   ,0.81,0.82,0.83,0.84\\n";

	/**
	 * A complex test that consists of 9 candidates in 3 levels.
	 */
	@Test
	public void test() {
		Project project = initializeTestProject();
		CsvExporter csvExporter = new CsvExporter();
		assertEquals(expectedResult, csvExporter.export(project, new ProjectInfo()));
	}

	private Project initializeTestProject() {
		Project project = new Project();
		project.setGoal(initializeTestGoal());
		project.getLevels().add(initializeTestLevel0());
		project.getLevels().add(initializeTestLevel1());
		project.getLevels().add(initializeTestLevel2());
		return project;
	}

	private Goal initializeTestGoal() {
		Goal rootGoal = new Goal();
		Goal goal1 = new Goal();
		Goal goal2 = new Goal();
		Goal goal3 = new Goal();
		Goal goal4 = new Goal();
		Goal goal5 = new Goal();
		Goal goal6 = new Goal();

		rootGoal.setName("Root Goal");
		goal1.setName("Performance");
		goal2.setName("Modifiability");
		goal3.setName("P1");
		goal4.setName("P2");
		goal5.setName("M1");
		goal6.setName("M2");

		rootGoal.getChildren().add(goal1);
		rootGoal.getChildren().add(goal2);
		goal1.getChildren().add(goal3);
		goal1.getChildren().add(goal4);
		goal2.getChildren().add(goal5);
		goal2.getChildren().add(goal6);

		return rootGoal;
	}

	private Level initializeTestLevel0() {
		Level level = new Level();
		level.getCandidates().add(initializeTestCandidate0_1());
		return level;
	}

	private Level initializeTestLevel1() {
		Level level = new Level();
		level.getCandidates().add(initializeTestCandidate1_1());
		level.getCandidates().add(initializeTestCandidate1_2());
		level.getCandidates().add(initializeTestCandidate1_3());
		level.getCandidates().add(initializeTestCandidate1_4());
		return level;
	}

	private Level initializeTestLevel2() {
		Level level = new Level();
		level.getCandidates().add(initializeTestCandidate2_1());
		level.getCandidates().add(initializeTestCandidate2_2());
		level.getCandidates().add(initializeTestCandidate2_3());
		level.getCandidates().add(initializeTestCandidate2_4());
		return level;
	}

	private Candidate initializeTestCandidate0_1() {
		Candidate candidate = new Candidate();
		candidate.setCandidateId(0l);
		Double[] values = { 0.01, 0.02, 0.03, 0.04 };
		candidate.setUtilityValues(Arrays.asList(values));
		candidate.setRealValueParetoLevelBased(false);
		candidate.setRealValueParetoPopulationBased(false);
		candidate.setUtilityValueParetoLevelBased(false);
		candidate.setUtilityValueParetoPopulationBased(false);
		candidate.setSuggested(false);
		return candidate;
	}

	private Candidate initializeTestCandidate1_1() {
		Candidate candidate = new Candidate();
		candidate.setCandidateId(1l);
		Double[] values = { 0.11, 0.12, 0.13, 0.14 };
		candidate.setUtilityValues(Arrays.asList(values));
		candidate.setRealValueParetoLevelBased(false);
		candidate.setRealValueParetoPopulationBased(false);
		candidate.setUtilityValueParetoLevelBased(false);
		candidate.setUtilityValueParetoPopulationBased(false);
		candidate.setSuggested(false);
		return candidate;
	}

	private Candidate initializeTestCandidate1_2() {
		Candidate candidate = new Candidate();
		candidate.setCandidateId(2l);
		Double[] values = { 0.21, 0.22, 0.23, 0.24 };
		candidate.setUtilityValues(Arrays.asList(values));
		candidate.setRealValueParetoLevelBased(false);
		candidate.setRealValueParetoPopulationBased(false);
		candidate.setUtilityValueParetoLevelBased(false);
		candidate.setUtilityValueParetoPopulationBased(false);
		candidate.setSuggested(false);
		return candidate;
	}

	private Candidate initializeTestCandidate1_3() {
		Candidate candidate = new Candidate();
		candidate.setCandidateId(3l);
		Double[] values = { 0.31, .32, 0.33, 0.34 };
		candidate.setUtilityValues(Arrays.asList(values));
		candidate.setRealValueParetoLevelBased(false);
		candidate.setRealValueParetoPopulationBased(false);
		candidate.setUtilityValueParetoLevelBased(false);
		candidate.setUtilityValueParetoPopulationBased(false);
		candidate.setSuggested(false);
		return candidate;
	}

	private Candidate initializeTestCandidate1_4() {
		Candidate candidate = new Candidate();
		candidate.setCandidateId(4l);
		Double[] values = { 0.41, .42, 0.43, 0.44 };
		candidate.setUtilityValues(Arrays.asList(values));
		candidate.setRealValueParetoLevelBased(false);
		candidate.setRealValueParetoPopulationBased(false);
		candidate.setUtilityValueParetoLevelBased(false);
		candidate.setUtilityValueParetoPopulationBased(false);
		candidate.setSuggested(false);
		return candidate;
	}

	private Candidate initializeTestCandidate2_1() {
		Candidate candidate = new Candidate();
		candidate.setCandidateId(5l);
		Double[] values = { 0.51, .52, 0.53, 0.54 };
		candidate.setUtilityValues(Arrays.asList(values));
		candidate.setRealValueParetoLevelBased(false);
		candidate.setRealValueParetoPopulationBased(false);
		candidate.setUtilityValueParetoLevelBased(false);
		candidate.setUtilityValueParetoPopulationBased(false);
		candidate.setSuggested(false);
		return candidate;
	}

	private Candidate initializeTestCandidate2_2() {
		Candidate candidate = new Candidate();
		candidate.setCandidateId(6l);
		Double[] values = { 0.61, .62, 0.63, 0.64 };
		candidate.setUtilityValues(Arrays.asList(values));
		candidate.setRealValueParetoLevelBased(false);
		candidate.setRealValueParetoPopulationBased(false);
		candidate.setUtilityValueParetoLevelBased(false);
		candidate.setUtilityValueParetoPopulationBased(false);
		candidate.setSuggested(false);
		return candidate;
	}

	private Candidate initializeTestCandidate2_3() {
		Candidate candidate = new Candidate();
		candidate.setCandidateId(7l);
		Double[] values = { 0.71, .72, 0.73, 0.74 };
		candidate.setUtilityValues(Arrays.asList(values));
		candidate.setRealValueParetoLevelBased(false);
		candidate.setRealValueParetoPopulationBased(false);
		candidate.setUtilityValueParetoLevelBased(false);
		candidate.setUtilityValueParetoPopulationBased(false);
		candidate.setSuggested(false);
		return candidate;
	}

	private Candidate initializeTestCandidate2_4() {
		Candidate candidate = new Candidate();
		candidate.setCandidateId(8l);
		Double[] values = { 0.81, .82, 0.83, 0.84 };
		candidate.setUtilityValues(Arrays.asList(values));
		candidate.setRealValueParetoLevelBased(false);
		candidate.setRealValueParetoPopulationBased(false);
		candidate.setUtilityValueParetoLevelBased(false);
		candidate.setUtilityValueParetoPopulationBased(false);
		candidate.setSuggested(false);
		return candidate;
	}
}
