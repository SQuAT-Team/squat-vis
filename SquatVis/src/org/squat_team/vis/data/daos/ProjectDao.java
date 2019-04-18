package org.squat_team.vis.data.daos;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.squat_team.vis.data.data.Project;

@Stateless
public class ProjectDao extends IDao<Long, Project> {

	// Injected database connection:
	@PersistenceContext
	private EntityManager em;

	public List<Project> list() {
		return em.createQuery("SELECT p FROM Project p", Project.class).getResultList();
	}

	public Project find(Integer id) {
		return em.find(Project.class, id);
	}

	public void save(Project project) {
		em.persist(project);
	}

	public void update(Project project) {
		em.merge(project);
	}

	public void delete(Project project) {
		em.remove(em.contains(project) ? project : em.merge(project));
	}

	@Override
	public Project newObject() {
		return new Project();
	}

}
