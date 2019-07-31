package org.squat_team.vis.test.importer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.squat_team.vis.connector.data.CArchitecture;
import org.squat_team.vis.connector.data.CCandidate;
import org.squat_team.vis.connector.data.CGoal;
import org.squat_team.vis.connector.data.CLevel;
import org.squat_team.vis.connector.data.CProject;
import org.squat_team.vis.connector.data.CToolConfiguration;
import org.squat_team.vis.connector.data.DefaultToolConfigurations;
import org.squat_team.vis.util.ArchitectureHelper;

public class SimpleTacticsImporter implements IImporter {
	private static final String BASE_PATH = "models" + File.separator + "simple-tactics-project";
	private static final String TXT_PATH = BASE_PATH + File.separator + "analysis.csv";
	private static final String PROJECT_NAME = "ST+ Case Study";

	private boolean overoptimizationPenalty;

	private long candidateCounter = -1;
	private CGoal rootGoal;
	private List<CLevel> levels = new ArrayList<>();
	private List<CGoal> orderedGoals = new ArrayList<CGoal>();
	private Map<String, CCandidate> candidates = new HashMap<>();
	private Map<String, CGoal> goals = new HashMap<>();
	private Map<String, CGoal> qualityAttributes = new LinkedHashMap<>();
	private Map<String, String> parents = new HashMap<String, String>();

	public SimpleTacticsImporter(boolean overoptimizationPenalty) {
		this.overoptimizationPenalty = overoptimizationPenalty;
	}

	public CProject importProject() {
		return new CProject(PROJECT_NAME);
	}

	public CToolConfiguration importConfiguration() {
		return DefaultToolConfigurations.getInstance().getSquatDefaultConfiguration();
	}

