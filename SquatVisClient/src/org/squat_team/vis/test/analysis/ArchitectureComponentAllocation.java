package org.squat_team.vis.test.analysis;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@XmlRootElement
@Data
public class ArchitectureComponentAllocation {
	private ArchitectureComponent component;
	private ArchitectureContainerResource container;
}
