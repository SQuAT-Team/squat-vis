package org.squat_team.vis.test;

import java.util.Arrays;
import java.util.List;

import org.squat_team.vis.connector.data.CCandidate;
import org.squat_team.vis.connector.data.CLevel;

public class TestNewLevelDataProvider {
	private static final List<Double> INITIAL_REAL_VALUES = Arrays.asList(0.9, 1.8, 350.0, 630.0);
	private static final List<Double> INITIAL_UTILITY_VALUES = Arrays.asList(1.0, 0.6, 1.0, 0.85);

	private static final List<Double> LEVEL1_CANDIDATE1_REAL_VALUES = Arrays.asList(0.8, 1.2, 410.0, 640.0);
	private static final List<Double> LEVEL1_CANDIDATE1_UTILITY_VALUES = Arrays.asList(1.0, 1.0, 0.95, 0.80);

	private static final List<Double> LEVEL1_CANDIDATE2_REAL_VALUES = Arrays.asList(1.2, 2.1, 350.0, 500.0);
	private static final List<Double> LEVEL1_CANDIDATE2_UTILITY_VALUES = Arrays.asList(0.8, 0.3, 1.0, 1.0);

	public CLevel getLevel0() {
		CLevel level = new CLevel();
		level.getCandidates().add(getInitialCandidate());
		return level;
	}

	public CLevel getLevel1() {
		CLevel level = new CLevel();
		level.getCandidates().add(getLevel1Candidate1());
		level.getCandidates().add(getLevel1Candidate2());
		return level;
	}

	public CCandidate getInitialCandidate() {
		CCandidate candidate = new CCandidate();
		candidate.setCandidateId(1L);
		candidate.setRealValues(INITIAL_REAL_VALUES);
		candidate.setUtilityValues(INITIAL_UTILITY_VALUES);
		candidate.setRealValuePareto(false);
		candidate.setUtilityValuePareto(false);
		return candidate;
	}

	public CCandidate getLevel1Candidate1() {
		CCandidate candidate = new CCandidate();
		candidate.setCandidateId(2L);
		candidate.setRealValues(LEVEL1_CANDIDATE1_REAL_VALUES);
		candidate.setUtilityValues(LEVEL1_CANDIDATE1_UTILITY_VALUES);
		candidate.setRealValuePareto(false);
		candidate.setUtilityValuePareto(false);
		return candidate;
	}

	public CCandidate getLevel1Candidate2() {
		CCandidate candidate = new CCandidate();
		candidate.setCandidateId(3L);
		candidate.setRealValues(LEVEL1_CANDIDATE2_REAL_VALUES);
		candidate.setUtilityValues(LEVEL1_CANDIDATE2_UTILITY_VALUES);
		candidate.setRealValuePareto(false);
		candidate.setUtilityValuePareto(false);
		return candidate;
	}

}
