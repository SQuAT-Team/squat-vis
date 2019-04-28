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

	public int getTotalProgress() {
		init();
		Double totalProgress = 100 * toolWeight * toolProgress + visWeight * visProgress;
		return limit(totalProgress.intValue());
	}

	private int limit(int value) {
		return Math.min(Math.max(value, 0), 100);
	}

	private void init() {
		if (toolProgress == null) {
			toolProgress = 0d;
		}
		if (visProgress == null) {
			visProgress = 0d;
		}
	}
}
