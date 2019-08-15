package org.squat_team.vis.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.squat_team.vis.data.data.Candidate;
import org.squat_team.vis.data.data.Level;
import org.squat_team.vis.data.data.Project;

import lombok.Data;

/**
 * Holds all the additional, session-based information about a {@link Project}.
 */
@Data
public class ProjectInfo implements Serializable {
	
	/**
	 * Generated
	 */
	private static final long serialVersionUID = 8781120400605216186L;
	private Map<Integer, Set<String>> candidateIdCache = new HashMap<>();
	private Project project;
	private SelectorInfo selectorInfo;
	private ColorInfo colorInfo = new ColorInfo();
	private OptionsInfo optionsInfo = new OptionsInfo();
	private TagInfo tagInfo;
	private MatrixViewInfo matrixViewInfo = new MatrixViewInfo();
	private StarViewInfo starViewInfo = new StarViewInfo();
	private GraphViewInfo graphViewInfo = new GraphViewInfo();
	private LevelInfo levelInfo;
	
	public ProjectInfo(Project project, List<Candidate> candidates) {
		this.project = project;
		addProjectToCandidateIdCache(this.project);
		this.levelInfo = new LevelInfo();
		this.selectorInfo = new SelectorInfo(this, candidates);
		this.tagInfo = new TagInfo(this);
	}

	private void addProjectToCandidateIdCache(Project project) {
		for(int i = 0; i < project.getLevels().size(); i++) {
			addLevelToCandidateIdCache(i, project.getLevels().get(i));
		}
	}
	
	private void addLevelToCandidateIdCache(int index, Level level) {
		Set<String> candidateIds = new HashSet<>();
		for(Candidate candidate : level.getCandidates()) {
			candidateIds.add(candidate.getCandidateId().toString());
		}
		candidateIdCache.put(index, candidateIds);
	}
	
	public void updateProject(Project project) {
		this.project = project;
		int currentCandidateId = candidateIdCache.keySet().size();
		while(currentCandidateId < project.getLevels().size()) {
			addLevelToCandidateIdCache(currentCandidateId, project.getLevels().get(currentCandidateId));
			currentCandidateId = candidateIdCache.keySet().size();
		}
	}
	
	public List<Candidate> getAllActiveCandidates() {
		List<Candidate> activeCandidates = new ArrayList<>();
		Set<Integer> activeLevelIndizes = levelInfo.getActiveLevels(project.getNumberOfLevels());
		for (int activeLevelIndex : activeLevelIndizes) {
			Level currentLevel = project.getLevels().get(activeLevelIndex);
			activeCandidates.addAll(currentLevel.getCandidates());
		}
		return activeCandidates;
	}

	public List<Candidate> getAllActiveParentCandidates() {
		List<Candidate> activeParentCandidates = new ArrayList<>();
		Set<Integer> activeParentLevelIndizes = new HashSet<>();
		activeParentLevelIndizes.addAll(levelInfo.getActiveParentLevels(project.getNumberOfLevels()));
		activeParentLevelIndizes.removeAll(levelInfo.getActiveLevels(project.getNumberOfLevels()));
		for (int currentLevelIndex : activeParentLevelIndizes) {
			Level currentLevel = project.getLevels().get(currentLevelIndex);
			activeParentCandidates.addAll(currentLevel.getCandidates());
		}
		return activeParentCandidates;
	}

	public List<Candidate> getAllActiveAndParentCandidates() {
		List<Candidate> activeAndParentCandidates = new ArrayList<>();
		Set<Integer> activeAndParentLevelIndizes = new HashSet<>();
		activeAndParentLevelIndizes.addAll(levelInfo.getActiveLevels(project.getNumberOfLevels()));
		activeAndParentLevelIndizes.addAll(levelInfo.getActiveParentLevels(project.getNumberOfLevels()));
		for (int currentLevelIndex : activeAndParentLevelIndizes) {
			Level currentLevel = project.getLevels().get(currentLevelIndex);
			activeAndParentCandidates.addAll(currentLevel.getCandidates());
		}
		return activeAndParentCandidates;
	}

}
