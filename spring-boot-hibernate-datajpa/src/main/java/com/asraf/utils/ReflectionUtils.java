package com.asraf.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReflectionUtils {

	public static List<String> getFieldNames(Class<?> aClass) {
		Field[] fields = aClass.getDeclaredFields();
		List<String> fieldNames = new ArrayList<>();
		for (Field field : fields) {
			fieldNames.add(field.getName());
		}
		return fieldNames;
	}

	public static Map<String, String> getFieldNameWithTypes(Class<?> aClass) {
		Field[] fields = aClass.getDeclaredFields();
		Map<String, String> fieldNameWithTypes = new HashMap<>();
		for (Field field : fields) {
			fieldNameWithTypes.put(field.getName(), field.getType().getSimpleName());
		}
		return fieldNameWithTypes;
	}

}
