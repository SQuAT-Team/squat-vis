package org.squat_team.vis.session;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import lombok.Data;

/**
 * A class that stores session specific information, e.g., which project is
 * currently selected.
 */
@Data
@Named
@SessionScoped
public class SessionInfo implements Serializable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = 6462742507992003369L;

	private long selectedProject;

	/**
	 * Sets the project and navigates to the project page.
	 * 
	 * @param selectedProject the id of the selected project.
	 * @return navigation to the project page.
	 */
	public String setSelectedProject(long selectedProject) {
		this.selectedProject = selectedProject;
		return "project";
	}
}
