package com.asraf.rsrc.utils;

import com.asraf.rsrc.exceptions.ResourceNotFoundException;

public final class ExceptionPreconditions {

	private ExceptionPreconditions() {
		throw new AssertionError();
	}

	/**
	 * @param resource
	 *            has value null if not found
	 * @param clazz
	 *            class type of resource
	 * @param searchParamsMap
	 *            sequence of parameter-value string
	 * @throws ResourceNotFoundException
	 *             if resource is null, means value not found
	 * @return resource
	 */
	public static <T> T checkFound(final T resource, final Class<T> clazz, String... searchParamsMap) {
		if (resource == null) {
			entityNotFound(clazz, searchParamsMap);
		}
		return resource;
	}

	/**
	 * @param clazz
	 *            class type of resource
	 * @param searchParamsMap
	 *            sequence of parameter-value string
	 * @throws ResourceNotFoundException
	 *             if resource is null, means value not found
	 */
	public static <T> T entityNotFound(final Class<T> clazz, String... searchParamsMap)
			throws ResourceNotFoundException {
		throw new ResourceNotFoundException(clazz, searchParamsMap);
	}
}