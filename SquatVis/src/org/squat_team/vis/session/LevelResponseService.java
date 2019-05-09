package org.squat_team.vis.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.squat_team.vis.data.controllers.LevelController;
import org.squat_team.vis.data.controllers.ProjectController;
import org.squat_team.vis.data.data.Candidate;
import org.squat_team.vis.data.data.Level;
import org.squat_team.vis.data.data.Project;
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
		List<Long> allCandidates = new ArrayList<Long>();
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
