package org.squat_team.vis.data.daos;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ToolConnectionDao {
	@PersistenceContext
	private EntityManager em;
	
}
