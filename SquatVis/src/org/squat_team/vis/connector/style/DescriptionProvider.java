package org.squat_team.vis.connector.style;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class DescriptionProvider {

	public String getLevelsCandidateDescription(int numberOfLevels, int numberOfCandidates) {
		return "Project contains " + numberOfLevels + " Levels and " + numberOfCandidates + " Candidates";
	}
}
