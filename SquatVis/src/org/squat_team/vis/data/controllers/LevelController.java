package org.squat_team.vis.data.controllers;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.squat_team.vis.data.daos.LevelDao;
import org.squat_team.vis.data.data.Level;

@Named
@RequestScoped
public class LevelController extends AbstractController<Level, LevelDao>{

	@EJB
	private LevelDao service;

	public LevelDao getService() {
		return service;
	}
	
	@Override
	public void add() {
		getService().save(datum);
		init();
	}
}
