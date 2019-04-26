package org.squat_team.vis.data.daos;

import java.util.List;

import javax.persistence.EntityManager;

public abstract class AbstractDao<Data> implements IDao<Data> {
	private Class<Data> clazz;

	public AbstractDao(Class<Data> clazz) {
		this.clazz = clazz;
	}

	protected abstract EntityManager getEntityManager();

	public List<Data> list() {
		return getEntityManager().createQuery("SELECT c FROM " + clazz.getName() + " c", clazz).getResultList();
	}

	@Override
	public Data find(Long id) {
		return getEntityManager().find(clazz, id);
	}

	public void save(Data data) {
		getEntityManager().persist(data);
	}

	public void update(Data data) {
		getEntityManager().merge(data);
	}

	public void delete(Data data) {
		EntityManager em = getEntityManager();
		em.remove(em.contains(data) ? data : em.merge(data));
	}

}
