package org.squat_team.vis.connector.data;

import java.io.Serializable;

public class CStatus implements Serializable {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = 9109266430504939845L;

	private double progress;
	private String message;

	public CStatus(double progress) {
		this.progress = progress;
	}

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
