package org.squat_team.vis.connector.style;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Maps Booleans to the correct CSS-Tags for coloring. False is mapped to red,
 * true is mapped to green.
 */
@Named
@RequestScoped
public class BooleanToBootstrapColorMapper {
	private static final String RED_COLOR = "bg-danger";
	private static final String GREEN_COLOR = "bg-success";

	/**
	 * Maps a boolean to a bootstrap color class. Red for false, green for true.
	 * 
	 * @param value the boolean
	 * @return the corresponding color boostrap css tag
	 */
	public String map(boolean value) {
		if (value) {
			return GREEN_COLOR;
		} else {
			return RED_COLOR;
		}
	}

}
