package org.squat_team.vis.data.controllers;

import java.util.List;

/**
 * Can be used to access database objects from the frontend.
 *
 * @param <D> The data object type that should be controlled
 */
public interface IController<D> {

	/**
	 * Prepares a new instance of the data object, that can be saved by calling
	 * {@link #add()}
	 */
	public void init();

	/**
	 * Adds the current data object to the database.
	 */
	public void add();

	/**
	 * Get the current data object. Call {@link #add()} to store it in the database
	 * 
	 * @return the data object
	 */
	public D getDatum();

	/**
	 * Lists all the data objects of the type that this controller is responsible
	 * for. Only objects that are stored in the database are returned.
	 * 
	 * @return all data objects of the specified type
	 */
	public List<D> getData();

	/**
	 * Finds the object with the specified id.
	 * 
	 * @param id the id
	 * @return the object of the specified type
	 */
	public D find(long id);

	/**
	 * Updates the entity in the database.
	 * 
	 * @param datum the data to update
	 */
	public void update(D datum);
}
