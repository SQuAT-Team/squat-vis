package org.squat_team.vis.data.controllers;

import java.util.List;

/**
 * Can be used to access database objects from the frontend.
 *
 * @param <D> The data object type that should be controlled
 */
public interface IController<D> {
	public void init();

	public void add();

	public D getDatum();

	public List<D> getData();
}
