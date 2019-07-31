package org.squat_team.vis.test.importer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.squat_team.vis.connector.data.CCandidate;
import org.squat_team.vis.connector.data.CGoal;
import org.squat_team.vis.connector.data.CLevel;
import org.squat_team.vis.connector.data.CProject;
import org.squat_team.vis.connector.data.CToolConfiguration;
import org.squat_team.vis.connector.data.DefaultToolConfigurations;

public class SquatImporter implements IImporter {
	private static final String PROJECT_NAME = "CoCoMe Case Study";
	
	private String filePath;
	
	private long candidateCounter = -1;
	private CGoal rootGoal;
	private List<CLevel> levels = new ArrayList<>();
	private List<CGoal> orderedGoals = new ArrayList<CGoal>();
	private Map<String, CCandidate> candidates = new HashMap<>();
	private Map<String, CGoal> goals = new HashMap<>();
	private Map<String, CGoal> qualityAttributes = new LinkedHashMap<>();
	private Map<String, String> parents = new HashMap<String, String>();

	public SquatImporter(String filePath) {
		this.filePath = filePath;
	}
	
	public CProject importProject() {
		return new CProject(PROJECT_NAME);
	}
	
	public CToolConfiguration importConfiguration() {
		CToolConfiguration configuration = DefaultToolConfigurations.getInstance().getSquatDefaultConfiguration();
		configuration.setHasArchitectures(false);
		return configuration;
	}
	
	public List<CLevel> importLevels() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		skipLine(reader);
		importLines(reader);
		setParents();
		return levels;
	}
	
	public CGoal importGoals() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
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
		// get level
		String[] lineEntries = getLineEntries(line);
		String levelNumber = getLevel(lineEntries);
		String father = getFather(lineEntries);
		CLevel level = getLevel(levelNumber);
		
		// get candidate
		String candidateName = getCandidateName(lineEntries);
		CCandidate candidate = candidates.get(candidateName);
		if(candidate == null) {
			// new candidate
			candidate = new CCandidate();
			candidate.setCandidateId(getNewCandidateId());
			for(int i = 0; i < orderedGoals.size(); i++) {
				candidate.getRealValues().add(0d);
				candidate.getUtilityValues().add(0d);
			}
			candidates.put(candidateName, candidate);
			level.getCandidates().add(candidate);
			
			// log father
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
		Double utilityValue = Double.parseDouble(getUtility(lineEntries));
		candidate.getUtilityValues().set(currentGoalIndex, utilityValue);
	}
	
	private void setParents() {
		for(Entry<String, String> parentRelation : parents.entrySet()) {
			CCandidate child = candidates.get(parentRelation.getKey());
			CCandidate parent = candidates.get(parentRelation.getValue());
			if(child != null && parent != null) {
				child.setParentId(parent.getCandidateId());
			}
		}
	}
	
	private void skipLine(BufferedReader reader) throws IOException {
		if(reader.readLine() != null) {
			reader.readLine();
		}
	}
	
	private CGoal getQualityAttributeGoal(String qualityAttribute) {
		CGoal goal = qualityAttributes.get(qualityAttribute);
		if(goal == null) {
			goal = rootGoal.addChild();
			goal.setName(qualityAttribute);
			qualityAttributes.put(qualityAttribute, goal);
		}
		return goal;
	}
	
	private CGoal getBotGoal(CGoal qualityAttributeGoal, String botName) {
		CGoal goal = goals.get(botName);
		if(goal == null) {
			goal = qualityAttributeGoal.addChild();
			goal.setName(botName);
			goals.put(botName, goal);
			orderedGoals.add(goal);
		}
		return goal;
	}
	
	private CLevel getLevel(String levelNumberAsString) {
		int levelNumberAsInt = Integer.parseInt(levelNumberAsString);
		while(levels.size() <= levelNumberAsInt) {
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
	
	private String getValue(String[] lineEntries) {
		return lineEntries[3];
	}
	
	private String getCandidateName(String[] lineEntries) {
		return lineEntries[4];
	}
	
	private String getLevel(String[] lineEntries) {
		return lineEntries[5];
	}
	
	private String getUtility(String[] lineEntries) {
		return lineEntries[8];
	}
	
	private String getFather(String[] lineEntries) {
		return lineEntries[9];
	}
}
