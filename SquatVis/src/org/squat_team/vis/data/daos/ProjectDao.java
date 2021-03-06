package org.squat_team.vis.data.daos;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.squat_team.vis.data.data.Project;

/**
 * An {@link IDao} for {@link Project}s.
 */
@Stateless
public class ProjectDao extends AbstractDao<Project> implements Serializable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = 6847276165850332599L;
	
	@PersistenceContext
	private transient EntityManager em;

	/**
	 * Creates a new dao.
	 */
	public ProjectDao() {
		super(Project.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	public Project newObject() {
		return new Project();
	}

}
