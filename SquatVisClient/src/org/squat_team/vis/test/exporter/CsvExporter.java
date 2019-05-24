package org.squat_team.vis.test.exporter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.squat_team.vis.connector.data.CCandidate;
import org.squat_team.vis.connector.data.CGoal;
import org.squat_team.vis.connector.data.CLevel;
import org.squat_team.vis.connector.data.CProject;
import org.squat_team.vis.connector.data.CToolConfiguration;

/**
 * Exports the files as a csv file. It contains columns for the candidate's id,
 * and all its atomic goals. A goal can have two columns, if real and utility
 * values exist. Each candidate is then exported in a row.
 */
public class CsvExporter implements IExporter {
	private static final String FILE_EXTENSION = ".csv";
	private String exportDirectoryPath;

	public CsvExporter(String exportDirectoryPath) {
		this.exportDirectoryPath = exportDirectoryPath;
	}

	@Override
	public void export(CProject project, CGoal goal, List<CLevel> levels, CToolConfiguration toolConfiguration)
			throws IOException {
		String filePath = this.exportDirectoryPath + File.separator + project.getName() + FILE_EXTENSION;
		StringBuilder contentBuilder = new StringBuilder();
		exportNameHeader(contentBuilder);
		exportGoals(goal, contentBuilder, toolConfiguration);
		exportLevels(levels, contentBuilder, toolConfiguration);
		exportFile(contentBuilder.toString(), filePath);
	}

	private void exportNameHeader(StringBuilder contentBuilder) {
		contentBuilder.append("Candidate ID");
		endValue(contentBuilder);
	}

	private void exportGoals(CGoal rootGoal, StringBuilder contentBuilder, CToolConfiguration toolConfiguration) {
		exportGoal(rootGoal, contentBuilder, toolConfiguration);
		endLine(contentBuilder);
	}

	private void exportGoal(CGoal goal, StringBuilder contentBuilder, CToolConfiguration toolConfiguration) {
		if (goal.getChildren().isEmpty()) {
			if (toolConfiguration.getHasUtilities()) {
				contentBuilder.append(goal.getName());
				contentBuilder.append("_u");
				endValue(contentBuilder);
			}
			if (toolConfiguration.getHasRealValues()) {
				contentBuilder.append(goal.getName());
				contentBuilder.append("_r");
				endValue(contentBuilder);
			}
		} else {
			for (CGoal childGoal : goal.getChildren()) {
				exportGoal(childGoal, contentBuilder, toolConfiguration);
			}
		}
	}

	private void exportLevels(List<CLevel> levels, StringBuilder contentBuilder, CToolConfiguration toolConfiguration) {
		for (CLevel level : levels) {
			exportLevel(level, contentBuilder, toolConfiguration);
		}
	}

	private void exportLevel(CLevel level, StringBuilder contentBuilder, CToolConfiguration toolConfiguration) {
		for (CCandidate candidate : level.getCandidates()) {
			exportCandidate(candidate, contentBuilder, toolConfiguration);
		}
	}

	private void exportCandidate(CCandidate candidate, StringBuilder contentBuilder,
			CToolConfiguration toolConfiguration) {
		exportCandidateId(candidate, contentBuilder);
		int numberOfValues = Math.max(candidate.getRealValues().size(), candidate.getUtilityValues().size());
		for (int i = 0; i < numberOfValues; i++) {
			exportCandidateValue(candidate, i, contentBuilder, toolConfiguration);
		}
		endLine(contentBuilder);
	}

	private void exportCandidateId(CCandidate candidate, StringBuilder contentBuilder) {
		contentBuilder.append(candidate.getCandidateId());
		endValue(contentBuilder);
	}

	private void exportCandidateValue(CCandidate candidate, int i, StringBuilder contentBuilder,
			CToolConfiguration toolConfiguration) {
		if (toolConfiguration.getHasUtilities()) {
			contentBuilder.append(candidate.getUtilityValues().get(i));
			endValue(contentBuilder);
		}
		if (toolConfiguration.getHasRealValues()) {
			contentBuilder.append(candidate.getRealValues().get(i));
			endValue(contentBuilder);
		}
	}

	private void exportFile(String content, String filePath) throws IOException {
		File file = new File(filePath);
		file.getParentFile().mkdirs();
		Path path = Paths.get(filePath);
		byte[] strToBytes = content.getBytes();
		Files.write(path, strToBytes);
	}

	private void endLine(StringBuilder contentBuilder) {
		removeLastCharacterfComma(contentBuilder);
		contentBuilder.append(System.lineSeparator());
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
