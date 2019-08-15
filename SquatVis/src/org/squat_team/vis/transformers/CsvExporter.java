package org.squat_team.vis.transformers;

import java.util.List;

import org.squat_team.vis.connector.style.CandidateSelectorCSSProvider;
import org.squat_team.vis.data.data.Candidate;
import org.squat_team.vis.data.data.Goal;
import org.squat_team.vis.data.data.Level;
import org.squat_team.vis.data.data.Project;
import org.squat_team.vis.session.ProjectInfo;

/**
 * Exports the current data as CSV. The csv data can then be parsed by d3.
 */
public class CsvExporter extends AbstractExporter {
	private ProjectInfo projectInfo;

	/**
	 * Exports all the candidates in the project.
	 * 
	 * @param project     the project
	 * @param projectInfo related to the project
	 * @return a CSV formatted String
	 */
	public String export(Project project, ProjectInfo projectInfo) {
		this.projectInfo = projectInfo;
		StringBuilder contentBuilder = new StringBuilder();
		exportNameHeader(contentBuilder);
		exportParentHeader(contentBuilder);
		exportTagsHeader(contentBuilder);
		exportLevelTypeHeader(contentBuilder);
		exportGoals(project.getGoal(), contentBuilder);
		exportLevels(findActiveLevels(project, projectInfo), contentBuilder, false);
		exportLevels(findActiveParentLevels(project, projectInfo), contentBuilder, true);
		return contentBuilder.toString();
	}

	private void exportNameHeader(StringBuilder contentBuilder) {
		contentBuilder.append("ID");
		endValue(contentBuilder);
	}

	private void exportParentHeader(StringBuilder contentBuilder){
		contentBuilder.append("Parent");
		endValue(contentBuilder);
	}
	
	private void exportTagsHeader(StringBuilder contentBuilder) {
		contentBuilder.append("SelectorTags");
		endValue(contentBuilder);
		contentBuilder.append("ParetoTags");
		endValue(contentBuilder);
		contentBuilder.append("SuggestionTags");
		endValue(contentBuilder);
		contentBuilder.append("InitialTags");
		endValue(contentBuilder);
	}
	
	private void exportLevelTypeHeader(StringBuilder contentBuilder){
		contentBuilder.append("LevelType");
		endValue(contentBuilder);
	}

	private void exportGoals(Goal rootGoal, StringBuilder contentBuilder) {
		exportGoal(rootGoal, contentBuilder);
		endLine(contentBuilder);
	}

	private void exportGoal(Goal goal, StringBuilder contentBuilder) {
		if (goal.getChildren().isEmpty()) {
			contentBuilder.append(goal.getName());
			endValue(contentBuilder);
		} else {
			for (Goal childGoal : goal.getChildren()) {
				exportGoal(childGoal, contentBuilder);
			}
		}
	}

	private void exportLevels(List<Level> levels, StringBuilder contentBuilder, boolean isParent) {
		for (Level currentLevel: levels) {
			exportLevel(currentLevel, contentBuilder, isParent);
		}
	}
	
	private void exportLevel(Level level, StringBuilder contentBuilder, boolean isParent) {
		for (Candidate candidate : level.getCandidates()) {
			exportCandidate(candidate, contentBuilder, isParent);
		}
	}

	private void exportCandidate(Candidate candidate, StringBuilder contentBuilder, boolean isParent) {
		exportCandidateId(candidate, contentBuilder);
		exportCandidateParent(candidate, contentBuilder);
		exportCandidateTags(candidate, contentBuilder);
		exportLevelType(isParent, contentBuilder);
		int numberOfValues = Math.max(candidate.getRealValues().size(), candidate.getUtilityValues().size());
		for (int i = 0; i < numberOfValues; i++) {
			exportCandidateValue(candidate, i, contentBuilder);
		}
		endLine(contentBuilder);
	}

	private void exportCandidateId(Candidate candidate, StringBuilder contentBuilder) {
		contentBuilder.append(candidate.getCandidateId());
		endValue(contentBuilder);
	}

	private void exportCandidateParent(Candidate candidate, StringBuilder contentBuilder) {
		String parentId = "";
		if(candidate.getParent() != null) {
			parentId = candidate.getParent().getCandidateId().toString();
		}
		contentBuilder.append(parentId);
		endValue(contentBuilder);
	}
	
	private void exportCandidateTags(Candidate candidate, StringBuilder contentBuilder) {
		String selectors = getSelectorTags(String.valueOf(candidate.getCandidateId()));
		contentBuilder.append(selectors);
		endValue(contentBuilder);
		contentBuilder.append(getCandidateParetoTag(candidate));
		endValue(contentBuilder);
		contentBuilder.append(getCandidateSuggestionTag(candidate));
		endValue(contentBuilder);
		contentBuilder.append(getCandidateInitialTag(candidate));
		endValue(contentBuilder);
	}
	
	private void exportLevelType(boolean isParent, StringBuilder contentBuilder) {
		String levelType;
		if(isParent) {
			levelType = "parent";
		}else {
			levelType = "normal";
		}
		contentBuilder.append(levelType);
		endValue(contentBuilder);
	}

	private String getSelectorTags(String id) {
		String selectors = "";
		CandidateSelectorCSSProvider candidateSelectorMapper = new CandidateSelectorCSSProvider();
		if (projectInfo.getSelectorInfo().isCurrent(id)) {
			selectors = selectors + " " + candidateSelectorMapper.getCurrentStyle();
		}
		if (projectInfo.getSelectorInfo().isComparison(id)) {
			selectors = selectors + " " + candidateSelectorMapper.getComparisonStyle();
		}
		if (projectInfo.getSelectorInfo().isMarked(id)) {
			selectors = selectors + " " + candidateSelectorMapper.getMarkedStyle();
		}
		if (projectInfo.getSelectorInfo().isSelected(id)) {
			selectors = selectors + " " + candidateSelectorMapper.getSelectedStyle();
		}
		return selectors;
	}

	private String getCandidateParetoTag(Candidate candidate) {
		return projectInfo.getTagInfo().getParetoTag(candidate);
	}

	private String getCandidateSuggestionTag(Candidate candidate) {
		return projectInfo.getTagInfo().getSuggestionTag(candidate);
	}
	
	private String getCandidateInitialTag(Candidate candidate) {
		return projectInfo.getTagInfo().getInitialTag(candidate);
	}

	private void exportCandidateValue(Candidate candidate, int i, StringBuilder contentBuilder) {
		contentBuilder.append(candidate.getUtilityValues().get(i));
		endValue(contentBuilder);
	}

	private void endLine(StringBuilder contentBuilder) {
		removeLastCharacterComma(contentBuilder);
		contentBuilder.append("\\n");
	}

	private void removeLastCharacterComma(StringBuilder contentBuilder) {
		int numberOfCharacters = contentBuilder.length();
		String lastCharacter = contentBuilder.substring(numberOfCharacters - 1);
		if (lastCharacter.equals(",")) {
			contentBuilder.deleteCharAt(numberOfCharacters - 1);
		}
	}

	private void endValue(StringBuilder contentBuilder) {
		contentBuilder.append(",");
	}

}
