package org.squat_team.vis.data.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class ArchitectureAnalysisData {
	@Id
	@GeneratedValue
	private Long id;

	private List<ArchitectureComponent> components = new ArrayList<>();;
	private List<ArchitectureComponentLink> componentLinks = new ArrayList<>();;
	private List<ArchitectureContainerResource> resourcesContainers = new ArrayList<>();;
	private List<ArchitectureComponentAllocation> allocations = new ArrayList<>();;
}
