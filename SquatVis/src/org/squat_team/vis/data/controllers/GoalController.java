package org.squat_team.vis.data.controllers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.squat_team.vis.data.daos.GoalDao;
import org.squat_team.vis.data.data.Goal;

/**
 * An {@link IController} for {@link Goal}s,
 */
@Named
@RequestScoped
public class GoalController extends AbstractController<Goal, GoalDao> {
	@Inject
	private GoalDao service;

	@Override
	public GoalDao getService() {
		return service;
	}

}