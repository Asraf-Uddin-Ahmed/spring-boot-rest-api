package com.asraf.rsrc.exceptions;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class StoragePathNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StoragePathNotFoundException(String... searchParamsMap) {
		super(StoragePathNotFoundException.generateMessage(toMap(String.class, String.class, searchParamsMap)));
	}

	private static String generateMessage(Map<String, String> searchParams) {
		return "Storage path was not found for parameters " + searchParams;
	}

	private static <K, V> Map<K, V> toMap(Class<K> keyType, Class<V> valueType, String... entries) {
		if (entries.length % 2 == 1)
			throw new IllegalArgumentException("Invalid entries");
		return IntStream.range(0, entries.length / 2).map(i -> i * 2).collect(HashMap::new,
				(m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])), Map::putAll);
	}

}