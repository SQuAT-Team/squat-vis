package org.squat_team.vis.data.data;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class ArchitectureComponentAllocation {
	private ArchitectureComponent component;
	private ArchitectureContainerResource container;
}
