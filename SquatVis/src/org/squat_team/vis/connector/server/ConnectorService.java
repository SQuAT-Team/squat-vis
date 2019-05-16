package org.squat_team.vis.connector.server;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.omnifaces.cdi.Startup;
import org.squat_team.vis.data.daos.CandidateDao;
import org.squat_team.vis.data.daos.GoalDao;
import org.squat_team.vis.data.daos.LevelDao;
import org.squat_team.vis.data.daos.ProjectDao;
import org.squat_team.vis.data.daos.StatusLogDao;
import org.squat_team.vis.data.daos.ToolConfigurationDao;
import org.squat_team.vis.util.ResponseManager;

import lombok.Data;

/**
 * This service starts the {@link ConnectorServer} and holds the database access
 * services for the data import.
 */
@Data
@Startup
public class ConnectorService {

	@Inject
	private ProjectDao projectDao;
	@Inject
	private GoalDao goalDao;
	@Inject
	private LevelDao levelDao;
	@Inject
	private CandidateDao candidateDao;
	@Inject
	private ToolConfigurationDao toolConfigurationDao;
	@Inject
	private StatusLogDao statusLogDao;
	@Inject
	private ResponseManager responseManager;

	@PostConstruct
	public void startConnectorServer() {
		ConnectorServer server = ConnectorServer.getInstance();
		server.setConnectorService(this);
		server.start();
	}

}
