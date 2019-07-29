package org.squat_team.vis.connector.style;

import java.io.Serializable;

/**
 * Provides the css class names for the specific options provided by
 * {@link GraphViewInfo}.
 */
public class GraphOptionsCSSProvider implements Serializable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = -5774407808002609589L;
	private static final String REDUCE_GRAPH_ON_CSS = "reduce-graph-active";
	private static final String POPULATION_ON_CSS = "population-active";

	public String getPopulationOnTag() {
		return POPULATION_ON_CSS;
	}

	public String getReduceGraphOnTag() {
		return REDUCE_GRAPH_ON_CSS;
	}

}
