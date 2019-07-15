package org.squat_team.vis.data.daos;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.squat_team.vis.data.data.ArchitectureAnalysisData;

@Stateless
public class ArchitectureAnalysisDataDao extends AbstractDao<ArchitectureAnalysisData> implements Serializable  {

	/**
	 * Generated 
	 */
	private static final long serialVersionUID = 4030741938518927189L;
	
	@PersistenceContext
	private transient EntityManager em;

	/**
	 * Creates a new dao.
	 */
	public ArchitectureAnalysisDataDao() {
		super(ArchitectureAnalysisData.class);
	}

	@Override
	public ArchitectureAnalysisData newObject() {
		return new ArchitectureAnalysisData();
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
}
