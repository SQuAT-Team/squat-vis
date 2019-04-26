package org.squat_team.vis.data.daos;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.squat_team.vis.data.data.Candidate;

@Stateless
public class CandidateDao extends AbstractDao<Candidate> {
	@PersistenceContext
	private EntityManager em;

	public CandidateDao() {
		super(Candidate.class);
	}

	public Candidate find(long projectId, long candidateId) {
		TypedQuery<Candidate> query = getEntityManager().createQuery(
				"SELECT c FROM Candidate  c WHERE c.projectId = :projectId AND c.candidateId = :candidateId ",
				Candidate.class);
		return query.setParameter("projectId", projectId).setParameter("candidateId", candidateId).getSingleResult();
	}

	@Override
	public Candidate newObject() {
		return new Candidate();
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

}
