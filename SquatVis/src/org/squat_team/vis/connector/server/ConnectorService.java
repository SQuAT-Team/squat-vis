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

/**
 * This service starts the {@link ConnectorServer} and holds the database access
 * services for the data import.
 */
@Data
@Named
@Eager
@ApplicationScoped
public class ConnectorService {

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
	public void startConnectorServer() {
		ConnectorServer server = ConnectorServer.getInstance();
		server.setConnectorService(this);
		server.start();
	}

}
