package org.squat_team.vis.transformers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.squat_team.vis.data.data.ArchitectureAnalysisData;
import org.squat_team.vis.data.data.ArchitectureComponent;
import org.squat_team.vis.data.data.ArchitectureComponentLink;
import org.squat_team.vis.data.data.Candidate;
import org.squat_team.vis.data.data.Level;
import org.squat_team.vis.data.data.Project;
import org.squat_team.vis.session.ProjectInfo;

public class ArchitectureLinksToCSVExporter extends AbstractExporter {
	private static final String CANDIDATE_SEPARATOR = " + ";
	private Map<String, Entry> links = new HashMap<>();
	private boolean useNameInsteadOfId;

	public String export(Project project, ProjectInfo projectInfo) {
		useNameInsteadOfId = projectInfo.getOptionsInfo().getUseNameInsteadOfId();
		StringBuilder contentBuilder = new StringBuilder();
		exportHeader(contentBuilder);
		findLinksInLevels(findActiveLevels(project, projectInfo));
		exportLinks(contentBuilder);
		return contentBuilder.toString();
	}

	private void exportLinks(StringBuilder contentBuilder) {
		for (Entry link : links.values()) {
			contentBuilder.append(getIdFrom(link.getLink().getSource()));
			endValue(contentBuilder);
			contentBuilder.append(getIdFrom(link.getLink().getTarget()));
			endValue(contentBuilder);
			contentBuilder.append(link.getCandidates());
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

	private void findLinksInLevels(List<Level> levels) {
		for (Level level : levels) {
			findLinksInLevel(level);
		}
	}

	private void findLinksInLevel(Level level) {
		for (Candidate candidate : level.getCandidates()) {
			findLinksInCandidate(candidate);
		}
	}

	private void findLinksInCandidate(Candidate candidate) {
		ArchitectureAnalysisData analysisData = candidate.getStaticArchitectureAnalysisData();
		if (analysisData == null) {
			return;
		}
		List<ArchitectureComponentLink> candidatesLinks = analysisData.getComponentLinks();

		for (ArchitectureComponentLink currentCandidatesLink : candidatesLinks) {
			String linkId = getLinkIdentifier(currentCandidatesLink);
			Entry linkInMap = links.get(linkId);
			if (linkInMap == null) {
				linkInMap = new Entry(currentCandidatesLink);
				links.put(linkId, linkInMap);
			}
			linkInMap.addCandidate(candidate.getCandidateId().toString());
		}
	}

	private String getLinkIdentifier(ArchitectureComponentLink link) {
		return link.getSource().getComponentId() + CANDIDATE_SEPARATOR + link.getTarget().getComponentId();
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
		private ArchitectureComponentLink link;
		private String candidates = "";

		public Entry(ArchitectureComponentLink link) {
			this.link = link;
		}

		public ArchitectureComponentLink getLink() {
			return link;
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
