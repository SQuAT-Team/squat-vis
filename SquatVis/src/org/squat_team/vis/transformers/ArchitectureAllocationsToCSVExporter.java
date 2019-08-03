package org.squat_team.vis.transformers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.squat_team.vis.data.data.ArchitectureAnalysisData;
import org.squat_team.vis.data.data.ArchitectureComponent;
import org.squat_team.vis.data.data.ArchitectureComponentAllocation;
import org.squat_team.vis.data.data.Candidate;
import org.squat_team.vis.data.data.Level;
import org.squat_team.vis.data.data.Project;
import org.squat_team.vis.session.ProjectInfo;

public class ArchitectureAllocationsToCSVExporter {
	private static final String CANDIDATE_SEPARATOR = " + ";
	private Map<String, Entry> allocations = new HashMap<>();
	private boolean useNameInsteadOfId;
	
	public String export(Project project, ProjectInfo projectInfo) {
		useNameInsteadOfId = projectInfo.getOptionsInfo().getUseNameInsteadOfId();
		StringBuilder contentBuilder = new StringBuilder();
		exportHeader(contentBuilder);
		findAllocationsInLevels(project.getLevels());
		exportAllocations(contentBuilder);
		return contentBuilder.toString();
	}

	private void exportAllocations(StringBuilder contentBuilder) {
		for (Entry allocation : allocations.values()) {
			contentBuilder.append(getIdFrom(allocation.getAllocation().getComponent()));
			endValue(contentBuilder);
			contentBuilder.append(allocation.getAllocation().getContainer().getResourceId());
			endValue(contentBuilder);
			contentBuilder.append(allocation.getCandidates());
			endLine(contentBuilder);
		}
	}

	private void exportHeader(StringBuilder contentBuilder) {
		contentBuilder.append("source");
		endValue(contentBuilder);
		contentBuilder.append("target");
		endValue(contentBuilder);
		contentBuilder.append("candidates");
		endLine(contentBuilder);
	}

	private void findAllocationsInLevels(List<Level> levels) {
		for (Level level : levels) {
			findAllocationsInLevel(level);
		}
	}

	private void findAllocationsInLevel(Level level) {
		for (Candidate candidate : level.getCandidates()) {
			findAllocationsInCandidate(candidate);
		}
	}

	private void findAllocationsInCandidate(Candidate candidate) {
		ArchitectureAnalysisData analysisData = candidate.getStaticArchitectureAnalysisData();
		if(analysisData == null) {
			return;
		}
		List<ArchitectureComponentAllocation> candidatesAllocations = analysisData.getAllocations();

		for (ArchitectureComponentAllocation currentCandidatesAllocation : candidatesAllocations) {
			String allocationId = getAllocationIdentifier(currentCandidatesAllocation);
			Entry linkInMap = allocations.get(allocationId);
			if (linkInMap == null) {
				linkInMap = new Entry(currentCandidatesAllocation);
				allocations.put(allocationId, linkInMap);
			}
			linkInMap.addCandidate(candidate.getCandidateId().toString());
		}
	}
	
	private String getAllocationIdentifier(ArchitectureComponentAllocation link) {
		return link.getComponent().getComponentId()+CANDIDATE_SEPARATOR+link.getContainer().getResourceId();
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

	private String getIdFrom(ArchitectureComponent component) {
		if (useNameInsteadOfId) {
			return replaceAllWhitespaces(component.getName());
		} else {
			return component.getComponentId();
		}
	}

	private String replaceAllWhitespaces(String name) {
		return name.replaceAll("\\s+", "-");
	}

	private class Entry {
		private ArchitectureComponentAllocation allocation;
		private String candidates = "";

		public Entry(ArchitectureComponentAllocation allocation) {
			this.allocation = allocation;
		}

		public ArchitectureComponentAllocation getAllocation() {
			return allocation;
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
