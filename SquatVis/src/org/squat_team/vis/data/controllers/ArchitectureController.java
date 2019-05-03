package org.squat_team.vis.data.controllers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.squat_team.vis.data.daos.ArchitectureDao;
import org.squat_team.vis.data.data.Architecture;

/**
 * An {@link IController} for {@link Architecture}s,
 */
@Named
@RequestScoped
public class ArchitectureController extends AbstractController<Architecture, ArchitectureDao> {
	@Inject
	private ArchitectureDao service;

	@Override
	public ArchitectureDao getService() {
		return service;
	}

}
