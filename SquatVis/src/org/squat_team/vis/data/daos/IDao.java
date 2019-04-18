package org.squat_team.vis.data.daos;

import java.util.List;

// TODO: Javadoc
public abstract class IDao<K, E> {

	public abstract E newObject();

	public abstract List<E> list();

	public abstract void save(E entity);

	public abstract void update(E entity);

	public abstract void delete(E entity);
}
