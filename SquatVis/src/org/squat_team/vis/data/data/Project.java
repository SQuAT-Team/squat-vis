package org.squat_team.vis.data.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Project {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private Date lastModified;
	private Integer numberOfGoals;
	// @OneToOne
	private ToolConfiguration configuration;
	private Status status;
	// @OneToOne
	private Goal goal;
	@OneToMany
	private List<Level> levels = new ArrayList<Level>();

	public String getToolName() {
		if (configuration == null || configuration.getName() == null || configuration.getName().isEmpty()) {
			return "Unknown";
		}
		return configuration.getToolName();
	}

	public int getNumberOfLevels() {
		if (levels == null) {
			return 0;
		}
		return levels.size();
	}

	public int getNumberOfCandidates() {
		int numberOfCandidates = 0;
		if (levels == null) {
			return 0;
		}
		for (Level level : levels) {
			List<Candidate> candidatesInLevel = level.getCandidates();
			if (candidatesInLevel != null) {
				numberOfCandidates += candidatesInLevel.size();
			}
		}
		return numberOfCandidates;
	}

}
