package org.squat_team.vis.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Transient;

import org.squat_team.vis.data.daos.ProjectDao;
import org.squat_team.vis.data.data.Candidate;
import org.squat_team.vis.data.data.Level;
import org.squat_team.vis.data.data.Project;
import org.squat_team.vis.transformers.ArchitectureAllocationsToCSVExporter;
import org.squat_team.vis.transformers.ArchitectureComponentsToCSVExporter;
import org.squat_team.vis.transformers.ArchitectureLinksToCSVExporter;
import org.squat_team.vis.transformers.ArchitectureResourceContainersToCSVExporter;
import org.squat_team.vis.transformers.ArchitectureResourcesToCSVExporter;
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
	private Map<Long, ProjectInfo> projectInfos = new HashMap<>();
	@Transient
	private ArchitectureResourcesToCSVExporter resourcesExporter;

	public ProjectInfo getCurrentProjectInfo() {
		if (project == null) {
			return null;
		}
		long projectId = project.getId();
		return projectInfos.computeIfAbsent(projectId, k -> new ProjectInfo(getAllCandidates()));
	}

	public List<Candidate> getAllCandidates() {
		List<Candidate> candidates = new ArrayList<>();
		for (Level level : project.getLevels()) {
			candidates.addAll(level.getCandidates());
		}
		return candidates;
	}

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
		return exporter.export(project, getCurrentProjectInfo());
	}

	public String getProjectArchitectureComponentsAsCSV() {
		ArchitectureComponentsToCSVExporter exporter = new ArchitectureComponentsToCSVExporter();
		return exporter.export(project, getCurrentProjectInfo());
	}

	public String getProjectArchitectureComponentLinksAsCSV() {
		ArchitectureLinksToCSVExporter exporter = new ArchitectureLinksToCSVExporter();
		return exporter.export(project, getCurrentProjectInfo());
	}

	public String getProjectArchitectureServersAsCSV() {
		ArchitectureResourceContainersToCSVExporter exporter = new ArchitectureResourceContainersToCSVExporter();
		return exporter.export(project, getCurrentProjectInfo());
	}

	public String getProjectArchitectureAllocationsAsCSV() {
		ArchitectureAllocationsToCSVExporter exporter = new ArchitectureAllocationsToCSVExporter();
		return exporter.export(project, getCurrentProjectInfo());
	}

	public String getProjectArchitectureResourcesAsCSV() {
		resourcesExporter = new ArchitectureResourcesToCSVExporter();
		return resourcesExporter.export(project, getCurrentProjectInfo());
	}
	
	public String getProjectArchitectureResourcesMetadataAsCSV() {
		return resourcesExporter.exportResourcesMetadata();
	}

}
