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
public class CsvExporter {
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
		exportInitialTagsHeader(contentBuilder);
		exportGoals(project.getGoal(), contentBuilder);
		exportLevels(project.getLevels(), contentBuilder);
		return contentBuilder.toString();
	}

	private void exportNameHeader(StringBuilder contentBuilder) {
		contentBuilder.append("ID");
		endValue(contentBuilder);
	}

	private void exportInitialTagsHeader(StringBuilder contentBuilder) {
		contentBuilder.append("InitialTags");
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

	private void exportLevels(List<Level> levels, StringBuilder contentBuilder) {
		for (Level level : levels) {
			exportLevel(level, contentBuilder);
		}
	}

	private void exportLevel(Level level, StringBuilder contentBuilder) {
		for (Candidate candidate : level.getCandidates()) {
			exportCandidate(candidate, contentBuilder);
		}
	}

	private void exportCandidate(Candidate candidate, StringBuilder contentBuilder) {
		exportCandidateId(candidate, contentBuilder);
		exportCandidateTags(candidate, contentBuilder);
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

	private void exportCandidateTags(Candidate candidate, StringBuilder contentBuilder) {
		String selectors = getSelectorTags(String.valueOf(candidate.getCandidateId()));
		String tags = getCandidateTag(candidate);
		contentBuilder.append(selectors).append(" ").append(tags);
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

	private String getCandidateTag(Candidate candidate) {
		String tags = "";
		tags = tags + " " + projectInfo.getTagInfo().getParetoTag(candidate);
		tags = tags + " " + projectInfo.getTagInfo().getSuggestionTag(candidate);
		return tags;
	}

	private void exportCandidateValue(Candidate candidate, int i, StringBuilder contentBuilder) {
		contentBuilder.append(candidate.getUtilityValues().get(i));
		endValue(contentBuilder);
	}

	private void endLine(StringBuilder contentBuilder) {
		removeLastCharacterfComma(contentBuilder);
		contentBuilder.append("\\n");
	}

	private void removeLastCharacterfComma(StringBuilder contentBuilder) {
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
