package org.squat_team.vis.test;

import org.squat_team.vis.connector.data.CStatus;

public class TestStatusUpdateDataProvider {

	private static final double STATUS_1_PROGRESS = 0.1;
	private static final String STATUS_1_MESSAGE = "Initialized Analaysis Modules";
	private static final double STATUS_2_PROGRESS = 0.2;
	private static final String STATUS_2_MESSAGE = "Search for Alternatives";
	private static final double STATUS_3_PROGRESS = 0.4;
	private static final String STATUS_3_MESSAGE = "Search for Alternatives";
	private static final double STATUS_4_PROGRESS = 0.6;
	private static final String STATUS_4_MESSAGE = "Search for Alternatives";
	private static final double STATUS_5_PROGRESS = 0.8;
	private static final String STATUS_5_MESSAGE = "Reanalyzing new Alternatives";
	
	public CStatus getStatus1of5() {
		CStatus status = new CStatus(STATUS_1_PROGRESS);
		status.setMessage(STATUS_1_MESSAGE);
		return status;
	}
	
	public CStatus getStatus2of5() {
		CStatus status = new CStatus(STATUS_2_PROGRESS);
		status.setMessage(STATUS_2_MESSAGE);
		return status;
	}

	public CStatus getStatus3of5() {
		CStatus status = new CStatus(STATUS_3_PROGRESS);
		status.setMessage(STATUS_3_MESSAGE);
		return status;
	}
	
	public CStatus getStatus4of5() {
		CStatus status = new CStatus(STATUS_4_PROGRESS);
		status.setMessage(STATUS_4_MESSAGE);
		return status;
	}
	
	public CStatus getStatus5of5() {
		CStatus status = new CStatus(STATUS_5_PROGRESS);
		status.setMessage(STATUS_5_MESSAGE);
		return status;
	}
}
