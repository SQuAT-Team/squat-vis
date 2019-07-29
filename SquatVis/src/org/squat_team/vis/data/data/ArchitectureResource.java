package org.squat_team.vis.data.data;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class ArchitectureResource {
	private String name;
	private String resourceId;
	private double value;
}
