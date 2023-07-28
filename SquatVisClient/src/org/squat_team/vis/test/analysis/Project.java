package org.squat_team.vis.test.analysis;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * A project is a independent group of results. Each project is associated with
 * a (continued) optimization tool run.
 */
@Data
public class Project {
	private Long id;
	private String name;
	private Integer numberOfGoals;
	private ToolConfiguration configuration;
	private Status status = new Status();
	private Goal goal;
	private List<Level> levels = new ArrayList<>();

	/**
	 * Gets the name of the optimization tool.
	 * 
	 * @return the name
	 */
	public String getToolName() {
		if (configuration == null || configuration.getToolName() == null || configuration.getToolName().isEmpty()) {
			return "Unknown";
		}
		return configuration.getToolName();
	}

	/**
	 * Gets the current number of levels in the project.
	 * 
	 * @return number of levels
	 */
	public int getNumberOfLevels() {
		if (levels == null) {
			return 0;
		}
		return levels.size();
	}

	/**
	 * Gets the number of candidates that are currently part of this project.
	 * 
	 * @return number of candidates
	 */
	public int getNumberOfCandidates() {
		int numberOfCandidates = 0;
		if (levels == null) {
			return 0;
		}
		for (Level level : levels) {
			numberOfCandidates += level.getNumberOfCandidates();
		}
		return numberOfCandidates;
	}

}
