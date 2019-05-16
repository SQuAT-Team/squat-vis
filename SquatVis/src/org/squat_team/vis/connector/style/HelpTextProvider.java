package org.squat_team.vis.connector.style;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Provides help texts that are shown to the user.
 */
@Named
@RequestScoped
public class HelpTextProvider {
	private static final String REAL_VALUE_DESCRIPTION = "Real values are the meassured or predicted metrics, e.g., response time in seconds for performance.";
	private static final String UTILITIES_DESCRIPTION = "Utility values are real values that are evaluated with respect to the architects preferences. A value of 0 represents the worst utility, while a value of 1 represents the highest utility.";
	private static final String HAS_ARCHITECTURES_DESCRIPTION = "A candidate has an architecture, if some architectural model, e.g., an instance of the Palladio Component Model, is available and sent to the visualization tool. Otherwise only values are sent.";
	private static final String HAS_PARENTS_DESCRIPTION = "The parent of a candidate is a candidate that has been used to derive the child candidate.";

	public String getRealValueText() {
		return REAL_VALUE_DESCRIPTION;
	}

	public String getUtilityText() {
		return UTILITIES_DESCRIPTION;
	}

	public String getArchitecturesText() {
		return HAS_ARCHITECTURES_DESCRIPTION;
	}

	public String getParentsText() {
		return HAS_PARENTS_DESCRIPTION;
	}

}
