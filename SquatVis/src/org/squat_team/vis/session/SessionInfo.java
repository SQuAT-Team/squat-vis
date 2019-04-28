package org.squat_team.vis.session;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import lombok.Data;

@Data
@Named
@SessionScoped
public class SessionInfo implements Serializable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = 6462742507992003369L;

	private long selectedProject;

	public String setSelectedProject(long selectedProject) {
		this.selectedProject = selectedProject;
		return "project";
	}
}
