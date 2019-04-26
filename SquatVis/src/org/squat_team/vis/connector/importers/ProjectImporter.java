package org.squat_team.vis.connector.importers;

import org.squat_team.vis.connector.data.CProject;
import org.squat_team.vis.connector.server.ServerService;
import org.squat_team.vis.data.daos.ProjectDao;
import org.squat_team.vis.data.data.Project;

public class ProjectImporter extends AbstractImporter<CProject, Project> {

	public ProjectImporter(ServerService serverService) {
		super(serverService, null);
	}

	@Override
	public Project transform(CProject cproject) {
		Project project = new Project();
		project.setName(cproject.getName());

		ProjectDao dao = serverService.getProjectDao();
		dao.save(project);

		return project;
	}

}
