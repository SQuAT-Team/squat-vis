package org.squat_team.vis.connector.style;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.squat_team.vis.data.data.Status;

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

	public String map(Status.StatusType statusType) {
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

	public String mapDescription(Status.StatusType statusType) {
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
