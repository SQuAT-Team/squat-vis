package org.squat_team.vis.transformers;

import java.util.List;

import org.squat_team.vis.data.data.Candidate;
import org.squat_team.vis.data.data.Goal;
import org.squat_team.vis.data.data.Level;
import org.squat_team.vis.data.data.Project;

/**
 * TODO
 */
public class CsvExporter {

	public String export(Project project) {
		StringBuilder contentBuilder = new StringBuilder();
		exportNameHeader(contentBuilder);
		exportGoals(project.getGoal(), contentBuilder);
		exportLevels(project.getLevels(), contentBuilder);
		return contentBuilder.toString();
	}

	private void exportNameHeader(StringBuilder contentBuilder) {
		contentBuilder.append("ID");
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
