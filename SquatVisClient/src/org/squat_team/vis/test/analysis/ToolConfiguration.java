package org.squat_team.vis.test.analysis;

import lombok.Data;

/**
 * A description of the properties that the optimization tool sends. <br/>
 * <br/>
 * Note that {@link #setName(String)} sets a name for the configuration and
 * {@link #setToolName(String)} is the name of the visualization tool.
 */
@Data
public class ToolConfiguration {
	private Long id;

	private String name;
	private String toolName;
	private Boolean hasRealValues;
	private Boolean hasUtilities;
	private Boolean hasArchitectures;
	private Boolean hasParents;
	private Boolean supportSuggestions;
}
