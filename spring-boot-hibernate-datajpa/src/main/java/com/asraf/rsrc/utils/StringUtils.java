package com.asraf.rsrc.utils;

public final class StringUtils {
	public static boolean isNullOrEmpty(String str) {
		return str == null || str.isEmpty();
	}
	public static boolean isNullOrWhitespace(String str) {
		return str == null || str.trim().isEmpty();
	}
}
