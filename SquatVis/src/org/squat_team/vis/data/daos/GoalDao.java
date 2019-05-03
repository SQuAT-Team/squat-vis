package org.squat_team.vis.data.daos;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.squat_team.vis.data.data.Goal;

/**
 * An {@link IDao} for {@link Goal}s.
 */
@Stateless
public class GoalDao extends AbstractDao<Goal> {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Creates a new dao.
	 */
	public GoalDao() {
		super(Goal.class);
	}

	@Override
	public Goal newObject() {
		return new Goal();
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

}
