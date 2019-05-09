package org.squat_team.vis.connector.importers;

import java.util.ArrayList;
import java.util.List;

import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.data.CCandidate;
import org.squat_team.vis.connector.data.CLevel;
import org.squat_team.vis.connector.exceptions.InvalidRequestException;
import org.squat_team.vis.connector.server.ConnectorService;
import org.squat_team.vis.data.daos.LevelDao;
import org.squat_team.vis.data.data.Candidate;
import org.squat_team.vis.data.data.Level;
import org.squat_team.vis.data.data.Project;

/**
 * Imports {@link CLevel}s and returns {@link Level} objects, which are then
 * stored in the database.
 */
public class LevelImporter extends AbstractImporter<CLevel, Level> {
	private LevelDao levelDao;

	/**
	 * Creates a new importer.
	 * 
	 * @param connectorService Provides daos for the import
	 * @param projectConnector Specifies the project the import belongs to
	 */
	public LevelImporter(ConnectorService connectorService, ProjectConnector projectConnector) {
		super(connectorService, projectConnector);
	}

	@Override
	public Level transform(CLevel clevel) throws InvalidRequestException {
		findDao();
		Project project = findProject();
		Level level = transformLevel(clevel, project);
		store(level);
		List<Level> levels = findLevels(project);
		levels.add(level);
		update(project);
		return level;
	}

	/**
	 * Sets the required daos in this class.
	 */
	private void findDao() {
		levelDao = connectorService.getLevelDao();
	}

	/**
	 * Find the list of levels in the project.
	 * 
	 * @param project the project to search in.
	 * @return all levels that are known to the project.
	 */
	private List<Level> findLevels(Project project) {
		List<Level> levels = project.getLevels();
		if (levels == null) {
			levels = new ArrayList<>();
			project.setLevels(levels);
		}
		return levels;
	}

	/**
	 * Applies the transformation on the object level and also imports contained
	 * candidates.
	 * 
	 * @param clevel  the level to transform.
	 * @param project the project that contains the level.
	 * @return the transformed level.
	 * @throws InvalidRequestException if the specified project is not found
	 */
	private Level transformLevel(CLevel clevel, Project project) throws InvalidRequestException {
		Level level = new Level();
		level.setProject(project);
		CandidateImporter candidateImporter = new CandidateImporter(connectorService, projectConnector);
		for (CCandidate ccandidate : clevel.getCandidates()) {
			Candidate candidate = candidateImporter.transform(ccandidate);
			level.getCandidates().add(candidate);
		}
		return level;
	}

	/**
	 * Stores the level in the database.
	 * 
	 * @param level the level to store.
	 */
	private void store(Level level) {
		levelDao.save(level);
	}

}
