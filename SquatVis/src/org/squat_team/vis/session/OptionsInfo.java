package org.squat_team.vis.session;

import java.io.Serializable;

import javax.faces.context.FacesContext;

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

	private ParetoMode paretoMode = ParetoMode.UTILITY_LEVEL_PARETO;
	private boolean showPareto = true;
	private boolean showSuggestions = true;
	private boolean useNameInsteadOfId = true;
	private boolean useMinimizedMatrix = true;
	private boolean shortenName = true;
	private int smallElementsFilterSize = 0;
	private int linkLength = 75;
	private float populationTransparency = 0.15f;
	
	public void setParetoMode() {
		String newMode = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		switch(newMode) {
		case "REAL_VALUE_LEVEL_PARETO":
			this.paretoMode = ParetoMode.REAL_VALUE_LEVEL_PARETO;
			break;
		case "REAL_VALUE_POPULATION_PARETO":
			this.paretoMode = ParetoMode.REAL_VALUE_POPULATION_PARETO;
			break;
		case "UTILITY_LEVEL_PARETO":
			this.paretoMode = ParetoMode.UTILITY_LEVEL_PARETO;
			break;
		case "UTILITY_POPULATION_PARETO":
			this.paretoMode = ParetoMode.UTILITY_POPULATION_PARETO;
			break;
		}
	}
	
	public void setUseNameInsteadOfId() {
		String newState = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		this.useNameInsteadOfId = Boolean.parseBoolean(newState);
	}
	
	public boolean getUseNameInsteadOfId() {
		return this.useNameInsteadOfId;
	}
	
	public void setUseMinimizedMatrix() {
		String newState = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		this.useMinimizedMatrix = Boolean.parseBoolean(newState);
	}
	
	public boolean getUseMinimizedMatrix() {
		return this.useMinimizedMatrix;
	}
	
	public void setShortenName() {
		String newState = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		this.shortenName = Boolean.parseBoolean(newState);
	}
	
	public boolean getShortenName() {
		return this.shortenName;
	}
	
	public enum ParetoMode {
		REAL_VALUE_LEVEL_PARETO, REAL_VALUE_POPULATION_PARETO, UTILITY_LEVEL_PARETO, UTILITY_POPULATION_PARETO;
	}
}
