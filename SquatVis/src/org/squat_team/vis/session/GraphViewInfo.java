package org.squat_team.vis.session;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.squat_team.vis.connector.style.GraphOptionsCSSProvider;

import lombok.Data;

/**
 * Provides the specific options for the star view.
 */
@Data
public class GraphViewInfo implements Serializable {
	/**
	 * Generated
	 */
	private static final long serialVersionUID = -8470311285184734884L;

	private boolean showPopulation = true;
	private boolean reduceGraph = false;
	private String mode = "All";
	private GraphOptionsCSSProvider graphOptionsCssProvider = new GraphOptionsCSSProvider();

	public boolean getShowPopulation() {
		return showPopulation;
	}

	public String getShowPopulationStyle() {
		if (getShowPopulation()) {
			return graphOptionsCssProvider.getPopulationOnTag();
		}
		return "";
	}
	
	public boolean getReduceGraph() {
		return reduceGraph;
	}

	public String getReduceGraphStyle() {
		if (getReduceGraph()) {
			return graphOptionsCssProvider.getReduceGraphOnTag();
		}
		return "";
	}
	
	public String getMode() {
		return mode;
	}

	public void setMode() {
		String newMode = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		this.mode = newMode;
	}
	
	public void setShowPopulation() {
		String newState = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		this.showPopulation = Boolean.parseBoolean(newState);
	}
	
	public void setReduceGraph() {
		String newState = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		this.reduceGraph = Boolean.parseBoolean(newState);
	}
	
}
