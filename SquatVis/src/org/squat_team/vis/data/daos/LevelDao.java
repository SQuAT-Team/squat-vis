package org.squat_team.vis.data.daos;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.squat_team.vis.data.data.Level;

/**
 * An {@link IDao} for {@link Level}s.
 */
@Stateless
public class LevelDao extends AbstractDao<Level> implements Serializable{

	/**
	 * Generated
	 */
	private static final long serialVersionUID = 4158852310631289193L;
	
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
