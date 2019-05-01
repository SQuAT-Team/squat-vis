package org.squat_team.vis.connector.importers;

import java.util.Date;

import org.squat_team.vis.connector.data.CProject;
import org.squat_team.vis.connector.server.ConnectorService;
import org.squat_team.vis.data.daos.ProjectDao;
import org.squat_team.vis.data.data.Project;
import org.squat_team.vis.data.data.Status;

public class ProjectImporter extends AbstractImporter<CProject, Project> {

	public ProjectImporter(ConnectorService connectorService) {
		super(connectorService, null);
	}

	@Override
	public Project transform(CProject cproject) {
		Project project = new Project();
		project.setName(cproject.getName());
		Date currentDate = new Date();
		Status status = findStatus(project);
		status.setCreationTime(currentDate);
		status.setLevelStarted(currentDate);
		status.setLastUpdate(currentDate);
		ProjectDao dao = connectorService.getProjectDao();
		dao.save(project);

		return project;
	}
	
	private Status findStatus(Project project) {
		Status status = project.getStatus();
		if (status == null) {
			status = new Status();
			project.setStatus(status);
		}
		return status;
	}

}
