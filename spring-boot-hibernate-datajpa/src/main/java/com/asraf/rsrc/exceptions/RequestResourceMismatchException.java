package com.asraf.rsrc.exceptions;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

import com.asraf.rsrc.constants.ErrorCode;

public class RequestResourceMismatchException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RequestResourceMismatchException(Class<?> clazz, String... searchParamsMap) {
		super(RequestResourceMismatchException.generateMessage(clazz.getSimpleName(),
				toMap(String.class, String.class, searchParamsMap)));
	}

	private static String generateMessage(String entity, Map<String, String> searchParams) {
		return "Request " + StringUtils.capitalize(entity) + " mismatch for parameters " + searchParams;
	}

	private static <K, V> Map<K, V> toMap(Class<K> keyType, Class<V> valueType, String... entries) {
		if (entries.length % 2 == 1)
			throw new IllegalArgumentException(ErrorCode.Exception.Resource.RequestMismatch.ILLEGAL_ARGUMENT);
		return IntStream.range(0, entries.length / 2).map(i -> i * 2).collect(HashMap::new,
				(m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])), Map::putAll);
	}

}