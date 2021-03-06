package org.squat_team.vis.test.testData;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.squat_team.vis.connector.data.CArchitecture;
import org.squat_team.vis.connector.data.CCandidate;
import org.squat_team.vis.connector.data.CLevel;
import org.squat_team.vis.util.ArchitectureHelper;

public class TestNewLevelDataProvider {
	private static final String MODEL_PATH = "models"+File.separator+"test-project"+File.separator;
	
	private static final List<Double> INITIAL_REAL_VALUES = Arrays.asList(0.9, 1.8, 350.0, 630.0);
	private static final List<Double> INITIAL_UTILITY_VALUES = Arrays.asList(0.2, 0.1, 0.3, 0.15);

	private static final List<Double> LEVEL1_CANDIDATE1_REAL_VALUES = Arrays.asList(0.8, 1.2, 410.0, 640.0);
	private static final List<Double> LEVEL1_CANDIDATE1_UTILITY_VALUES = Arrays.asList(1.0, 1.0, 0.95, 0.80);

	private static final List<Double> LEVEL1_CANDIDATE2_REAL_VALUES = Arrays.asList(1.2, 2.1, 350.0, 500.0);
	private static final List<Double> LEVEL1_CANDIDATE2_UTILITY_VALUES = Arrays.asList(0.8, 0.3, 1.0, 1.0);

	private static final List<Double> LEVEL1_CANDIDATE3_REAL_VALUES = Arrays.asList(1.8, 2.4, 500.0, 700.0);
	private static final List<Double> LEVEL1_CANDIDATE3_UTILITY_VALUES = Arrays.asList(0.5, 0.2, 0.5, 0.6);

	public List<CLevel> getAllLevels() {
		List<CLevel> levels = new ArrayList<CLevel>();
		levels.add(getLevel0());
		levels.add(getLevel1());
		return levels;
	}

	public CLevel getLevel0() {
		CLevel level = new CLevel();
		level.setLevelNumber(0);
		level.getCandidates().add(getInitialCandidate());
		return level;
	}

	public CLevel getLevel1() {
		CLevel level = new CLevel();
		level.setLevelNumber(1);
		level.getCandidates().add(getLevel1Candidate1());
		level.getCandidates().add(getLevel1Candidate2());
		level.getCandidates().add(getLevel1Candidate3());
		return level;
	}

	public CCandidate getInitialCandidate() {
		CCandidate candidate = new CCandidate();
		candidate.setCandidateId(1L);
		candidate.setRealValues(INITIAL_REAL_VALUES);
		candidate.setUtilityValues(INITIAL_UTILITY_VALUES);
		candidate.setSuggested(true);
		loadArchitecture1(candidate);
		return candidate;
	}

	public CCandidate getLevel1Candidate1() {
		CCandidate candidate = new CCandidate();
		candidate.setCandidateId(2L);
		candidate.setParentId(1L);
		candidate.setRealValues(LEVEL1_CANDIDATE1_REAL_VALUES);
		candidate.setUtilityValues(LEVEL1_CANDIDATE1_UTILITY_VALUES);
		candidate.setSuggested(false);
		loadArchitecture2(candidate);
		return candidate;
	}

	public CCandidate getLevel1Candidate2() {
		CCandidate candidate = new CCandidate();
		candidate.setCandidateId(3L);
		candidate.setParentId(1L);
		candidate.setRealValues(LEVEL1_CANDIDATE2_REAL_VALUES);
		candidate.setUtilityValues(LEVEL1_CANDIDATE2_UTILITY_VALUES);
		candidate.setSuggested(true);
		loadArchitecture3(candidate);
		return candidate;
	}

	public CCandidate getLevel1Candidate3() {
		CCandidate candidate = new CCandidate();
		candidate.setCandidateId(4L);
		candidate.setParentId(1L);
		candidate.setRealValues(LEVEL1_CANDIDATE3_REAL_VALUES);
		candidate.setUtilityValues(LEVEL1_CANDIDATE3_UTILITY_VALUES);
		candidate.setSuggested(false);
		loadArchitecture4(candidate);
		return candidate;
	}

	private CCandidate loadArchitecture1(CCandidate candidate) {
		CArchitecture architecture = new CArchitecture();
		ArchitectureHelper.loadModel(architecture, MODEL_PATH + "stplus-0-IExporter");
		candidate.setArchitecture(architecture);
		return candidate;
	}

	private CCandidate loadArchitecture2(CCandidate candidate) {
		CArchitecture architecture = new CArchitecture();
		ArchitectureHelper.loadModel(architecture, MODEL_PATH + "stplus-0-Payment System");
		candidate.setArchitecture(architecture);
		return candidate;
	}

	private CCandidate loadArchitecture3(CCandidate candidate) {
		CArchitecture architecture = new CArchitecture();
		ArchitectureHelper.loadModel(architecture, MODEL_PATH + "stplus-1-ITripDB");
		candidate.setArchitecture(architecture);
		return candidate;
	}

	private CCandidate loadArchitecture4(CCandidate candidate) {
		CArchitecture architecture = new CArchitecture();
		ArchitectureHelper.loadModel(architecture, MODEL_PATH + "stplus-2-IExternalPayment");
		candidate.setArchitecture(architecture);
		return candidate;
	}

}
