package org.squat_team.vis.export;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.squat_team.vis.data.controllers.LevelController;
import org.squat_team.vis.data.controllers.ProjectController;
import org.squat_team.vis.data.data.Candidate;
import org.squat_team.vis.data.data.Level;
import org.squat_team.vis.data.data.Project;
import org.squat_team.vis.session.SelectorInfo;
import org.squat_team.vis.session.SessionInfo;
import org.squat_team.vis.util.ResponseIdentifier;
import org.squat_team.vis.util.ResponseManager;

import lombok.Data;

/**
 * This service is responsible to handle frontend requests to send selected
 * candidates (level responses) to the optimization tool.
 */
@Data
@Named
@SessionScoped
public class LevelResponseService implements Serializable {
	/**
	 * Generated
	 */
	private static final long serialVersionUID = -8745222915286021619L;

	@Inject
	private SessionInfo sessionInfo;
	@Inject
	private ProjectController projectController;
	@Inject
	private LevelController levelController;
	@Inject
	private Event<ResponseIdentifier> eventService;

	/**
	 * Sends all active (means that they are in a shown level) and selected
	 * candidates as response.
	 */
	public void respondAllActiveSelected() {
		long projectId = this.sessionInfo.getSelectedProject();
		List<Level> levels = getAllLevels(projectId);
		if (!levels.isEmpty()) {
			int levelNumber = getLastLevelIndex(levels);
			Level lastLevel = levels.get(levelNumber);
			Set<String> activeSelectedCandidateIds = getActiveSelectedCandidateIds();
			List<Long> responseCandidates = castToLongList(activeSelectedCandidateIds);
			setSelectedCandidatesInLevel(responseCandidates, lastLevel);
			notifyResponseProtocol(projectId, levelNumber);
		}
	}

	/**
	 * Sends the complete last level as a response
	 */
	public void respondCompleteLastLevel() {
		long projectId = this.sessionInfo.getSelectedProject();
		List<Level> levels = getAllLevels(projectId);
		if (!levels.isEmpty()) {
			int levelNumber = getLastLevelIndex(levels);
			Level lastLevel = levels.get(levelNumber);
			List<Long> allCandidates = getAllCandidateIds(lastLevel);
			setSelectedCandidatesInLevel(allCandidates, lastLevel);
			notifyResponseProtocol(projectId, levelNumber);
		}
	}

	/**
	 * Finds candidate IDs of all selected candidates which are part of an active
	 * level.
	 * 
	 * @return the ids of the candidates
	 */
	private Set<String> getActiveSelectedCandidateIds() {
		SelectorInfo selectorInfo = sessionInfo.getCurrentProjectInfo().getSelectorInfo();
		Set<String> allSelectedCandidateIds = selectorInfo.getSelected();
		return selectorInfo.findOnlyIdsOfActiveLevels(allSelectedCandidateIds);
	}

	/**
	 * Turns a set of strings into a list of longs.
	 * 
	 * @param stringList the string set
	 * @return the long list
	 */
	private List<Long> castToLongList(Set<String> stringList) {
		List<Long> longList = new ArrayList<>();
		for (String candidateId : stringList) {
			longList.add(Long.parseLong(candidateId));
		}
		return longList;
	}

	/**
	 * Gets all levels from the project.
	 * 
	 * @param projectId identifies the project
	 * @return all levels stored in the project
	 */
	private List<Level> getAllLevels(long projectId) {
		Project project = projectController.find(projectId);
		return project.getLevels();
	}

	/**
	 * Gets the index of the last level
	 * 
	 * @param levels all levels
	 * @return the index of the last level
	 */
	private int getLastLevelIndex(List<Level> levels) {
		return levels.size() - 1;
	}

	/**
	 * Gets the ids of all candidates in the specified level
	 * 
	 * @param level the level to search in
	 * @return the ids of all candidates in the specified level
	 */
	private List<Long> getAllCandidateIds(Level level) {
		List<Long> allCandidates = new ArrayList<>();
		for (Candidate candidate : level.getCandidates()) {
			allCandidates.add(candidate.getCandidateId());
		}
		return allCandidates;
	}

	/**
	 * Sets the selected candidates as response for a level.
	 * 
	 * @param candidates the ids of the selected candidate
	 * @param level      the level that contains the candidates
	 */
	private void setSelectedCandidatesInLevel(List<Long> candidates, Level level) {
		level.setSelectedCandidates(candidates);
		level.setCandidatesSelected(true);
		levelController.update(level);
	}

	/**
	 * Notifies the protocol via the {@link ResponseManager} that the response
	 * arrived
	 * 
	 * @param projectId   identifies the project
	 * @param levelNumber the index of the level the answer belongs to
	 */
	private void notifyResponseProtocol(long projectId, int levelNumber) {
		ResponseIdentifier responseIdentifier = new ResponseIdentifier(projectId, levelNumber);
		eventService.fire(responseIdentifier);
	}

}
