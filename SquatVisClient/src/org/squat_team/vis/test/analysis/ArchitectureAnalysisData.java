package org.squat_team.vis.test.analysis;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ArchitectureAnalysisData {
	private Long id;

	private List<ArchitectureComponent> components = new ArrayList<>();;
	private List<ArchitectureComponentLink> componentLinks = new ArrayList<>();;
	private List<ArchitectureContainerResource> resourcesContainers = new ArrayList<>();;
	private List<ArchitectureComponentAllocation> allocations = new ArrayList<>();;
}
