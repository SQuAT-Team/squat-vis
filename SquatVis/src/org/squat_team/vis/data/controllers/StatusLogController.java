package org.squat_team.vis.data.controllers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.squat_team.vis.data.daos.StatusLogDao;
import org.squat_team.vis.data.data.StatusLog;

/**
 * An {@link IController} for {@link StatusLog}s,
 */
@Named
@RequestScoped
public class StatusLogController extends AbstractController<StatusLog, StatusLogDao> {

	@Inject
	private StatusLogDao statusLogDao;

	@Override
	protected StatusLogDao getService() {
		return statusLogDao;
	}

}
