package org.squat_team.vis.transformers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.squat_team.vis.data.data.ArchitectureAnalysisData;
import org.squat_team.vis.data.data.ArchitectureComponent;
import org.squat_team.vis.data.data.Candidate;
import org.squat_team.vis.data.data.Level;
import org.squat_team.vis.data.data.Project;
import org.squat_team.vis.session.ProjectInfo;

import lombok.extern.java.Log;

@Log
public class ArchitectureComponentsToCSVExporter {
	private static final String CANDIDATE_SEPARATOR = " + ";
	private Map<String, Entry> components = new HashMap<>();

	public String export(Project project, ProjectInfo projectInfo) {
		StringBuilder contentBuilder = new StringBuilder();
		exportHeader(contentBuilder);
		findComponentsInLevels(project.getLevels());
		exportComponents(contentBuilder);
		return contentBuilder.toString();
	}

	private void exportComponents(StringBuilder contentBuilder) {
		for (Entry component : components.values()) {
			contentBuilder.append(component.getComponent().getComponentId());
			endValue(contentBuilder);
			contentBuilder.append(component.getComponent().getName());
			endValue(contentBuilder);
			contentBuilder.append(component.getCandidates());
			endLine(contentBuilder);
		}
	}

	private void exportHeader(StringBuilder contentBuilder) {
		contentBuilder.append("ID");
		endValue(contentBuilder);
		contentBuilder.append("Name");
		endValue(contentBuilder);
		contentBuilder.append("Candidates");
		endLine(contentBuilder);
	}

	private void findComponentsInLevels(List<Level> levels) {
		for (Level level : levels) {
			findComponentsInLevel(level);
		}
	}

	private void findComponentsInLevel(Level level) {
		for (Candidate candidate : level.getCandidates()) {
			findComponentsInCandidate(candidate);
		}
	}

	private void findComponentsInCandidate(Candidate candidate) {
		ArchitectureAnalysisData analysisData = candidate.getStaticArchitectureAnalysisData();
		if(analysisData == null) {
			log.log(java.util.logging.Level.WARNING, "Did not find architecture for candidate "+ candidate.getCandidateId());
			return;
		}
		List<ArchitectureComponent> candidatesComponents = analysisData.getComponents();

		for (ArchitectureComponent currentCandidatesComponent : candidatesComponents) {
			String componentId = currentCandidatesComponent.getComponentId();
			Entry componentInMap = components.get(componentId);
			if (componentInMap == null) {
				componentInMap = new Entry(currentCandidatesComponent);
				components.put(componentId, componentInMap);
			}
			componentInMap.addCandidate(candidate.getCandidateId().toString());
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

	private class Entry {
		private ArchitectureComponent component;
		private String candidates = "";

		public Entry(ArchitectureComponent component) {
			this.component = component;
		}

		public ArchitectureComponent getComponent() {
			return component;
		}

		public String getCandidates() {
			return candidates;
		}

		public void addCandidate(String candidateId) {
			if (candidates.length() == 0) {
				candidates = candidateId;
			} else {
				candidates += CANDIDATE_SEPARATOR + candidateId;
			}
		}

	}
}
