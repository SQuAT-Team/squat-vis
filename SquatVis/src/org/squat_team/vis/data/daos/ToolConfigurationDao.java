package org.squat_team.vis.data.daos;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.squat_team.vis.data.data.ToolConfiguration;

/**
 * An {@link IDao} for {@link ToolConfiguration}s.
 */
@Stateless
public class ToolConfigurationDao extends AbstractDao<ToolConfiguration> implements Serializable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = 8576220919035495402L;
	
	@PersistenceContext
	private transient EntityManager em;

	/**
	 * Creates a new dao.
	 */
	public ToolConfigurationDao() {
		super(ToolConfiguration.class);
	}

	@Override
	public ToolConfiguration newObject() {
		return new ToolConfiguration();
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

}
