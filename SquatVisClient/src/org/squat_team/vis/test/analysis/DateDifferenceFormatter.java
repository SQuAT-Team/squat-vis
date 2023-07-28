package org.squat_team.vis.test.analysis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Formats the time difference of two dates in a human-readable form.
 */
public class DateDifferenceFormatter {
	private SimpleDateFormat formatDay = new SimpleDateFormat("dd-MM-yyyy");
	private SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");

	/**
	 * Formats the time difference with second precision. Outputs relative time
	 * difference for the same day, e.g., 01m 02s. For different days, the point of
	 * time of the earlier date is returned, e.g., 13-01-2019 14:15:00.
	 * 
	 * @param dateEarlier the earlier date
	 * @param dateLater   the later date
	 * @return formated time difference or a point in time of the earlier date.
	 */
	public String formatDifference(Date dateEarlier, Date dateLater) {
		String formatedDifference = null;
		long diffInSeconds = getDateDiff(dateEarlier, dateLater, TimeUnit.SECONDS);
		formatedDifference = checkSeconds(diffInSeconds);
		long diffInMinutes = getDateDiff(dateEarlier, dateLater, TimeUnit.MINUTES);
		formatedDifference = checkMinutes(diffInMinutes, diffInSeconds, formatedDifference);
		formatedDifference = checkHour(dateEarlier, dateLater, diffInMinutes, diffInSeconds, formatedDifference);
		formatedDifference = formatAsTimepoint(dateEarlier, formatedDifference);
		return formatedDifference;
	}

	/**
	 * Returns the point in time of the specified date, if no formated text is
	 * available yet.
	 * 
	 * @param dateEarlier        the date which marks the point in time
	 * @param formatedDifference the formated text so far. Method will return this
	 *                           String, if it is not null.
	 * @return the point in time of the date, e.g., 13-01-2019 14:15:00 or the
	 *         formatedDifference, if not null.
	 */
	private String formatAsTimepoint(Date dateEarlier, String formatedDifference) {
		if (formatedDifference != null) {
			return formatedDifference;
		}
		String dateEarlierDayString = formatDay.format(dateEarlier);
		String dateEarlierTimeString = formatTime.format(dateEarlier);
		return dateEarlierDayString + " " + dateEarlierTimeString;
	}

	/**
	 * Checks whether the hour-format should be used and returns it if the
	 * preconditions are fulfilled. The precondition is that no other format has
	 * been chosen yet and the two dates are equal in their day.
	 * 
	 * @param dateEarlier        the earlier date
	 * @param dateLater          the later date
	 * @param diffInMinutes      the difference between the two dates in minutes
	 * @param diffInSeconds      the difference between the two dates in seconds
	 * @param formatedDifference the formated text so far. Method will return this
	 *                           String, if it is not null.
	 * @return the time formated: contains hours, minutes, and seconds, e.g., 01h
	 *         15m 10s.
	 */
	private String checkHour(Date dateEarlier, Date dateLater, long diffInMinutes, long diffInSeconds,
			String formatedDifference) {
		if (formatedDifference != null) {
			return formatedDifference;
		}
		String dateEarlierDayString = formatDay.format(dateEarlier);
		String dateLaterDayString = formatDay.format(dateLater);
		long diffInHours = getDateDiff(dateEarlier, dateLater, TimeUnit.HOURS);
		if (dateEarlierDayString.equals(dateLaterDayString)) {
			return formatHours(diffInHours) + " " + formatMinutes(diffInMinutes) + " " + formatSeconds(diffInSeconds);
		}
		return null;
	}

	/**
	 * Checks whether the minute-format should be used and returns it if the
	 * preconditions are fulfilled. The precondition is that no other format has
	 * been chosen yet and the time difference is less than one hour.
	 * 
	 * @param diffInMinutes      the difference between the two dates in minutes
	 * @param diffInSeconds      the difference between the two dates in seconds
	 * @param formatedDifference the formated text so far. Method will return this
	 *                           String, if it is not null.
	 * @return the time formated: contains minutes and seconds, e.g., 15m 10s.
	 */
	private String checkMinutes(long diffInMinutes, long diffInSeconds, String formatedDifference) {
		if (formatedDifference != null) {
			return formatedDifference;
		}
		if (diffInMinutes < 60) {
			return formatMinutes(diffInMinutes) + " " + formatSeconds(diffInSeconds);
		}
		return null;
	}

	/**
	 * Checks whether the second-format should be used and returns it if the
	 * preconditions are fulfilled. The precondition is that the time difference is
	 * less than one minute.
	 * 
	 * @param diffInSeconds the difference between the two dates in seconds
	 * @return the time formated: contains only seconds, e.g., 05s.
	 */
	private String checkSeconds(long diffInSeconds) {
		if (diffInSeconds < 60) {
			return formatSeconds(diffInSeconds);
		}
		return null;
	}

	/**
	 * Assures that the number has 2 digits, is less than 1 minute, and has the
	 * correct time unit.
	 * 
	 * @param seconds some time in seconds
	 * @return the formated text for the specified time
	 */
	private String formatSeconds(long seconds) {
		return String.format("%02d", (seconds % 60)) + "s";
	}

	/**
	 * Assures that the number has 2 digits, is less than 1 hour, and has the
	 * correct time unit.
	 * 
	 * @param minutes some time in minutes
	 * @return the formated text for the specified time
	 */
	private String formatMinutes(long minutes) {
		return String.format("%02d", (minutes % 60)) + "m";
	}

	/**
	 * Assures that the number has 2 digits and has the correct time unit.
	 * 
	 * @param hours some time in hours
	 * @return the formated text for the specified time
	 */
	private String formatHours(long hours) {
		return String.format("%02d", hours) + "h";
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
