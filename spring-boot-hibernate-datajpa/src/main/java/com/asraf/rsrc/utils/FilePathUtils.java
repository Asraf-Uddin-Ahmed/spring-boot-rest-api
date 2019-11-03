package com.asraf.rsrc.utils;

import java.util.UUID;

public final class FilePathUtils {

	private static String LOCATION = "location/%s.%s";
	private static String EVENT = "event/%s.%s";
	private static String EVENT_LAYOUT = "event/layout/%s.%s";
	private static String VENUE = "venue/%s.%s";
	private static String ORGANIZER = "organizer/%s.%s";
	private static String HOW_TOS = "how-tos/%s.%s";
	private static String INTERVIEW = "interview/%s.%s";
	private static String CASE_STUDY = "case-study/%s.%s";

	public static String getFileExtension(String fileNameOrUrlWithExtension) {
		return fileNameOrUrlWithExtension.substring(fileNameOrUrlWithExtension.lastIndexOf('.') + 1).trim();
	}

	public static String getFileNameWithExtension(String url) {
		return url.substring(url.lastIndexOf('/') + 1).trim();
	}

	public static String getLocationPath(String extension) {
		return String.format(LOCATION, UUID.randomUUID(), extension);
	}

	public static String getEventPath(String extension) {
		return String.format(EVENT, UUID.randomUUID(), extension);
	}

	public static String getEventLayoutPath(String extension) {
		return String.format(EVENT_LAYOUT, UUID.randomUUID(), extension);
	}

	public static String getVenuePath(String extension) {
		return String.format(VENUE, UUID.randomUUID(), extension);
	}

	public static String getOrganizerPath(String extension) {
		return String.format(ORGANIZER, UUID.randomUUID(), extension);
	}

	public static String getHowTosPath(String extension) {
		return String.format(HOW_TOS, UUID.randomUUID(), extension);
	}

	public static String getInterviewPath(String extension) {
		return String.format(INTERVIEW, UUID.randomUUID(), extension);
	}

	public static String getCaseStudyPath(String extension) {
		return String.format(CASE_STUDY, UUID.randomUUID(), extension);
	}

}
