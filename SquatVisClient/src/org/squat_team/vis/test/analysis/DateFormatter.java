package org.squat_team.vis.test.analysis;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
	private SimpleDateFormat formatDay = new SimpleDateFormat("dd-MM-yyyy");
	private SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");

	public String format(Date date) {
		return formatDay.format(date) + " " + formatTime.format(date);
	}
}
