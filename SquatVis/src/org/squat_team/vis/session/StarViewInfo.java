package org.squat_team.vis.session;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.squat_team.vis.connector.style.StarOptionsCSSProvider;

import lombok.Data;

/**
 * Provides the specific options for the star view.
 */
@Data
public class StarViewInfo implements Serializable {
	/**
	 * Generated
	 */
	private static final long serialVersionUID = -8470311285184734884L;

	private boolean showPopulation = true;
	private String mode = "All";
	private String modeBig = "Current";
	private StarOptionsCSSProvider starOptionsCssProvider = new StarOptionsCSSProvider();

	public boolean getShowPopulation() {
		return showPopulation;
	}

	public String getShowPopulationStyle() {
		if (getShowPopulation()) {
			return starOptionsCssProvider.getPopulationOnTag();
		}
		return "";
	}

	public void setMode() {
		String newMode = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		this.mode = newMode;
	}

	public void setModeBig() {
		String newMode = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		this.modeBig = newMode;
	}
	
	public void setShowPopulation() {
		String newState = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		this.showPopulation = Boolean.parseBoolean(newState);
	}
	
}
