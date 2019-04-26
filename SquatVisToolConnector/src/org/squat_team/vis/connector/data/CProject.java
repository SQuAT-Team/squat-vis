package org.squat_team.vis.connector.data;

import java.io.Serializable;

public class CProject implements Serializable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = -3541522376422764272L;

	private String name;

	public CProject(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
