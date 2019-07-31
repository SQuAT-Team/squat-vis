package org.squat_team.vis.connector.protocols;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.squat_team.vis.analysis.PCMArchitectureAnalyzer;
import org.squat_team.vis.analysis.ParetoAnalyzer;
import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.data.CLevel;
import org.squat_team.vis.connector.server.ConnectorService;
import org.squat_team.vis.data.daos.CandidateDao;
import org.squat_team.vis.data.data.Candidate;
import org.squat_team.vis.data.data.Level;
import org.squat_team.vis.data.data.Project;

/**
 * This handler should be called after the import of a new level of search.
 */
public class NewLevelPostProtocolHandler extends AbstractStatusUpdatingPostProtocolHandler {
	private CLevel level;
	private boolean noResponse;

	/**
	 * Creates a new handler.
	 * 
	 * @param connectorService Provides daos for the import
	 * @param projectConnector Specifies the project the import belongs to
	 * @param level            the received level
	 * @param noResponse       True if the client does not expect a response.
	 */
	public NewLevelPostProtocolHandler(ConnectorService connectorService, ProjectConnector projectConnector, CLevel level,
			boolean noResponse) {
		super(connectorService, projectConnector);
		this.level = level;
		this.noResponse = noResponse;
	}

	@Override
	public void handle() {
		updateStatusFinishLevel();
		startParetoAnalysis();
		startArchitectureAnalysis();
		updateStatusFinishLevelImport();
		if (noResponse) {
			updateStatusStartLevel();
		}
	}

	/**
	 * Starts the analysis of all pareto candidates.
	 */
	private void startParetoAnalysis() {
		if (project.getConfiguration().getHasUtilities()) {
			analyzeForParetoCandidates(true);
		}
		if (project.getConfiguration().getHasRealValues()) {
			analyzeForParetoCandidates(false);
		}
	}
	
	private void startArchitectureAnalysis() {
		if(project.getConfiguration().getHasArchitectures()) {
			PCMArchitectureAnalyzer analyzer = new PCMArchitectureAnalyzer(level, projectConnector, connectorService);
			analyzer.analyze();
		}
	}

	private void analyzeForParetoCandidates(boolean useUtility) {
		ParetoAnalyzer analyzer = new ParetoAnalyzer(useUtility);
		analyzeForLevelParetoCandidates(analyzer, useUtility);
		analyzeForPopulationParetoCandidates(analyzer, useUtility);
	}

	private void analyzeForLevelParetoCandidates(ParetoAnalyzer analyzer, boolean useUtility) {
		List<Level> levels = project.getLevels();
		Level lastLevel = levels.get(levels.size() - 1);
		Collection<Candidate> levelParetoCandidates = analyzer.findParetoCandidates(lastLevel.getCandidates());
		markAsParetoCandidates(levelParetoCandidates, useUtility, false);
		updateCandidates(levelParetoCandidates);
	}

	private void analyzeForPopulationParetoCandidates(ParetoAnalyzer analyzer, boolean useUtility) {
		List<Candidate> allProjectParetoCandidates = getProjectParetoCandidates(project, useUtility);
		Collection<Candidate> populationParetoCandidates = analyzer.findParetoCandidates(allProjectParetoCandidates);
		markAsParetoCandidates(populationParetoCandidates, useUtility, true);
		updateCandidates(populationParetoCandidates);
	}

	private List<Candidate> getProjectParetoCandidates(Project project, boolean useUtility) {
		List<Candidate> paretoCandidates = new ArrayList<>();
		for (Level level : project.getLevels()) {
			paretoCandidates.addAll(getLevelParetoCandidates(level, useUtility));
		}
		return paretoCandidates;
	}

	private List<Candidate> getLevelParetoCandidates(Level level, boolean useUtility) {
		List<Candidate> paretoCandidates = new ArrayList<>();
		for (Candidate candidate : level.getCandidates()) {
			if (checkIsLevelPareto(candidate, useUtility)) {
				paretoCandidates.add(candidate);
			}
		}
		return paretoCandidates;
	}

	private boolean checkIsLevelPareto(Candidate candidate, boolean useUtility) {
		if (useUtility) {
			return candidate.isUtilityValueParetoLevelBased();
		} else {
			return candidate.isRealValueParetoLevelBased();
		}
	}

	private void markAsParetoCandidates(Collection<Candidate> paretoCandidates, boolean useUtility,
			boolean populationPareto) {
		for (Candidate paretoCandidate : paretoCandidates) {
			markAsParetoCandidate(paretoCandidate, useUtility, populationPareto);
		}
	}

	private void markAsParetoCandidate(Candidate paretoCandidate, boolean useUtility, boolean populationPareto) {
		if (populationPareto) {
			if (useUtility) {
				paretoCandidate.setUtilityValueParetoPopulationBased(true);
			} else {
				paretoCandidate.setRealValueParetoPopulationBased(true);
			}
		} else {
			if (useUtility) {
				paretoCandidate.setUtilityValueParetoLevelBased(true);
			} else {
				paretoCandidate.setRealValueParetoLevelBased(true);
			}
		}
	}

	private void updateCandidates(Collection<Candidate> candidates) {
		CandidateDao candidateDao = connectorService.getCandidateDao();
		for (Candidate candidate : candidates) {
			candidateDao.update(candidate);
		}
	}

}
