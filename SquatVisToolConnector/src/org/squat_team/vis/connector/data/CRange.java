package org.squat_team.vis.connector.data;

import java.io.Serializable;

public class CRange implements Serializable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = 7704711097956820518L;

	private Double rangeMin;
	private Double rangeMax;
	private Boolean computeMin;
	private Boolean computeMax;

	public Double getRangeMin() {
		return rangeMin;
	}

	public void setRangeMin(Double rangeMin) {
		this.rangeMin = rangeMin;
	}

	public Double getRangeMax() {
		return rangeMax;
	}

	public void setRangeMax(Double rangeMax) {
		this.rangeMax = rangeMax;
	}

	public Boolean getComputeMin() {
		return computeMin;
	}

	public void setComputeMin(Boolean computeMin) {
		this.computeMin = computeMin;
	}

	public Boolean getComputeMax() {
		return computeMax;
	}

	public void setComputeMax(Boolean computeMax) {
		this.computeMax = computeMax;
	}

}
