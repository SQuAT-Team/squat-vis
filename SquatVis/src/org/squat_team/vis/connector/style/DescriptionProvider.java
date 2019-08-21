package org.squat_team.vis.connector.style;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Provides and formats the detailed, human-readable descriptions shown in
 * popup-messages on the frontend pages.
 */
@Named
@RequestScoped
public class DescriptionProvider {
	private static final String COMPARISON_DESCRIPTION = "Candidates that should be compared to each other. A color is mapped to each candidate. The view might has to be set to comparison mode to see an effect.";
	private static final String CURRENT_DESCRIPTION = "Candidates that are currently highlighted in the view, e.g., by brushing.";
	private static final String MARKED_DESCRIPTION = "These candidates are 'interesting' from the user's perspecitve, but not completely evaluated yet. These candidates are usually potential selected candidates.";
	private static final String SELECTED_DESCRIPTION = "Candidates that should be taken to the next level of optimization or exported as final results.";
	private static final String ALL_DESCRIPTION = "All (visible) candidates are listed here.";
	private static final String REDUCE_GRAPH_DESCRIPTION = "Nodes and links used by all candidates are hidden. Thus, only differences become visible.";
	private static final String SHOW_POPULATION_STAR_DESCRIPTION = "The available candidates are shown in the background of the star plot. Allows a rough comparison with the population.";
	private static final String SHOW_PARENTS_MATRIX_DESCRIPTION = "Shows parent relation in the matrix view for current candidates.";
	private static final String SHOW_PARETO_MATRIX_DESCRIPTION = "Shows red circles around Pareto candidates.";
	private static final String SHOW_SUGGESTIONS_MATRIX_DESCRIPTION = "Shows blue circles around suggestion candidates.";
	private static final String HIGHLIGHTS_INITIAL_CANDIDATES_MATRIX_DESCRIPTION = "Highlights the initial candidates as green circle.";
	private static final String DROPDDOWN_GRAPH_DESCRIPTION = "Selects the used population of candidates for the resources, components, and allocation.";
	private static final String DROPDDOWN_DETAILED_STAR_DESCRIPTION = "Selects the group of candidates that should be shown in the big star plot.";
	private static final String DROPDDOWN_CANDIDATES_STAR_DESCRIPTION = "Filters the list of candidates depending on the selected option.";
	private static final String DROPDDOWN_MATRIX_DESCRIPTION = "Controls the coloring of circles depending on the group. Does not hide any elements.";
	private static final String SEARCH_DESCRIPTION = "Filter the list by searching for candidate names, numbers or special tags. Type 'p' for Pareto candidates, 's' for suggested candidates, and 'i' for initial candidates.";
	
	public String getLevelsCandidateDescription(int numberOfLevels, int numberOfCandidates) {
		return "Project contains " + numberOfLevels + " Levels and " + numberOfCandidates + " Candidates";
	}

	public String getComparisonDescription() {
		return COMPARISON_DESCRIPTION;
	}

	public String getCurrentDescription() {
		return CURRENT_DESCRIPTION;
	}

	public String getMarkedDescription() {
		return MARKED_DESCRIPTION;
	}

	public String getSelectedDescription() {
		return SELECTED_DESCRIPTION;
	}

	public String getAllDescription() {
		return ALL_DESCRIPTION;
	}
	
	public String getReduceGraphDescription() {
		return REDUCE_GRAPH_DESCRIPTION;
	}
	
	public String getShowPopulationStarDescription() {
		return SHOW_POPULATION_STAR_DESCRIPTION;
	}
	
	public String getDropdownGraphDescription() {
		return DROPDDOWN_GRAPH_DESCRIPTION;
	}
	
	public String getDropdownDetailedStarDescription() {
		return DROPDDOWN_DETAILED_STAR_DESCRIPTION;
	}
	
	public String getDropdownCandidatesStarDescription() {
		return DROPDDOWN_CANDIDATES_STAR_DESCRIPTION;
	}
	
	public String getSearchDescription() {
		return SEARCH_DESCRIPTION;
	}
	
	public String getDropdownMatrixDescription() {
		return DROPDDOWN_MATRIX_DESCRIPTION;
	}	
	
	public String getShowParentsMatrixDescription() {
		return SHOW_PARENTS_MATRIX_DESCRIPTION;
	}
	
	public String getShowParetoMatrixDescription() {
		return SHOW_PARETO_MATRIX_DESCRIPTION;
	}
	
	public String getShowSuggestionsDescription() {
		return SHOW_SUGGESTIONS_MATRIX_DESCRIPTION;
	}
	
	public String getHighlightInitialDescription() {
		return HIGHLIGHTS_INITIAL_CANDIDATES_MATRIX_DESCRIPTION;
	}
	
}
