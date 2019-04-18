package org.squat_team.vis.data.controllers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;

import org.squat_team.vis.data.daos.IDao;

// TODO: JavaDoc

@Dependent
public abstract class AbstractController<Data, Dao extends IDao<Long, Data>> implements IController<Data, Dao>{
	protected Data datum;
	protected List<Data> data;
	
	protected abstract Dao getService();
	
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
	public Data getDatum() {
		return datum;

	}

	@Override
	public List<Data> getData() {
		return data;
	}
	
}
