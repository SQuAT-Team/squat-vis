package org.squat_team.vis.data.daos;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.squat_team.vis.data.data.Level;

/**
 * An {@link IDao} for {@link Level}s.
 */
@Stateless
public class LevelDao extends AbstractDao<Level> {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Creates a new dao.
	 */
	public LevelDao() {
		super(Level.class);
	}

	@Override
	public Level newObject() {
		return new Level();
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

}
