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
	private static final String PARETO_TAG_CSS = "pareto-on";
	private static final String SUGGESTION_TAG_CSS = "suggestion-on";

	public String getParetoTag() {
		return PARETO_TAG_CSS;
	}

	public String getSuggestionTag() {
		return SUGGESTION_TAG_CSS;
	}

}
