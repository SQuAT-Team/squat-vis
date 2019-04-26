package org.squat_team.vis.data.daos;

import java.util.List;

// TODO: Javadoc
public interface IDao<Data> {

	public Data newObject();

	public List<Data> list();

	public Data find(Long id);
	
	public void save(Data entity);

	public void update(Data entity);

	public void delete(Data entity);
}
