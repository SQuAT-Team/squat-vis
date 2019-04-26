package org.squat_team.vis.data.data;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Status {
	private static final double toolWeight = 0.9;
	private static final double visWeight = 0.1;

	private Double toolProgress;
	private Double visProgress;
	private String toolMessage;
	private String visMessage;

	public static double getToolweight() {
		return toolWeight;
	}

	public static double getVisweight() {
		return visWeight;
	}
}
