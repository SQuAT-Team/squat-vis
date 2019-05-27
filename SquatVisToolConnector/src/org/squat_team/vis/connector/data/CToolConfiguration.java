package org.squat_team.vis.connector.data;

import java.io.Serializable;

import lombok.Data;
import lombok.NonNull;

/**
 * A description of the properties that the optimization tool sends. All values
 * have to be set. <br/>
 * <br/>
 * Note that {@link #setName(String)} sets a name for the configuration and
 * {@link #setToolName(String)} is the name of the visualization tool.
 */
@Data
public class CToolConfiguration implements Serializable {
	/**
	 * Generated
	 */
	private static final long serialVersionUID = -2493483029789861688L;

	private String name;
	private String toolName;
	private Boolean hasRealValues;
	private Boolean hasArchitectures;
	private Boolean hasUtilities;
	private Boolean hasParents;
	private Boolean supportSuggestions;

	public CToolConfiguration(@NonNull String name) {
		super();
		this.setName(name);
	}

}
