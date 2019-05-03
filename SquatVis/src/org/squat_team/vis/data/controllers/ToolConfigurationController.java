package org.squat_team.vis.data.controllers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.squat_team.vis.data.daos.ToolConfigurationDao;
import org.squat_team.vis.data.data.Goal;
import org.squat_team.vis.data.data.ToolConfiguration;

/**
 * An {@link IController} for {@link Goal}s,
 */
@Named
@RequestScoped
public class ToolConfigurationController extends AbstractController<ToolConfiguration, ToolConfigurationDao> {
	@Inject
	private ToolConfigurationDao service;

	@Override
	public ToolConfigurationDao getService() {
		return service;
	}

}
