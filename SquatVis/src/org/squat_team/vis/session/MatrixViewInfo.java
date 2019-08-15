package org.squat_team.vis.session;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.squat_team.vis.connector.style.MatrixOptionsCSSProvider;

import lombok.Data;

/**
 * Provides the specific options for the matrix view.
 */
@Data
public class MatrixViewInfo implements Serializable {
	/**
	 * Generated
	 */
	private static final long serialVersionUID = -6298299571486000929L;
	
	private boolean showPareto = true;
	private boolean showSuggestions = true;
	private boolean showParents = true;
	private boolean highlightInitial = true;
	private String mode = "All";
	private MatrixOptionsCSSProvider matrixOptionsCssProvider = new MatrixOptionsCSSProvider();

	public boolean getShowPareto() {
		return showPareto;
	}
	
	public boolean getShowSuggestions() {
		return showSuggestions;
	}
	
	public boolean getShowParents() {
		return showParents;
	}
	
	public boolean getHighlightInitial() {
		return highlightInitial;
	}

	public String getShowParentsStyle() {
		if (getShowParents()) {
			return matrixOptionsCssProvider.getParentsOnTag();
		}
		return "";
	}
	
	public String getShowParetoStyle() {
		if (getShowPareto()) {
			return matrixOptionsCssProvider.getParetoOnTag();
		}
		return "";
	}
	
	public String getShowSuggestionsStyle() {
		if (getShowSuggestions()) {
			return matrixOptionsCssProvider.getSuggestionsOnTag();
		}
		return "";
	}
	
	public String getHighlightInitialStyle() {
		if (getHighlightInitial()) {
			return matrixOptionsCssProvider.getInitialsOnTag();
		}
		return "";
	}

	public void setShowPareto() {
		String newState = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		this.showPareto = Boolean.parseBoolean(newState);
	}
	
	public void setShowSuggestions() {
		String newState = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		this.showSuggestions = Boolean.parseBoolean(newState);
	}
	
	public void setMode() {
		String newMode = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		this.mode = newMode;
	}
	
	public void setShowParents() {
		String newState = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		this.showParents = Boolean.parseBoolean(newState);
	}
	
	public void setHighlightInitial() {
		String newState = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		this.highlightInitial = Boolean.parseBoolean(newState);
	}

}
