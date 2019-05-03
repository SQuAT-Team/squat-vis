package org.squat_team.vis.data.data;

import java.util.Date;

import javax.persistence.Embeddable;

import org.squat_team.vis.util.DateDifferenceFormatter;

import lombok.Data;

/**
 * Describes the status of the current optimization run.<br/>
 * <br/>
 * Note that {@link #setToolProgress(Double)} and
 * {@link #setVisProgress(Double)} describes the progress as number between 0.0
 * and 1.0. {@link #setToolMessage(String)} and {@link #setVisMessage(String)}
 * are status messages that might be visible to the architect.
 */
@Data
@Embeddable
public class Status {
	private static final long TOOL_UPDATE_TIMEOUT = 180000; // 3min

	private static final String TOOL_LEVEL_FINISHED_MESSAGE = "Level finished";
	private static final String TOOL_NEW_LEVEL_MESSAGE = "Started Search for Alternatives";
	private static final String VIS_IMPORTING_MESSAGE = "Level Import in progress";
	private static final String VIS_IMPORT_FINISHED_MESSAGE = "Level Import finished";
	private static final String VIS_NEW_LEVEL_MESSAGE = "";

	private static final double TOOL_WEIGHT = 0.9;
	private static final double VIS_WEIGHT = 0.1;

	public enum StatusType {
		RUNNING, WAITING, EXCEPTION, TERMINATED
	}

	private Double toolProgress = 0d;
	private Double visProgress = 0d;
	private String toolMessage = "";
	private String visMessage = "";
	private Date lastUpdate;
	private Date levelStarted;
	private Date creationTime;
	private boolean exception = false;
	private boolean terminated = false;

	public String getLastUpdate() {
		return (new DateDifferenceFormatter()).formatDifference(lastUpdate, new Date());
	}

	public String getLevelStarted() {
		return (new DateDifferenceFormatter()).formatDifference(levelStarted, new Date());
	}

	public String getCreationTime() {
		return (new DateDifferenceFormatter()).formatDifference(creationTime, new Date());
	}

	public static double getToolweight() {
		return TOOL_WEIGHT;
	}

	public static double getVisweight() {
		return VIS_WEIGHT;
	}

	public int getTotalProgress() {
		init();
		Double totalProgress = 100 * (TOOL_WEIGHT * toolProgress + VIS_WEIGHT * visProgress);
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
		lastUpdate = new Date();
	}

	public void notifyLevelImported() {
		this.visProgress = 1d;
		this.toolMessage = VIS_IMPORT_FINISHED_MESSAGE;
		lastUpdate = new Date();
	}

	public void notifyNewLevel() {
		this.visProgress = 0d;
		this.toolProgress = 0d;
		this.toolMessage = TOOL_NEW_LEVEL_MESSAGE;
		this.visMessage = VIS_NEW_LEVEL_MESSAGE;
		lastUpdate = new Date();
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
