package org.squat_team.vis.data.controllers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.squat_team.vis.data.daos.ProjectDao;
import org.squat_team.vis.data.data.Project;

@Named
@RequestScoped
public class ProjectController extends AbstractController<Project, ProjectDao> {
	
	@Inject
	private ProjectDao service;

	public ProjectDao getService() {
		return service;
	}
	
	@Override
	public void add() {
		getService().save(datum);
		init();
	}
}
