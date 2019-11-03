package com.asraf.rsrc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public final class DateUtils {
	private final static String DATE_PATTERN_ISO8601 = "yyyy-MM-dd";
	private final static String DATE_TIME_PATTERN_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss";

	public static Date parseGmtDateOrTime(final String dateOrTimeString) {
		String pattern = dateOrTimeString.length() == DATE_PATTERN_ISO8601.length() ? DATE_PATTERN_ISO8601
				: DATE_TIME_PATTERN_ISO8601;
		Date date = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
			date = formatter.parse(dateOrTimeString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date parseDateOrTime(final String dateOrTimeString) {
		String pattern = dateOrTimeString.length() == DATE_PATTERN_ISO8601.length() ? DATE_PATTERN_ISO8601
				: DATE_TIME_PATTERN_ISO8601;
		Date date = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			date = formatter.parse(dateOrTimeString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
