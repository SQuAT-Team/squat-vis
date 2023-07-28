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
import org.squat_team.vis.test.analysis.ArchitectureAnalysisData;
import org.squat_team.vis.test.analysis.PCMArchitectureAnalyzer;

/**
 * Exports the files as a csv file. It contains columns for the candidate's id,
 * and all its atomic goals. A goal can have two columns, if real and utility
 * values exist. Each candidate is then exported in a row.
 */
public class CsvExporter implements IExporter {
	private static final String FILE_EXTENSION = ".csv";
	private String exportDirectoryPath;
	private PCMArchitectureAnalyzer analyzer;

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
		exportArchitecture(contentBuilder);
		exportLevels(levels, contentBuilder, toolConfiguration);
		exportFile(contentBuilder.toString(), filePath);
	}

	private void exportNameHeader(StringBuilder contentBuilder) {
		contentBuilder.append("Candidate ID");
		endValue(contentBuilder);
		contentBuilder.append("Candidate Filename");
		endValue(contentBuilder);
	}

	private void exportGoals(CGoal rootGoal, StringBuilder contentBuilder, CToolConfiguration toolConfiguration) {
		exportGoal(rootGoal, contentBuilder, toolConfiguration);
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

	// TODO:
	private void exportArchitecture(StringBuilder contentBuilder) {
		contentBuilder.append("Component Number");
		endLine(contentBuilder);
	}

	private void exportLevels(List<CLevel> levels, StringBuilder contentBuilder, CToolConfiguration toolConfiguration) {
		for (CLevel level : levels) {
			exportLevel(level, contentBuilder, toolConfiguration);
		}
	}

	private void exportLevel(CLevel level, StringBuilder contentBuilder, CToolConfiguration toolConfiguration) {
		analyzer = new PCMArchitectureAnalyzer(level);
		for (CCandidate candidate : level.getCandidates()) {
			exportCandidate(candidate, contentBuilder, toolConfiguration);
		}
	}

	private void exportCandidate(CCandidate candidate, StringBuilder contentBuilder,
			CToolConfiguration toolConfiguration) {
		System.out.println("Print Candidate " + candidate.getCandidateId());
		ArchitectureAnalysisData analysisData = null;
		try {
			analysisData = analyzer.analyzeCandidateStatic(candidate);
		} catch (IOException e) {
			e.printStackTrace();
		}
		exportCandidateId(candidate, contentBuilder);
		exportCandidatePath(candidate, contentBuilder);

		int numberOfValues = Math.max(candidate.getRealValues().size(), candidate.getUtilityValues().size());
		for (int i = 0; i < numberOfValues; i++) {
			exportCandidateValue(candidate, i, contentBuilder, toolConfiguration);
		}
		exportCandidateArchitecture(candidate, contentBuilder, analysisData);
		endLine(contentBuilder);
	}

	private void exportCandidatePath(CCandidate candidate, StringBuilder contentBuilder) {
		contentBuilder.append(candidate.getArchitecture().getRepository().getPath());
		endValue(contentBuilder);
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

	// TODO:
	private void exportCandidateArchitecture(CCandidate candidate, StringBuilder contentBuilder,
			ArchitectureAnalysisData data) {
		contentBuilder.append(data.getComponents().size());
		endValue(contentBuilder);
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
