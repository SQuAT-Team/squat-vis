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

	private boolean useRealValuesPareto = false;
	private boolean usePopulationPareto = false;
	private boolean showPareto = true;
	private boolean showSuggestions = true;
	private boolean useNameInsteadOfId = true;
	private boolean useMinimizedMatrix = true;
	
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
}
