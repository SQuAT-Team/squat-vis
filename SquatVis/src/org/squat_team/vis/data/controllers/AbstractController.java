package org.squat_team.vis.data.controllers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;

import org.squat_team.vis.data.daos.IDao;

/**
 * Default implementation of the {@link IController} for dao-based database
 * access. Already implements most standard tasks like getting data from the
 * database.
 * 
 * @param <D> The data object type that should be controlled
 * @param <O> The corresponding dao type of the data object type
 */
@Dependent
public abstract class AbstractController<D, O extends IDao<D>> implements IController<D> {
	protected D datum;
	protected List<D> data;

	/**
	 * Returns the Dao.
	 * 
	 * @return The Dao of the class that should be controlled.
	 */
	protected abstract O getService();

	@PostConstruct
	@Override
	public void init() {
		datum = getService().newObject();
		data = getService().list();
	}

	@Override
	public void add() {
		getService().save(datum);
		init();
	}

	@Override
	public D getDatum() {
		return datum;

	}

	@Override
	public List<D> getData() {
		return data;
	}

}
