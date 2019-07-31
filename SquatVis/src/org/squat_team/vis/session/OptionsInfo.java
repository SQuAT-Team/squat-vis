package org.squat_team.vis.session;

import java.io.Serializable;

import org.squat_team.vis.data.data.Project;

import lombok.Data;

/**
 * Knows the options for a single {@link Project} and session.
 */
@Data
public class OptionsInfo implements Serializable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = 7745830674295348641L;

	private boolean useRealValuesPareto = false;
	private boolean usePopulationPareto = false;
	private boolean showPareto = true;
	private boolean showSuggestions = true;
	private boolean useNameInsteadOfId = true;
}
