package org.squat_team.vis.test;

import org.squat_team.vis.connector.data.CStatus;

public class TestStatusUpdateDataProvider {

	private static final double STATUS_1_PROGRESS = 0.1;
	private static final String STATUS_1_MESSAGE = "Initialized Analaysis Modules";
	
	public CStatus getStatus1of5() {
		CStatus status = new CStatus(STATUS_1_PROGRESS);
		status.setMessage(STATUS_1_MESSAGE);
		return status;
	}

}
