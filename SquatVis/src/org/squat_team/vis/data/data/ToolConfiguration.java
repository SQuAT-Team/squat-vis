package org.squat_team.vis.data.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

/**
 * A description of the properties that the optimization tool sends. <br/>
 * <br/>
 * Note that {@link #setName(String)} sets a name for the configuration and
 * {@link #setToolName(String)} is the name of the visualization tool.
 */
@Entity
@Data
public class ToolConfiguration {
	@Id
	@GeneratedValue
	private Long id;

	private String name;
	private String toolName;
	private Boolean hasRealValues;
	private Boolean hasUtilities;
	private Boolean hasArchitectures;
}
