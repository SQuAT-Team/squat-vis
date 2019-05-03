package org.squat_team.vis.connector.style;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Provides and formats the detailed, human-readable descriptions shown in
 * popup-messages on the frontend pages.
 */
@Named
@RequestScoped
public class DescriptionProvider {

	public String getLevelsCandidateDescription(int numberOfLevels, int numberOfCandidates) {
		return "Project contains " + numberOfLevels + " Levels and " + numberOfCandidates + " Candidates";
	}
}
