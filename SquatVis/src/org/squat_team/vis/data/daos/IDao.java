package org.squat_team.vis.data.daos;

import java.util.List;

/**
 * Daos allow to persist and find objects in the database.
 * 
 * @param <D> The data object type that should be accessed by this dao
 */
public interface IDao<D> {

	/**
	 * Creates a new, empty instance.
	 * 
	 * @return the new instance
	 */
	public D newObject();

	/**
	 * Returns all stored objects of the specified type.
	 * 
	 * @return all objects with arbitrary ordering.
	 */
	public List<D> list();

	/**
	 * Finds the object with the specified id.
	 * 
	 * @param id the unique id of the object.
	 * @return the object or null if not found
	 */
	public D find(Long id);

	/**
	 * Stores the object in the database.
	 * 
	 * @param entity the object to store.
	 */
	public void save(D entity);

	/**
	 * Updates the database with the current state of the object. The object must
	 * already be stored (see {@link #save(Object)}).
	 * 
	 * @param entity the object to update.
	 */
	public void update(D entity);

	/**
	 * Deletes the object from the database.
	 * 
	 * @param entity the object to delete.
	 */
	public void delete(D entity);
}
