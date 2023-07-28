package org.squat_team.vis.test.analysis;

import lombok.Data;

/**
 * Describes the range of values that should be shown. A range is part of a
 * {@link Goal}. {@link #setComputeMin(Boolean)} and
 * {@link #setComputeMax(Boolean)} can be used if the server should compute
 * these values.
 */
@Data
public class Range {
	private Double rangeMin;
	private Double rangeMax;
	private Boolean computeMin;
	private Boolean computeMax;
}
