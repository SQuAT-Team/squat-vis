package org.squat_team.vis.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateFormatter {

	public String formatDifference(Date dateEarlier, Date dateLater) {
		long diffInSeconds = getDateDiff(dateEarlier, dateLater, TimeUnit.SECONDS);
		if (diffInSeconds < 60) {
			return String.format("%02d", diffInSeconds) + "s";
		}
		long diffInMinutes = getDateDiff(dateEarlier, dateLater, TimeUnit.MINUTES);
		if (diffInMinutes < 60) {
			return String.format("%02d", diffInMinutes) + "m " + String.format("%02d", (diffInSeconds % 60)) + "s";
		}
		SimpleDateFormat formatDay = new SimpleDateFormat("dd-MM-yyyy");
		String dateEarlierDayString = formatDay.format(dateEarlier);
		String dateLaterDayString = formatDay.format(dateEarlier);
		long diffInHours = getDateDiff(dateEarlier, dateLater, TimeUnit.MINUTES);
		if (dateEarlierDayString.equals(dateLaterDayString)) {
			return String.format("%02d", diffInHours) + "h " + String.format("%02d", (diffInMinutes % 60)) + "m "
					+ String.format("%02d", (diffInSeconds % 60)) + "s";
		}
		SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
		String dateEarlierTimeString = formatTime.format(dateEarlier);
		return dateEarlierDayString + " " + dateEarlierTimeString;
	}

	/**
	 * Get a diff between two dates
	 * 
	 * @param date1    the oldest date
	 * @param date2    the newest date
	 * @param timeUnit the unit in which you want the diff
	 * @return the diff value, in the provided unit
	 */
	public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();
		return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}
}
