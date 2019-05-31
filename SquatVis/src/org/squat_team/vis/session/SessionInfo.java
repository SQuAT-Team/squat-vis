package org.squat_team.vis.session;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.squat_team.vis.data.daos.ProjectDao;
import org.squat_team.vis.data.data.Project;
import org.squat_team.vis.transformers.CsvExporter;

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
	
	@Inject
	private ProjectDao projectDao;
	private long selectedProject;
	private transient Project project;
	
	/**
	 * Sets the project and navigates to the project page.
	 * 
	 * @param selectedProject the id of the selected project.
	 * @return navigation to the project page.
	 */
	public String setSelectedProject(long selectedProject) {
		this.selectedProject = selectedProject;
		this.project = projectDao.find(selectedProject);
		return "project?faces-redirect=true";
	}

	/**
	 * Updates the currently active project in the database.
	 */
	public void updateProject() {
		projectDao.update(project);
	}

	public String getProjectDataAsCSV() {
		CsvExporter exporter = new CsvExporter();
		return exporter.export(project);
	}
	
}
