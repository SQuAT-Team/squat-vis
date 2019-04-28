package org.squat_team.vis.data.data;

import java.util.Date;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Status {
	private static final long TOOL_UPDATE_TIMEOUT = 180000; // 3min

	private static final String TOOL_LEVEL_FINISHED_MESSAGE = "Level finished";
	private static final String VIS_IMPORTING_MESSAGE = "Level Import in progress";
	private static final String VIS_IMPORT_FINISHED_MESSAGE = "Level Import finished";

	public enum StatusType {
		RUNNING, WAITING, EXCEPTION, TERMINATED
	}

	private static final double toolWeight = 0.9;
	private static final double visWeight = 0.1;

	private Double toolProgress;
	private Double visProgress;
	private String toolMessage;
	private String visMessage;
	private Date lastUpdate;
	private Date levelStarted;
	private Date creationTime;
	private boolean exception = false;
	private boolean terminated = false;

	public static double getToolweight() {
		return toolWeight;
	}

	public static double getVisweight() {
		return visWeight;
	}

	public int getTotalProgress() {
		init();
		Double totalProgress = 100 * (toolWeight * toolProgress + visWeight * visProgress);
		return limit(totalProgress.intValue());
	}

	public String getMessage() {
		if (toolProgress.equals(1d)) {
			return visMessage;
		} else {
			return toolMessage;
		}
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

	public void notifyLevelFinished() {
		this.toolProgress = 1d;
		this.toolMessage = TOOL_LEVEL_FINISHED_MESSAGE;
		this.visMessage = VIS_IMPORTING_MESSAGE;
	}

	public void notifyLevelImported() {
		this.visProgress = 1d;
		this.toolMessage = VIS_IMPORT_FINISHED_MESSAGE;
	}

	public StatusType getType() {
		if (isTerminated()) {
			return StatusType.TERMINATED;
		}
		if (isException()) {
			return StatusType.EXCEPTION;
		}
		if (isWaiting()) {
			return StatusType.WAITING;
		}
		return StatusType.RUNNING;
	}

	private boolean isException() {
		Date now = new Date();
		long timeSinceLastUpdate = now.getTime() - lastUpdate.getTime();
		boolean timeout = timeSinceLastUpdate > TOOL_UPDATE_TIMEOUT;
		boolean toolFinished = toolProgress.equals(1d);
		return exception || (!toolFinished && timeout);
	}

	private boolean isTerminated() {
		return terminated;
	}

	private boolean isWaiting() {
		boolean toolFinished = toolProgress.equals(1d);
		boolean visToolFinished = visProgress.equals(1d);
		return toolFinished && visToolFinished;
	}

}
