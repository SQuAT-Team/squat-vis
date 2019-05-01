package org.squat_team.vis.connector.importers;

import java.util.ArrayList;
import java.util.List;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.data.CCandidate;
import org.squat_team.vis.connector.data.CLevel;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.server.ConnectorService;
import org.squat_team.vis.data.daos.LevelDao;
import org.squat_team.vis.data.daos.ProjectDao;
import org.squat_team.vis.data.data.Candidate;
import org.squat_team.vis.data.data.Level;
import org.squat_team.vis.data.data.Project;
import org.squat_team.vis.data.data.Status;

public class LevelImporter extends AbstractImporter<CLevel, Level> {
	private ProjectDao projectDao;
	private LevelDao levelDao;

	public LevelImporter(ConnectorService connectorService, ProjectConnector connection) {
		super(connectorService, connection);
	}

	@Override
	public Level transform(CLevel clevel) throws InvalidRequestException {
		findDaos();
		Project project = findProject();
		checkProject(project);
		List<Level> levels = findLevels(project);
		Level level = transformLevel(clevel, levels);
		store(level);
		update(project);
		return level;
	}

	private Project findProject() {
		return projectDao.find(connection.getProjectId());
	}

	private void findDaos() {
		projectDao = connectorService.getProjectDao();
		levelDao = connectorService.getLevelDao();
	}

	private void checkProject(Project project) throws InvalidRequestException {
		if (project == null) {
			throw new InvalidRequestException("Could not find requested project");
		}
	}

	private List<Level> findLevels(Project project) {
		List<Level> levels = project.getLevels();
		if (levels == null) {
			levels = new ArrayList<Level>();
			project.setLevels(levels);
		}
		return levels;
	}

	private Level transformLevel(CLevel clevel, List<Level> levels) throws InvalidRequestException {
		Level level = new Level();
		levels.add(level);

		CandidateImporter candidateImporter = new CandidateImporter(connectorService, connection);
		for (CCandidate ccandidate : clevel.getCandidates()) {
			Candidate candidate = candidateImporter.transform(ccandidate);
			level.getCandidates().add(candidate);
		}

		return level;
	}

	private void store(Level level) {
		levelDao.save(level);
	}

	private void update(Project project) {
		updateProjectStatus(project);
		projectDao.update(project);
	}
	
	private void updateProjectStatus(Project project) {
		Status projectStatus = project.getStatus();
		projectStatus.notifyLevelFinished();
	}
}
