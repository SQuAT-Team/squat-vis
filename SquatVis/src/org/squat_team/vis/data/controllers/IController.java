package org.squat_team.vis.data.controllers;

import java.util.List;

// TODO: JavaDoc

public interface IController<Data, Dao> {
	public void init();

	public void add();

	public Data getDatum();

	public List<Data> getData();
}
