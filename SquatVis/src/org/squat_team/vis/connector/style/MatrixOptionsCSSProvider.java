package org.squat_team.vis.connector.style;

import java.io.Serializable;

import org.squat_team.vis.session.MatrixViewInfo;

/**
 * Provides the css class names for the specific options provided by
 * {@link MatrixViewInfo}.
 */
public class MatrixOptionsCSSProvider implements Serializable {
	/**
	 * Generated
	 */
	private static final long serialVersionUID = -5856344665780774742L;

	private static final String PARETO_ON_CSS = "pareto-active";
	private static final String SUGGESTIONS_ON_CSS = "suggestions-active";
	private static final String PARENTS_ON_CSS = "parents-active";
	private static final String INITIAL_ON_CSS = "initial-active";

	public String getParetoOnTag() {
		return PARETO_ON_CSS;
	}
	
	public String getSuggestionsOnTag() {
		return SUGGESTIONS_ON_CSS;
	}

	public String getParentsOnTag() {
		return PARENTS_ON_CSS;
	}

	public String getInitialsOnTag() {
		return INITIAL_ON_CSS;
	}
}
