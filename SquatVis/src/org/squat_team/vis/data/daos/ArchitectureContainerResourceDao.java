package org.squat_team.vis.data.daos;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.squat_team.vis.data.data.ArchitectureContainerResource;

@Stateless
public class ArchitectureContainerResourceDao extends AbstractDao<ArchitectureContainerResource> implements Serializable  {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = -911439301110743704L;
	
	@PersistenceContext
	private transient EntityManager em;

	/**
	 * Creates a new dao.
	 */
	public ArchitectureContainerResourceDao() {
		super(ArchitectureContainerResource.class);
	}

	@Override
	public ArchitectureContainerResource newObject() {
		return new ArchitectureContainerResource();
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
}
