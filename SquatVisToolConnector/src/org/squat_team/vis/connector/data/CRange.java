package org.squat_team.vis.connector.data;

import java.io.Serializable;

import lombok.Data;

/**
 * Describes the range that the values of a {@link CGoal} can take.
 * {@link #setComputeMin(Boolean)} and {@link #setComputeMax(Boolean)} can be
 * used if the server should compute these values.
 */
@Data
public class CRange implements Serializable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = 7704711097956820518L;

	private Double rangeMin;
	private Double rangeMax;
	private Boolean computeMin;
	private Boolean computeMax;

}
