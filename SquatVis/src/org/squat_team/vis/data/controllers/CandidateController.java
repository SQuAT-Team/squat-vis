package org.squat_team.vis.data.controllers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.squat_team.vis.data.daos.CandidateDao;
import org.squat_team.vis.data.data.Candidate;

@Named
@RequestScoped
public class CandidateController extends AbstractController<Candidate, CandidateDao> {
	@Inject
	private CandidateDao service;

	public CandidateDao getService() {
		return service;
	}
	
	@Override
	public void add() {
		getService().save(datum);
		init();
	}
}
