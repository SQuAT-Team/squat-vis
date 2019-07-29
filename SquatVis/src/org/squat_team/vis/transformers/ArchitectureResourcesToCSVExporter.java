package org.squat_team.vis.transformers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.squat_team.vis.data.data.ArchitectureAnalysisData;
import org.squat_team.vis.data.data.ArchitectureContainerResource;
import org.squat_team.vis.data.data.ArchitectureResource;
import org.squat_team.vis.data.data.Candidate;
import org.squat_team.vis.data.data.Level;
import org.squat_team.vis.data.data.Project;
import org.squat_team.vis.session.ProjectInfo;

import lombok.extern.java.Log;

@Log
public class ArchitectureResourcesToCSVExporter {
	private Map<String, Entry> resources = new HashMap<>();
	private Collection<Entry> sortedResources;

	public String export(Project project, ProjectInfo projectInfo) {
		StringBuilder contentBuilder = new StringBuilder();
		findResourcesInLevels(project.getLevels());
		sortedResources = sortEntries(resources.values());
		exportHeader(contentBuilder);
		exportResourcesInLevels(project.getLevels(), contentBuilder);
		return contentBuilder.toString();
	}
	
	public String exportResourcesMetadata() {
		StringBuilder contentBuilder = new StringBuilder();
		contentBuilder.append("ContainerId");
		endValue(contentBuilder);
		contentBuilder.append("Name");
		endValue(contentBuilder);
		contentBuilder.append("Min");
		endValue(contentBuilder);
		contentBuilder.append("Max");
		endLine(contentBuilder);
		for(Entry entry : sortedResources) {
			contentBuilder.append(entry.container.getResourceId());
			endValue(contentBuilder);
			contentBuilder.append(entry.getName());
			endValue(contentBuilder);
			contentBuilder.append(entry.getMinValue());
			endValue(contentBuilder);
			contentBuilder.append(entry.getMaxValue());
			endLine(contentBuilder);
		}
		return contentBuilder.toString();
	}
	
	private void exportHeader(StringBuilder contentBuilder) {
		contentBuilder.append("ID");
		endValue(contentBuilder);
		for (Entry entry : sortedResources) {
			contentBuilder.append(entry.getName());
			endValue(contentBuilder);
		}
		endLine(contentBuilder);
	}

	private void findResourcesInLevels(List<Level> levels) {
		for (Level level : levels) {
			findResourcesInLevel(level);
		}
	}

	private void exportResourcesInLevels(List<Level> levels, StringBuilder contentBuilder) {
		for (Level level : levels) {
			exportResourcesInLevel(level, contentBuilder);
		}
	}

	private void findResourcesInLevel(Level level) {
		for (Candidate candidate : level.getCandidates()) {
			findResourcesInCandidate(candidate);
		}
	}

	private void exportResourcesInLevel(Level level, StringBuilder contentBuilder) {
		for (Candidate candidate : level.getCandidates()) {
			exportResourcesInCandidate(candidate, contentBuilder);
		}
	}

	private void exportResourcesInCandidate(Candidate candidate, StringBuilder contentBuilder) {
		contentBuilder.append(candidate.getCandidateId());
		endValue(contentBuilder);
		for (Entry entry : sortedResources) {
			contentBuilder.append(entry.getCandidateValue(candidate.getCandidateId()));
			endValue(contentBuilder);
		}
		endLine(contentBuilder);
	}

	private void findResourcesInCandidate(Candidate candidate) {
		ArchitectureAnalysisData analysisData = candidate.getStaticArchitectureAnalysisData();
		if (analysisData == null) {
			log.log(java.util.logging.Level.WARNING,
					"Did not find architecture for candidate " + candidate.getCandidateId());
			return;
		}
		List<ArchitectureContainerResource> candidatesContainers = analysisData.getResourcesContainers();

		for (ArchitectureContainerResource currentCandidatesContainer : candidatesContainers) {
			for (ArchitectureResource currentCandidateResource : currentCandidatesContainer.getResources()) {
				String resourceId = currentCandidatesContainer.getResourceId() + currentCandidateResource.getResourceId();
				Entry resourcesInMap = resources.get(resourceId);
				if (resourcesInMap == null) {
					resourcesInMap = new Entry(currentCandidatesContainer, currentCandidateResource);
					resources.put(resourceId, resourcesInMap);
				}
				resourcesInMap.setCandidateValue(candidate.getCandidateId(), currentCandidateResource.getValue());
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
	
	private Collection<Entry> sortEntries(Collection<Entry> entries) {
		List<Entry> entriesList = new ArrayList<>();
		entriesList.addAll(entries);
		Collections.sort(entriesList);
		return entriesList;
	}

	private class Entry implements Comparable<Entry>{
		private ArchitectureContainerResource container;
		private ArchitectureResource resource;
		private Map<String, Double> values = new HashMap<>();

		public Entry(ArchitectureContainerResource container, ArchitectureResource resource) {
			this.container = container;
			this.resource = resource;
		}

		public String getName() {
			return container.getName() + " " + resource.getName();
		}

		public void setCandidateValue(Long candidateId, Double value) {
			values.put(candidateId.toString(), value);
		}

		public String getCandidateValue(Long candidateId) {
			Double value = values.get(candidateId.toString());
			if (value == null) {
				value = 0d;
			}
			return value.toString();
		}
		
		public Double getMinValue() {
			double minValue = Double.MAX_VALUE;
			for(double value : values.values()) {
				if(value < minValue) {
					minValue = value;
				}
			}
			return minValue;
		}
		
		public Double getMaxValue() {
			double maxValue = 0;
			for(double value : values.values()) {
				if(value > maxValue) {
					maxValue = value;
				}
			}
			return maxValue;
		}

		@Override
		public int compareTo(Entry o) {
			return this.getName().compareTo(o.getName());
		}
	}
}
