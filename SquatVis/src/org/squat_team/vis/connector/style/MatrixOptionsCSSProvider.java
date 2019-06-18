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

	private static final String TAGS_ON_CSS = "tags-active";
	private static final String PARENTS_ON_CSS = "parents-active";

	public String getTagsOnTag() {
		return TAGS_ON_CSS;
	}

	public String getParentsOnTag() {
		return PARENTS_ON_CSS;
	}
}
