package org.squat_team.vis.connector.style;

import java.io.Serializable;

/**
 * Provides the Tag css class names for the different tags.
 */
public class CandidateTagCSSProvider implements Serializable {
	/**
	 * Generated
	 */
	private static final long serialVersionUID = 4585922370479891147L;
	private static final String PARETO_REAL_LEVEL_TAG_CSS = "pareto-real-level";
	private static final String PARETO_REAL_POPULATION_TAG_CSS = "pareto-real-population";
	private static final String PARETO_UTILITY_LEVEL_TAG_CSS = "pareto-utility-level";
	private static final String PARETO_UTILITY_POPULATION_TAG_CSS = "pareto-utility-population";
	private static final String SUGGESTION_TAG_CSS = "suggestion-on";

	public String getParetoRealLevelTag() {
		return PARETO_REAL_LEVEL_TAG_CSS;
	}

	public String getParetoRealPopulationTag() {
		return PARETO_REAL_POPULATION_TAG_CSS;
	}

	public String getParetoUtilityLevelTag() {
		return PARETO_UTILITY_LEVEL_TAG_CSS;
	}

	public String getParetoUtilityPopulationTag() {
		return PARETO_UTILITY_POPULATION_TAG_CSS;
	}

	public String getSuggestionTag() {
		return SUGGESTION_TAG_CSS;
	}

}
