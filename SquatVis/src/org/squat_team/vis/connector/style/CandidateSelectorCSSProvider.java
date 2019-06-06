package org.squat_team.vis.connector.style;

import java.io.Serializable;

/**
 * Provides the Tag css class names for the different selectors and their icons.
 */
public class CandidateSelectorCSSProvider implements Serializable {
	/**
	 * Generated
	 */
	private static final long serialVersionUID = -1237262111678874939L;
	private static final String FULL_STAR_CSS = "fas";
	private static final String HALF_STAR_CSS = "far";
	private static final String SELECTOR_MARKED_CSS = "marked";
	private static final String SELECTOR_SELECTED_CSS = "selected";
	private static final String SELECTOR_CURRENT_CSS = "current";
	private static final String SELECTOR_COMPARISON_CSS = "comparison";

	public String getStarType(boolean selected) {
		if (selected) {
			return FULL_STAR_CSS;
		} else {
			return HALF_STAR_CSS;
		}
	}

	public String getMarkedStyle() {
		return SELECTOR_MARKED_CSS;
	}

	public String getSelectedStyle() {
		return SELECTOR_SELECTED_CSS;
	}

	public String getCurrentStyle() {
		return SELECTOR_CURRENT_CSS;
	}

	public String getComparisonStyle() {
		return SELECTOR_COMPARISON_CSS;
	}

}
