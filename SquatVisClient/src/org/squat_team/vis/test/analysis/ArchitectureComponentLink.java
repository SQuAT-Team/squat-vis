package org.squat_team.vis.test.analysis;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@XmlRootElement
@Data
public class ArchitectureComponentLink {
	private ArchitectureComponent source;
	private ArchitectureComponent target;
}
