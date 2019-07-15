package org.squat_team.vis.data.controllers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.squat_team.vis.data.daos.ArchitectureAnalysisDataDao;
import org.squat_team.vis.data.data.ArchitectureAnalysisData;

@Named
@RequestScoped
public class ArchitectureAnalysisDataController  extends AbstractController<ArchitectureAnalysisData, ArchitectureAnalysisDataDao> {
	@Inject
	private ArchitectureAnalysisDataDao service;

	@Override
	public ArchitectureAnalysisDataDao getService() {
		return service;
	}
}
