package org.squat_team.vis.data.daos;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.squat_team.vis.data.data.ToolConfiguration;

@Stateless
public class ToolConfigurationDao extends AbstractDao<ToolConfiguration> {

	@PersistenceContext
	private EntityManager em;

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
