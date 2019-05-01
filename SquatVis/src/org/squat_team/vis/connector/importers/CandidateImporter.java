package org.squat_team.vis.connector.importers;

import org.squat_team.vis.connector.Connection;
import org.squat_team.vis.connector.data.CCandidate;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.server.ConnectorService;
import org.squat_team.vis.data.daos.CandidateDao;
import org.squat_team.vis.data.data.Candidate;

public class CandidateImporter extends AbstractImporter<CCandidate, Candidate> {
	private CandidateDao dao;

	public CandidateImporter(ConnectorService connectorService, Connection connection) {
		super(connectorService, connection);
	}

	@Override
	public Candidate transform(CCandidate ccandidate) throws InvalidRequestException {
		findDao();
		Candidate candidate = transformCandidate(ccandidate);
		store(candidate);
		return candidate;
	}

	private Candidate transformCandidate(CCandidate ccandidate) {
		Candidate candidate = new Candidate();
		long candidateId = ccandidate.getCandidateId();
		candidate.setCandidateId(candidateId);

		candidate.setParent(findParent(ccandidate));
		candidate.setProjectId(connection.getProjectId());
		candidate.setRealValues(ccandidate.getRealValues());
		candidate.setUtilityValues(ccandidate.getUtilityValues());
		candidate.setRealValuePareto(ccandidate.isRealValuePareto());
		candidate.setUtilityValuePareto(ccandidate.isUtilityValuePareto());
		return candidate;
	}

	private Candidate findParent(CCandidate ccandidate) {
		if (ccandidate.getParentId() == null) {
			return null;
		}
		long candidateId = ccandidate.getParentId();
		return dao.find(connection.getProjectId(), candidateId);
	}

	private void store(Candidate candidate) {
		dao.save(candidate);
	}

	private void findDao() {
		this.dao = connectorService.getCandidateDao();
	}
}
