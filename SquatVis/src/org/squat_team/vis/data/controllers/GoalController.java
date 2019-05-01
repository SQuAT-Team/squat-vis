package org.squat_team.vis.data.controllers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.squat_team.vis.data.daos.GoalDao;
import org.squat_team.vis.data.data.Goal;

@Named
@RequestScoped
public class GoalController extends AbstractController<Goal, GoalDao> {
	@Inject
	private GoalDao service;

	public GoalDao getService() {
		return service;
	}
	
	@Override
	public void add() {
		getService().save(datum);
		init();
	}
}
