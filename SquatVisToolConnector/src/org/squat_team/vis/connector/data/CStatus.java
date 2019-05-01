package org.squat_team.vis.connector.data;

import java.io.Serializable;

import lombok.Data;

/**
 * Describes the status of the current optimization run.<br/>
 * <br/>
 * Note that {@link #setProgress(double)} describes the progress as number
 * between 0.0 and 1.0. {@link #setMessage(String)} is a status message that
 * might be visible to the architect.
 */
@Data
public class CStatus implements Serializable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = 9109266430504939845L;

	private double progress;
	private String message = "";

	/**
	 * Create a new status.
	 * 
	 * @param progress the progress of the optimization run as a number between 0
	 *                 and 1, with 0 as starting and 1 as finished. Is expected to
	 *                 be proportional to the actual progress with regard to
	 *                 execution time.
	 */
	public CStatus(double progress) {
		if (progress < 0.0 || progress > 1.0) {
			throw new IllegalArgumentException("Progress must be a number between 0 and 1, but is " + progress);
		}
		this.progress = progress;
	}

}
