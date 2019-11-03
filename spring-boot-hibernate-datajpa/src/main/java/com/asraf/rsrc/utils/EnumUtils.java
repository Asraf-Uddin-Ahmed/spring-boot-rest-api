package com.asraf.rsrc.utils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class EnumUtils {

	public static List<String> getNames(Class<? extends Enum<?>> enumClazz) {
		return Arrays.stream(enumClazz.getEnumConstants()).map(Enum::name).collect(Collectors.toList());
	}

	public static Map<String, Object> getNameValues(Class<? extends Enum<?>> enumClazz) {
		String[] names = Arrays.stream(enumClazz.getEnumConstants()).map(Enum::name).toArray(String[]::new);
		Object[] ordinals = Arrays.stream(enumClazz.getEnumConstants()).map(Enum::ordinal).toArray(Object[]::new);
		Map<String, Object> nameValues = new LinkedHashMap<>();
		for (int I = 0; I < names.length; I++)
			nameValues.put(names[I], ordinals[I]);
		return nameValues;
	}

	@SuppressWarnings("unchecked")
	public static List<String> getNames(String enumClassName) throws ClassNotFoundException {
		Class<?> enumClass = Class.forName(enumClassName);
		return getNames((Class<? extends Enum<?>>) enumClass);
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getNameValues(String enumClassName) throws ClassNotFoundException {
		Class<?> enumClass = Class.forName(enumClassName);
		return getNameValues((Class<? extends Enum<?>>) enumClass);
	}

}
