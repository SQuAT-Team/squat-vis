package org.squat_team.vis.transformers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.squat_team.vis.data.data.ArchitectureAnalysisData;
import org.squat_team.vis.data.data.ArchitectureContainerResource;
import org.squat_team.vis.data.data.Candidate;
import org.squat_team.vis.data.data.Level;
import org.squat_team.vis.data.data.Project;
import org.squat_team.vis.session.ProjectInfo;

public class ArchitectureResourceContainersToCSVExporter extends AbstractExporter {
	private Map<String, ArchitectureContainerResource> resourceContainers = new HashMap<>();

	public String export(Project project, ProjectInfo projectInfo) {
		StringBuilder contentBuilder = new StringBuilder();
		exportHeader(contentBuilder);
		findResourceContainersInLevels(findActiveLevels(project, projectInfo));
		exportResourceContainers(contentBuilder);
		return contentBuilder.toString();
	}

	private void exportResourceContainers(StringBuilder contentBuilder) {
		for (ArchitectureContainerResource resourceContainer : resourceContainers.values()) {
			contentBuilder.append(resourceContainer.getResourceId());
			endValue(contentBuilder);
			contentBuilder.append(resourceContainer.getName());
			endLine(contentBuilder);
		}
	}

	private void exportHeader(StringBuilder contentBuilder) {
		contentBuilder.append("ID");
		endValue(contentBuilder);
		contentBuilder.append("Name");
		endLine(contentBuilder);
	}

	private void findResourceContainersInLevels(List<Level> levels) {
		for (Level level : levels) {
			findResourceContainersInLevel(level);
		}
	}

	private void findResourceContainersInLevel(Level level) {
		for (Candidate candidate : level.getCandidates()) {
			findResourceContainersInCandidate(candidate);
		}
	}

	private void findResourceContainersInCandidate(Candidate candidate) {
		ArchitectureAnalysisData analysisData = candidate.getStaticArchitectureAnalysisData();
		if (analysisData == null) {
			return;
		}
		List<ArchitectureContainerResource> candidatesContainerResources = analysisData.getResourcesContainers();

		for (ArchitectureContainerResource currentCandidatesContainerResource : candidatesContainerResources) {
			if (!currentCandidatesContainerResource.isLink()) {
				String resourceContainerId = currentCandidatesContainerResource.getResourceId();
				ArchitectureContainerResource resourceContainerInMap = resourceContainers.get(resourceContainerId);
				if (resourceContainerInMap == null) {
					resourceContainerInMap = currentCandidatesContainerResource;
					resourceContainers.put(resourceContainerId, resourceContainerInMap);
				}
			}
		}
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
