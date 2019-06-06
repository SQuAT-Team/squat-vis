package org.squat_team.vis.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

/**
 * Knows colors that are assigned to the candidates. These colors are used in
 * the comparison mode.
 */
public class ColorInfo implements Serializable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = -5519962380783182799L;

	private Map<String, String> colorMapping = new HashMap<>();
	private String[] defaultColors = { "#800000", "#177E89", "#DB3A34", "#FFC857", "#084C61", "#EF476F", "#3F220F" };
	private int defaultColorsIndex = 0;

	public String getColor(String id) {
		String color = colorMapping.get(id);
		if (color == null) {
			color = getDefaultColor();
			colorMapping.put(id, color);
		}
		return color;
	}

	public void setColor() {
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		String color = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("color");
		colorMapping.put(id, color);
	}

	private String getDefaultColor() {
		String defaultColor = defaultColors[defaultColorsIndex];
		defaultColorsIndex++;
		return defaultColor;
	}
}
