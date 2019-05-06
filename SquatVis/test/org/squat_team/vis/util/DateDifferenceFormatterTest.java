package org.squat_team.vis.util;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

/**
 * Tests the {@link DateDifferenceFormatter}
 */
public class DateDifferenceFormatterTest {
	private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	/**
	 * Tests the case in which the difference is 0 seconds.
	 */
	@Test
	public void testZeroSeconds() throws ParseException {
		Date earlierDate = formatToDate("01-01-2019 12:00:00");
		Date laterDate = formatToDate("01-01-2019 12:00:00");
		String formattedDateDifference = execute(earlierDate, laterDate);
		assertEquals("00s", formattedDateDifference);
	}

	/**
	 * Tests the case in which the difference is 59 seconds
	 */
	@Test
	public void testNearlyOneMinute() throws ParseException {
		Date earlierDate = formatToDate("01-01-2019 12:00:01");
		Date laterDate = formatToDate("01-01-2019 12:01:00");
		String formattedDateDifference = execute(earlierDate, laterDate);
		assertEquals("59s", formattedDateDifference);
	}

	/**
	 * Tests the case in which the difference is 1 minute.
	 */
	@Test
	public void testOneMinute() throws ParseException {
		Date earlierDate = formatToDate("01-01-2019 12:00:00");
		Date laterDate = formatToDate("01-01-2019 12:01:00");
		String formattedDateDifference = execute(earlierDate, laterDate);
		assertEquals("01m 00s", formattedDateDifference);
	}

	/**
	 * Tests the case in which the difference is 59 minutes and 59 seconds.
	 */
	@Test
	public void testNearlyOneHour() throws ParseException {
		Date earlierDate = formatToDate("01-01-2019 12:00:00");
		Date laterDate = formatToDate("01-01-2019 12:59:59");
		String formattedDateDifference = execute(earlierDate, laterDate);
		assertEquals("59m 59s", formattedDateDifference);
	}

	/**
	 * Tests the case in which the difference is 1 hour.
	 */
	@Test
	public void testOneHour() throws ParseException {
		Date earlierDate = formatToDate("01-01-2019 12:00:00");
		Date laterDate = formatToDate("01-01-2019 13:00:00");
		String formattedDateDifference = execute(earlierDate, laterDate);
		assertEquals("01h 00m 00s", formattedDateDifference);
	}

	/**
	 * Tests the case in which the difference is 23 hours, 59 minutes, and 59
	 * seconds.
	 */
	@Test
	public void testNearlyOneDay() throws ParseException {
		Date earlierDate = formatToDate("01-01-2019 00:00:00");
		Date laterDate = formatToDate("01-01-2019 23:59:59");
		String formattedDateDifference = execute(earlierDate, laterDate);
		assertEquals("23h 59m 59s", formattedDateDifference);
	}

	/**
	 * Tests the case in which the dates differ in the day.
	 */
	@Test
	public void testNextDay() throws ParseException {
		Date earlierDate = formatToDate("01-01-2019 12:00:00");
		Date laterDate = formatToDate("02-01-2019 00:00:00");
		String formattedDateDifference = execute(earlierDate, laterDate);
		assertEquals("01-01-2019 12:00:00", formattedDateDifference);
	}

	/**
	 * Runs the formatter and returns its result
	 * 
	 * @param earlierDate the earlier date
	 * @param laterDate   the later date
	 * @return the result of the formatter
	 */
	private String execute(Date earlierDate, Date laterDate) {
		DateDifferenceFormatter formatter = new DateDifferenceFormatter();
		return formatter.formatDifference(earlierDate, laterDate);
	}

	/**
	 * Turns a String into a date object
	 * 
	 * @param date the string object
	 * @return the date object
	 * @throws ParseException if parsing is not possible
	 */
	private Date formatToDate(String date) throws ParseException {
		return format.parse(date);
	}
}
