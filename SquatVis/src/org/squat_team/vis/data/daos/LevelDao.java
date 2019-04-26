package org.squat_team.vis.data.daos;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.squat_team.vis.data.data.Level;

@Stateless
public class LevelDao extends AbstractDao<Level> {

	@PersistenceContext
	private EntityManager em;

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
