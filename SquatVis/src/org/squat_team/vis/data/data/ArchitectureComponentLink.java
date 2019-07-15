package org.squat_team.vis.data.data;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class ArchitectureComponentLink {
	private ArchitectureComponent source;
	private ArchitectureComponent target;
}
