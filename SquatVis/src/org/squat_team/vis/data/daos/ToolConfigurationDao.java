package org.squat_team.vis.data.daos;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.squat_team.vis.data.data.ToolConfiguration;

/**
 * An {@link IDao} for {@link ToolConfiguration}s.
 */
@Stateless
public class ToolConfigurationDao extends AbstractDao<ToolConfiguration> {

	@PersistenceContext
	private EntityManager em;

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
