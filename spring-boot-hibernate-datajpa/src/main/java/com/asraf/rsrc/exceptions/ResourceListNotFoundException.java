package com.asraf.rsrc.exceptions;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class ResourceListNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceListNotFoundException(Class<?> clazz, List<Map<String, Object>> mappedExceptionList) {
		super(ResourceListNotFoundException.generateMessage(clazz.getSimpleName(), mappedExceptionList));
	}

	public ResourceListNotFoundException(Class<?>[] clazzes, List<Map<String, Object>> mappedExceptionList) {
		super(ResourceListNotFoundException.generateMessage(Arrays.asList(clazzes).stream()
				.map(c -> StringUtils.capitalize(c.getSimpleName())).collect(Collectors.toList()),
				mappedExceptionList));
	}

	private static String generateMessage(String entity, List<Map<String, Object>> mappedExceptionList) {
		return StringUtils.capitalize(entity) + " list was not found for parameters " + mappedExceptionList;
	}

	private static String generateMessage(List<String> entities, List<Map<String, Object>> mappedExceptionList) {
		return entities + " list was not found for parameters " + mappedExceptionList;
	}

}