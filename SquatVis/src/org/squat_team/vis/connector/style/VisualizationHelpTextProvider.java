package org.squat_team.vis.connector.style;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Provides help texts for visualizations that are shown to the user.
 */
@Named
@RequestScoped
public class VisualizationHelpTextProvider {
	private static final String GRAPH_GOALS_DESCRIPTION = "Visualizes the utility values of the current candidates. RIGHT CLICK: Group a single candidate as current.";
	private static final String GRAPH_RESOURCES_DESCRIPTION = "Shows the configured value of resources in the architecture, e.g., CPU-clock-rate. RIGHT CLICK LINE: Group a single candidate as current. RIGHT CLICK AXIS NAME: Highlights the server it is located at and all other resources at this server.";
	private static final String GRAPH_COMPONENTS_DESCRIPTION = "Nodes represent the components of the active architectures. Links show dependencies between components. Node radius and link size increases when more candidates contain this element. LEFT CLICK NODE: Enables movement of the node and moves it to the front. RIGHT CLICK NODE/LINK: Sets all candidates to current that contain this element.";
	private static final String GRAPH_ALLOCATION_DESCRIPTION = "Round nodes represent the components of the active architectures. Rectangle nodes represent servers. Links show allocations of components to servers. Node radius and link size increases when more candidates contain this element. LEFT CLICK NODE: Enables movement of the node and moves it to the front. RIGHT CLICK NODE/LINK: Sets all candidates to current that contain this element. RIGHT CLICK SERVER: Highlights the resources which are located at this server.";
	
	private static final String STAR_DETAILED_DESCRIPTION = "Visualizes the utility values of the active group of candidates. The whole population can be shown in the background. Transparency of population candidates can be changed in the options.";
	private static final String STAR_CANDIDATES_DESCRIPTION = "Lists the active candidates. LEFT CLICK CANDIDATE: Adds a candidate to the current group. RIGHT CLICK CANDIDATE: Opens a menu for grouping of the candidate.";

	private static final String MATRIX_DETAILED_DESCRIPTION = "Shows the active scatterplot. Red cirles show Pareto candidates, blue circles suggested candidates. Only this view shows parent relationships. LEFT CLICK: Groups all the brushed candidates as current.";
	private static final String MATRIX_OVERVIEW_DESCRIPTION = "Each scatterplot show the relationship between two goals. The red scatterplot is the active one shown in the detailed area. LEFT CLICK: Groups all the brushed candidates as current. RIGHT CLICK: Marks a scatterplot as active.";
	private static final String MATRIX_CURRENT_DESCRIPTION = "Shows the candidates in the current group.";
	
	public String getGraphGoalsText() {
		return GRAPH_GOALS_DESCRIPTION;
	}
	
	public String getGraphResourcesText() {
		return GRAPH_RESOURCES_DESCRIPTION;
	}
	
	public String getGraphComponentsText() {
		return GRAPH_COMPONENTS_DESCRIPTION;
	}
	
	public String getGraphAllocationText() {
		return GRAPH_ALLOCATION_DESCRIPTION;
	}
	
	public String getStarDetailedText() {
		return STAR_DETAILED_DESCRIPTION;
	}
	
	public String getStarCandidatesText() {
		return STAR_CANDIDATES_DESCRIPTION;
	}
	
	public String getMatrixDetailedText() {
		return MATRIX_DETAILED_DESCRIPTION;
	}
	
	public String getMatrixOverviewText() {
		return MATRIX_OVERVIEW_DESCRIPTION;
	}
	
	public String getMatrixCurrentText() {
		return MATRIX_CURRENT_DESCRIPTION;
	}
}
