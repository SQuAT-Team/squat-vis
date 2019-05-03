package org.squat_team.vis.data.daos;

import java.util.List;

import javax.persistence.EntityManager;

/**
 * Default implementation of {@link IDao}. Supports standard tasks, like
 * {@link #find(Long)}, {@link #save(Object)}, {@link #update(Object)}, and
 * {@link #delete(Object)}.
 *
 * @param <D> The data object type that should be accessed by this dao
 */
public abstract class AbstractDao<D> implements IDao<D> {
	private Class<D> clazz;

	/**
	 * Creates a new dao object.
	 * 
	 * @param clazz this should be the .class of the generic D. See {@link AbstractDao}. 
	 */
	public AbstractDao(Class<D> clazz) {
		this.clazz = clazz;
	}

	/**
	 * An entity manager actually manages the database access. It must be injected
	 * in a non-abstract class.
	 * 
	 * @return the entity manager
	 */
	protected abstract EntityManager getEntityManager();

	@Override
	public List<D> list() {
		return getEntityManager().createQuery("SELECT c FROM " + clazz.getName() + " c", clazz).getResultList();
	}

	@Override
	public D find(Long id) {
		return getEntityManager().find(clazz, id);
	}

	@Override
	public void save(D data) {
		getEntityManager().persist(data);
	}

	@Override
	public void update(D data) {
		getEntityManager().merge(data);
	}

	@Override
	public void delete(D data) {
		EntityManager em = getEntityManager();
		em.remove(em.contains(data) ? data : em.merge(data));
	}

}
