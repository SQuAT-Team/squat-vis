package org.squat_team.vis.data.daos;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
