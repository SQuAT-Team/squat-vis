package org.squat_team.vis.connector.style;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.squat_team.vis.data.data.Status;
import org.squat_team.vis.data.data.Status.StatusType;

import lombok.NonNull;

/**
 * Maps {@link StatusType}s to the correct CSS-Tags and descriptions.
 */
@Named
@RequestScoped
public class StatusMapper {

	private static final String STATUS_RUNNING_CSS = "running";
	private static final String STATUS_WAITING_CSS = "waiting";
	private static final String STATUS_EXCEPTION_CSS = "exception";
	private static final String STATUS_TERMINATED_CSS = "terminated";

	private static final String STATUS_RUNNING_DESCRIPTION = "Status: Optimization is running";
	private static final String STATUS_WAITING_DESCRIPTION = "Status: User interaction required";
	private static final String STATUS_EXCEPTION_DESCRIPTION = "Status: Tool not responding or error occurred";
	private static final String STATUS_TERMINATED_DESCRIPTION = "Status: Project terminated";

	/**
	 * Returns the correct CSS-Tag for the correct color of the status.
	 * 
	 * @param statusType the current status
	 * @return the corresponding CSS-Tag, can be placed in the class attribute
	 */
	public String map(@NonNull Status.StatusType statusType) {
		switch (statusType) {
		case RUNNING:
			return STATUS_RUNNING_CSS;
		case WAITING:
			return STATUS_WAITING_CSS;
		case EXCEPTION:
			return STATUS_EXCEPTION_CSS;
		case TERMINATED:
			return STATUS_TERMINATED_CSS;
		default:
			return "";
		}
	}

	/**
	 * Returns the description for a status.
	 * 
	 * @param statusType the current status
	 * @return the description, can be placed in a title attribute
	 */
	public String mapDescription(@NonNull Status.StatusType statusType) {
		switch (statusType) {
		case RUNNING:
			return STATUS_RUNNING_DESCRIPTION;
		case WAITING:
			return STATUS_WAITING_DESCRIPTION;
		case EXCEPTION:
			return STATUS_EXCEPTION_DESCRIPTION;
		case TERMINATED:
			return STATUS_TERMINATED_DESCRIPTION;
		default:
			return "";
		}
	}
}
