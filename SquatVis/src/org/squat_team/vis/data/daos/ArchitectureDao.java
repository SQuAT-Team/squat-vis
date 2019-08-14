package org.squat_team.vis.data.daos;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.squat_team.vis.data.data.Architecture;

/**
 * An {@link IDao} for {@link Architecture}s.
 */
@Stateless
public class ArchitectureDao extends AbstractDao<Architecture> implements Serializable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = 1302708640948499897L;

	@PersistenceContext
	private transient EntityManager em;

	/**
	 * Creates a new dao.
	 */
	public ArchitectureDao() {
		super(Architecture.class);
	}

	/**
	 * Finds an architecture within a project.
	 * 
	 * @param projectId the id of the project
	 * @param candidateId the id of the candidate (project specific)
	 * @return the candidate or null if not found
	 */
	public Architecture find(long projectId, long candidateId) {
		TypedQuery<Architecture> query = getEntityManager().createQuery(
				"SELECT c FROM Architecture c WHERE c.projectId = :projectId AND c.candidateId = :candidateId ",
				Architecture.class);
		try {
			return query.setParameter("projectId", projectId).setParameter("candidateId", candidateId).getSingleResult();
		}catch(Exception e) {
			return null;
		}
	}
	
	@Override
	public Architecture newObject() {
		return new Architecture();
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
}
