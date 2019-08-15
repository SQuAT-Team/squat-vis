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
	
	private boolean showTags = true;
	private boolean showParents = true;
	private boolean highlightInitial = true;
	private String mode = "All";
	private MatrixOptionsCSSProvider matrixOptionsCssProvider = new MatrixOptionsCSSProvider();

	
	public MatrixViewInfo() {
		this.showTags = true;
	}
	public boolean getShowTags() {
		return showTags;
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
	
	public String getShowTagsStyle() {
		if (getShowTags()) {
			return matrixOptionsCssProvider.getTagsOnTag();
		}
		return "";
	}
	
	public String getHighlightInitialStyle() {
		if (getHighlightInitial()) {
			return matrixOptionsCssProvider.getInitialsOnTag();
		}
		return "";
	}

	public void setShowTags() {
		String newState = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		this.showTags = Boolean.parseBoolean(newState);
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
