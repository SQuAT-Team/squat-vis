package org.squat_team.vis.data.daos;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.squat_team.vis.data.data.Architecture;

/**
 * An {@link IDao} for {@link Architecture}s.
 */
@Stateless
public class ArchitectureDao extends AbstractDao<Architecture> {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Creates a new dao.
	 */
	public ArchitectureDao() {
		super(Architecture.class);
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
