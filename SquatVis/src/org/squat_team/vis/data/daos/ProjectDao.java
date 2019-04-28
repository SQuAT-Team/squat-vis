package org.squat_team.vis.data.daos;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.squat_team.vis.data.data.Project;

@Stateless
public class ProjectDao extends AbstractDao<Project> {

	@PersistenceContext
	private EntityManager em;

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
