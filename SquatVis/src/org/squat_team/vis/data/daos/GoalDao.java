package org.squat_team.vis.data.daos;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.squat_team.vis.data.data.Goal;

/**
 * An {@link IDao} for {@link Goal}s.
 */
@Stateless
public class GoalDao extends AbstractDao<Goal> implements Serializable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = 4213492899795504685L;

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
