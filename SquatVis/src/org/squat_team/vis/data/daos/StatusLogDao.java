package org.squat_team.vis.data.daos;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.squat_team.vis.data.data.StatusLog;

/**
 * An {@link IDao} for {@link StatusLog}s.
 */
@Stateless
public class StatusLogDao extends AbstractDao<StatusLog> implements Serializable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = -2067478205534423715L;

	@PersistenceContext
	private EntityManager em;

	/**
	 * Creates a new dao.
	 */
	public StatusLogDao() {
		super(StatusLog.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	public StatusLog newObject() {
		return new StatusLog();
	}
	
}
