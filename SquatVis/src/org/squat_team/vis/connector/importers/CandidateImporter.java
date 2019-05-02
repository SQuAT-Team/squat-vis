package org.squat_team.vis.connector.importers;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.data.CCandidate;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.server.ConnectorService;
import org.squat_team.vis.data.daos.CandidateDao;
import org.squat_team.vis.data.data.Candidate;

/**
 * Imports {@link CCandidate}s and returns {@link Candidate} objects, which are
 * then stored in the database.
 */
public class CandidateImporter extends AbstractImporter<CCandidate, Candidate> {
	private CandidateDao candidateDao;

	/**
	 * Creates a new importer.
	 * 
	 * @param connectorService Provides daos for the import
	 * @param projectConnector Specifies the project the import belongs to
	 */
	public CandidateImporter(ConnectorService connectorService, ProjectConnector projectConnector) {
		super(connectorService, projectConnector);
	}

	@Override
	public Candidate transform(CCandidate ccandidate) throws InvalidRequestException {
		findDao();
		Candidate candidate = transformCandidate(ccandidate);
		store(candidate);
		return candidate;
	}

	/**
	 * Applies the transformation on the object level.
	 * 
	 * @param ccandidate the candidate to transform.
	 * @return the transformed candidate.
	 * @throws InvalidRequestException if the specified project is not found
	 */
	private Candidate transformCandidate(CCandidate ccandidate) throws InvalidRequestException {
		Candidate candidate = new Candidate();
		candidate.setCandidateId(ccandidate.getCandidateId());
		candidate.setParent(findParent(ccandidate));
		candidate.setProjectId(projectConnector.getProjectId());
		candidate.setRealValues(ccandidate.getRealValues());
		candidate.setUtilityValues(ccandidate.getUtilityValues());
		candidate.setRealValuePareto(ccandidate.isRealValuePareto());
		candidate.setUtilityValuePareto(ccandidate.isUtilityValuePareto());
		return candidate;
	}

	/**
	 * Finds the parent of the candidate.
	 * 
	 * @param ccandidate the child object.
	 * @return the parent object or null if there is none.
	 * @throws InvalidRequestException if the specified project is not found
	 */
	private Candidate findParent(CCandidate ccandidate) throws InvalidRequestException {
		if (ccandidate.getParentId() == null) {
			return null;
		}
		long candidateId = ccandidate.getParentId();
		return candidateDao.find(findProject().getId(), candidateId);
	}

	/**
	 * Stores the candidate in the database.
	 * 
	 * @param candidate candidate to store.
	 */
	private void store(Candidate candidate) {
		candidateDao.save(candidate);
	}

	/**
	 * Sets the dao in this class.
	 */
	private void findDao() {
		this.candidateDao = connectorService.getCandidateDao();
	}
}
