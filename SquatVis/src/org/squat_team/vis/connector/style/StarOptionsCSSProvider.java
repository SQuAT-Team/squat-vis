package org.squat_team.vis.connector.style;

import java.io.Serializable;

import org.squat_team.vis.session.StarViewInfo;

/**
 * Provides the css class names for the specific options provided by
 * {@link StarViewInfo}.
 */
public class StarOptionsCSSProvider implements Serializable {
	/**
	 * Generated
	 */
	private static final long serialVersionUID = -5856344665780774742L;

	private static final String POPULATION_ON_CSS = "population-active";

	public String getPopulationOnTag() {
		return POPULATION_ON_CSS;
	}

}
