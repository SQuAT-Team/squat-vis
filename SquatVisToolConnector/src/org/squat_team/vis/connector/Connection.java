package org.squat_team.vis.connector;

import java.io.Serializable;

public class Connection implements Serializable {
	/**
	 * Generated
	 */
	private static final long serialVersionUID = 8205540475117635046L;

	private final long projectId;

	public Connection(long projectId) {
		this.projectId = projectId;
	}

	public long getProjectId() {
		return projectId;
	}
}
