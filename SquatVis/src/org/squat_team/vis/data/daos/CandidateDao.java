package org.squat_team.vis.data.daos;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.squat_team.vis.data.data.Candidate;

/**
 * An {@link IDao} for {@link Candidate}s.
 */
@Stateless
public class CandidateDao extends AbstractDao<Candidate> implements Serializable{
	
	/**
	 * Generated
	 */
	private static final long serialVersionUID = 7038388113264871488L;
	
	@PersistenceContext
	private transient EntityManager em;

	/**
	 * Creates a new dao.
	 */
	public CandidateDao() {
		super(Candidate.class);
	}

	/**
	 * Finds a candidate within a project.
	 * 
	 * @param projectId the id of the project
	 * @param candidateId the id of the candidate (project specific)
	 * @return the candidate or null if not found
	 */
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