	public List<CLevel> importLevels() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(TXT_PATH));
		skipLine(reader);
		importLines(reader);
		setParents();
		loadArchitectures();
		filterInvalidCandidates();
		return levels;
	}
	
	private void filterInvalidCandidates() {
		for(CLevel level : levels) {	
			for ( Iterator<CCandidate> iterator = level.getCandidates().iterator(); iterator.hasNext(); ) {
				if(iterator.next().getRealValues().contains(9999d)) {
					System.out.println("Removed");
					iterator.remove();
				}
			}
		}
	}

	public CGoal importGoals() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(TXT_PATH));
		rootGoal = new CGoal();
		rootGoal.setName("Root Goal");
		skipLine(reader);
		String line;
		while ((line = reader.readLine()) != null) {
			importGoal(line);
		}
		return rootGoal;
	}

	private void importGoal(String line) {
		String[] lineEntries = getLineEntries(line);
		String botName = getBotName(lineEntries);
		String qualityAttribute = getQualityAttribute(lineEntries);
		CGoal qualityAttributeGoal = getQualityAttributeGoal(qualityAttribute);
		getBotGoal(qualityAttributeGoal, botName);
	}

	private void importLines(BufferedReader reader) throws IOException {
		String line;
		while ((line = reader.readLine()) != null) {
			importLine(line);
		}
	}

	private void importLine(String line) {
		String[] lineEntries = getLineEntries(line);
		if (getResponseType(lineEntries).equals("AffectedComponents")) {
			return;
		}

		// get level
		String levelNumber = getLevel(lineEntries);
		CLevel level = getLevel(levelNumber);

		// get candidate
		String candidateName = getCandidateName(lineEntries);
		CCandidate candidate = candidates.get(candidateName);
		if (candidate == null) {
			// new candidate
			candidate = new CCandidate();
			candidate.setCandidateId(getNewCandidateId());
			for (int i = 0; i < orderedGoals.size(); i++) {
				candidate.getRealValues().add(0d);
				candidate.getUtilityValues().add(0d);
			}
			candidates.put(candidateName, candidate);
			level.getCandidates().add(candidate);

			// log father
			String father = getFather(candidateName);
			parents.put(candidateName, father);
		}

		// get index of goal
		String botName = getBotName(lineEntries);
		CGoal currentGoal = goals.get(botName);
		int currentGoalIndex = orderedGoals.indexOf(currentGoal);

		// set real value
		Double realValue = Double.parseDouble(getValue(lineEntries));
		candidate.getRealValues().set(currentGoalIndex, realValue);

		// set utility value
		Float utilityValue = getUtilityFor(realValue.floatValue(), botName);
		candidate.getUtilityValues().set(currentGoalIndex, utilityValue.doubleValue());
	}

	private void setParents() {
		for (Entry<String, String> parentRelation : parents.entrySet()) {
			CCandidate child = candidates.get(parentRelation.getKey());
			CCandidate parent = candidates.get(parentRelation.getValue());
			if (child != null && parent != null) {
				child.setParentId(parent.getCandidateId());
			}
		}
	}

	private void skipLine(BufferedReader reader) throws IOException {
		if (reader.readLine() != null) {
			reader.readLine();
		}
	}

	private CGoal getQualityAttributeGoal(String qualityAttribute) {
		CGoal goal = qualityAttributes.get(qualityAttribute);
		if (goal == null) {
			goal = rootGoal.addChild();
			goal.setName(qualityAttribute);
			qualityAttributes.put(qualityAttribute, goal);
		}
		return goal;
	}

	private CGoal getBotGoal(CGoal qualityAttributeGoal, String botName) {
		CGoal goal = goals.get(botName);
		if (goal == null) {
			goal = qualityAttributeGoal.addChild();
			goal.setName(botName);
			goals.put(botName, goal);
			orderedGoals.add(goal);
		}
		return goal;
	}

	private CLevel getLevel(String levelNumberAsString) {
		int levelNumberAsInt = Integer.parseInt(levelNumberAsString);
		while (levels.size() <= levelNumberAsInt) {
			CLevel level = new CLevel();
			level.setLevelNumber(levels.size());
			levels.add(level);
		}
		return levels.get(levelNumberAsInt);
	}

	private long getNewCandidateId() {
		candidateCounter++;
		return candidateCounter;
	}

	private String[] getLineEntries(String line) {
		return line.split(";");
	}

	private String getBotName(String[] lineEntries) {
		return lineEntries[0];
	}

	private String getQualityAttribute(String[] lineEntries) {
		return lineEntries[1];
	}

	private String getResponseType(String[] lineEntries) {
		return lineEntries[2];
	}

	private String getValue(String[] lineEntries) {
		return lineEntries[3];
	}

	private String getCandidateName(String[] lineEntries) {
		return lineEntries[4];
	}

	private String getLevel(String[] lineEntries) {
		String levelName = lineEntries[5];
		if (levelName.equals("Base")) {
			return "0";
		} else if (levelName.equals("First")) {
			return "1";
		} else if (levelName.equals("Second")) {
			return "2";
		} else {
			throw new IllegalArgumentException("Unexpceted level name " + levelName);
		}
	}

	private String getFather(String candidateName) {
		String[] nameParts = candidateName.split("-");
		if (nameParts.length == 3) {
			return "stplus-initial";
		} else {
			String fatherName = nameParts[0];
			for (int i = 1; i < nameParts.length - 2; i++) {
				fatherName += "-" + nameParts[i];
			}
			return fatherName;
		}
	}

	private float getUtilityFor(Float realValue, String botName) {
		float expectedResponse = getExpectedResponse(botName);

		float utility = 0;
		if (realValue <= expectedResponse) {
			if (overoptimizationPenalty) {
				utility = 2 - expectedResponse / realValue;
			} else {
				utility = 1;
			}
		} else {
			utility = 2 - 1.10f * realValue / expectedResponse;
		}
		if (utility >= 0 && utility <= 1)
			return utility;
		else
			return 0;
	}

	private float getExpectedResponse(String botName) {
		switch (botName) {
		case "m1":
			return 120;
		case "m2":
			return 300;
		case "p1":
			return 30;
		case "p2":
			return 40;
		default:
			throw new IllegalStateException("Unknown bot name " + botName);
		}
	}

	private void loadArchitectures() {
		for (Entry<String, CCandidate> candidateRelation : candidates.entrySet()) {
			CCandidate currentCandidate = candidateRelation.getValue();
			String currentCandidateName = candidateRelation.getKey();
			String[] currentCandidateNameParts = currentCandidateName.split("-");
			CArchitecture architecture = new CArchitecture();
			String path = BASE_PATH;
			if (currentCandidateNameParts.length == 2) {
				path += File.separator + "base" + File.separator + "default";
			} else {
				boolean isModifiability = currentCandidateNameParts[currentCandidateNameParts.length - 3
						+ currentCandidateNameParts.length % 2].equals("mod");
				if (isModifiability) {
					path += File.separator + "ModifiabilityBots";
				} else {
					path += File.separator + "PerformanceBots";
				}
				if (currentCandidateNameParts.length == 3 || currentCandidateNameParts.length == 4) {
					path += File.separator + "1stRound";
					if (isModifiability) {
						path += File.separator
								+ getFileName(getModifiabilityIdentifierBack(currentCandidateNameParts), true);
						// found
					} else {
						if (currentCandidateNameParts[1].equals("ps1")) {
							path += File.separator + "scenario-p1" + File.separator + "candidate"
									+ currentCandidateNameParts[2] + "_minPlus" + File.separator + "default";
							// found
						} else {
							path += File.separator + "scenario-p2" + File.separator + "candidate"
									+ currentCandidateNameParts[2] + "_minPlus" + File.separator + "default";
							// found
						}
					}
				} else {
					path += File.separator + "2ndRound";
					if (isModifiability) {
						if (currentCandidateNameParts[1].equals("ps1")) {
							path += File.separator + "scenario-p1";
						} else {
							path += File.separator + "scenario-p2";
						}
						path += File.separator + "candidate" + currentCandidateNameParts[2] + "_minPlus"
								+ File.separator
								+ getFileName(getModifiabilityIdentifierBack(currentCandidateNameParts), false);
						// found
					} else {
						if (currentCandidateNameParts[currentCandidateNameParts.length - 2].equals("ps1")) {
							path += File.separator + "scenario-p1";
						} else {
							path += File.separator + "scenario-p2";
						}
						String directoryName = getDirectoryName(
								getModifiabilityIdentifierFront(currentCandidateNameParts));
						path += File.separator + directoryName + File.separator + directoryName;
						String nextDirectoryName = getDirectoryIn(path);
						path += File.separator + nextDirectoryName + File.separator + "candidate"
								+ currentCandidateNameParts[currentCandidateNameParts.length - 1] + File.separator
								+ "default";
						// found
					}
				}
			}
			ArchitectureHelper.loadModel(architecture, path);
			currentCandidate.setArchitecture(architecture);
		}
	}

	private String getDirectoryName(String name) {
		switch (name) {
		case "split(PaymentSystem)":
			return "stplus-0-Payment System";
		case "wrapper(IExporter)":
			return "stplus-0-IExporter";
		case "wrapper(ITripDB)":
			return "stplus-1-ITripDB";
		case "wrapper(IExternalPayment)":
			return "stplus-2-IExternalPayment";
		case "wrapper(IEmployeePayment)":
			return "stplus-3-IEmployeePayment";
		case "wrapper(IBooking)":
			return "stplus-4-IBooking";
		case "wrapper(IBusinessTrip)":
			return "stplus-5-IBusiness Trip";
		case "split(PaymentSystem)-wrapper(IExporter)":
			return "stplus-split-0-IExporter";
		case "split(PaymentSystem)-wrapper(ITripDB)":
			return "stplus-split-1-ITripDB";
		case "split(PaymentSystem)-wrapper(IExternalPayment)":
			return "stplus-split-2-IExternalPayment";
		case "split(PaymentSystem)-wrapper(IEmployeePayment)":
			return "stplus-split-3-IEmployeePayment";
		case "split(PaymentSystem)-wrapper(IBooking)":
			return "stplus-split-4-IBooking";
		case "split(PaymentSystem)-wrapper(IBusinessTrip)":
			return "stplus-split-5-IBusiness Trip";
		default:
			throw new IllegalStateException("Unknown directory name " + name);
		}
	}

	private String getFileName(String name, boolean isModifiability) {
		String fileNamePrefix;
		if (isModifiability) {
			fileNamePrefix = "stplus";
		} else {
			fileNamePrefix = "default";
		}
		switch (name) {
		case "split(PaymentSystem)":
			return fileNamePrefix + "-0-Payment System";
		case "wrapper(IExporter)":
			return fileNamePrefix + "-0-IExporter";
		case "wrapper(ITripDB)":
			return fileNamePrefix + "-1-ITripDB";
		case "wrapper(IExternalPayment)":
			return fileNamePrefix + "-2-IExternalPayment";
		case "wrapper(IEmployeePayment)":
			return fileNamePrefix + "-3-IEmployeePayment";
		case "wrapper(IBooking)":
			return fileNamePrefix + "-4-IBooking";
		case "wrapper(IBusinessTrip)":
			return fileNamePrefix + "-5-IBusiness Trip";
		case "split(PaymentSystem)-wrapper(IExporter)":
			return fileNamePrefix + "-0-Payment System-0-IExporter";
		case "split(PaymentSystem)-wrapper(ITripDB)":
			return fileNamePrefix + "-0-Payment System-1-ITripDB";
		case "split(PaymentSystem)-wrapper(IExternalPayment)":
			return fileNamePrefix + "-0-Payment System-2-IExternalPayment";
		case "split(PaymentSystem)-wrapper(IEmployeePayment)":
			return fileNamePrefix + "-0-Payment System-3-IEmployeePayment";
		case "split(PaymentSystem)-wrapper(IBooking)":
			return fileNamePrefix + "-0-Payment System-4-IBooking";
		case "split(PaymentSystem)-wrapper(IBusinessTrip)":
			return fileNamePrefix + "-0-Payment System-5-IBusiness Trip";
		default:
			throw new IllegalStateException("Unknown directory name " + name);
		}
	}

	private String getModifiabilityIdentifierBack(String[] currentCandidateNameParts) {
		String identifier = "";
		if (currentCandidateNameParts.length % 2 == 0) {
			identifier += currentCandidateNameParts[currentCandidateNameParts.length - 2] + "-";
		}
		identifier += currentCandidateNameParts[currentCandidateNameParts.length - 1];
		return identifier;
	}

	private String getModifiabilityIdentifierFront(String[] currentCandidateNameParts) {
		String identifier = currentCandidateNameParts[2];
		if (currentCandidateNameParts.length % 2 == 0) {
			identifier += "-" + currentCandidateNameParts[3];
		}
		return identifier;
	}

	private String getDirectoryIn(String path) {
		File file = new File(path);
		return file.listFiles()[0].getName();
	}

}
