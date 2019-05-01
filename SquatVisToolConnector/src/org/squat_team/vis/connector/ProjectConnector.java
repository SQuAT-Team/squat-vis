package org.squat_team.vis.connector;

import java.io.Serializable;

import lombok.Data;

/**
 * Associates a request to a project. The receiver assures that no other
 * projects are modified by the request.
 */
@Data
public class ProjectConnector implements Serializable {
	/**
	 * Generated
	 */
	private static final long serialVersionUID = 8205540475117635046L;

	private final long projectId;

	public ProjectConnector(long projectId) {
		this.projectId = projectId;
	}
}
