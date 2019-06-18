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
	private static final String COMPARISON_DESCRIPTION = "Candidates that should be compared to each other. A color is mapped to each candidate. The view might has to be set to comparison mode to see an effect.";
	private static final String CURRENT_DESCRIPTION = "Candidates that are currently highlighted in the view, e.g., by brushing.";
	private static final String MARKED_DESCRIPTION = "These candidates are 'interesting' from the user's perspecitve, but not completely evaluated yet. These candidates are usually potential selected candidates.";
	private static final String SELECTED_DESCRIPTION = "Candidates that should be taken to the next level of optimization or exported as final results.";
	private static final String ALL_DESCRIPTION = "All (visible) candidates are listed here.";

	public String getLevelsCandidateDescription(int numberOfLevels, int numberOfCandidates) {
		return "Project contains " + numberOfLevels + " Levels and " + numberOfCandidates + " Candidates";
	}

	public String getComparisonDescription() {
		return COMPARISON_DESCRIPTION;
	}

	public String getCurrentDescription() {
		return CURRENT_DESCRIPTION;
	}

	public String getMarkedDescription() {
		return MARKED_DESCRIPTION;
	}

	public String getSelectedDescription() {
		return SELECTED_DESCRIPTION;
	}

	public String getAllDescription() {
		return ALL_DESCRIPTION;
	}
}
