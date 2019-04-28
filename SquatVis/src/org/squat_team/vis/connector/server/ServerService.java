package org.squat_team.vis.connector.server;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.omnifaces.cdi.Eager;
import org.squat_team.vis.data.daos.CandidateDao;
import org.squat_team.vis.data.daos.GoalDao;
import org.squat_team.vis.data.daos.LevelDao;
import org.squat_team.vis.data.daos.ProjectDao;
import org.squat_team.vis.data.daos.ToolConfigurationDao;

import lombok.Data;

@Data
@Named
@Eager
@ApplicationScoped
public class ServerService {

	@EJB
	private ProjectDao projectDao;
	@EJB
	private GoalDao goalDao;
	@EJB
	private LevelDao levelDao;
	@EJB
	private CandidateDao candidateDao;
	@EJB
	private ToolConfigurationDao toolConfigurationDao;

	@PostConstruct
	public void init() {
		startConnectorServer();;
		setDatabaseMode();
	}
	
	private void startConnectorServer() {
		System.out.println("Starting Connector Service");
		MainServer server = MainServer.getInstance();
		server.start();
		server.setRunner(this);
	}
	
	private void setDatabaseMode() {
		System.setProperty("objectdb.temp.no-detach", "true");
	}
}
