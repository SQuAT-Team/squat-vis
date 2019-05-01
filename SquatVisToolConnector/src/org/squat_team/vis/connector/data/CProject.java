package org.squat_team.vis.connector.data;

import java.io.Serializable;

import org.squat_team.vis.connector.ProjectConnector;

import lombok.Data;
import lombok.NonNull;

/**
 * A simplified project description. A new project should be used for new
 * optimization runs. <br/>
 * <br/>
 * To add data to an existing project, a {@link ProjectConnector} is required
 * instead of a {@link #CProject(String)}.
 */
@Data
public class CProject implements Serializable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = -3541522376422764272L;

	private String name;

	/**
	 * Creates a new project.
	 * 
	 * @param name Must not be null! Does not have to be unique, but it is
	 *             recommended as it might be shown to the user.
	 */
	public CProject(@NonNull String name) {
		this.name = name;
	}

}
