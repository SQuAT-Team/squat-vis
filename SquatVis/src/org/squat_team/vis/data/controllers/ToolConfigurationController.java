package org.squat_team.vis.data.controllers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.squat_team.vis.data.daos.ToolConfigurationDao;
import org.squat_team.vis.data.data.ToolConfiguration;

@Named
@RequestScoped
public class ToolConfigurationController extends AbstractController<ToolConfiguration, ToolConfigurationDao> {
	@Inject
	private ToolConfigurationDao service;

	public ToolConfigurationDao getService() {
		return service;
	}
	
	@Override
	public void add() {
		getService().save(datum);
		init();
	}
}
