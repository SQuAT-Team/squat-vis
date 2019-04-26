package org.squat_team.vis.data.data;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class Range {
	private Double rangeMin;
	private Double rangeMax;
	private Boolean computeMin;
	private Boolean computeMax;
}
