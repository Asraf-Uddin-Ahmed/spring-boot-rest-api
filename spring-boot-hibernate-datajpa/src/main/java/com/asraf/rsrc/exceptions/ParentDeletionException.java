package com.asraf.rsrc.exceptions;

import java.util.Arrays;
import java.util.List;

public class ParentDeletionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParentDeletionException(String... relatedEntityname) {
		super(ParentDeletionException.generateMessage(toList(relatedEntityname)));
	}
	
	public ParentDeletionException(List<String> relatedEntitynames) {
		super(ParentDeletionException.generateMessage(relatedEntitynames));
	}
	
	private static String generateMessage(List<String> relatedEntityname) {
		return "Entity can not be deleted for related " + relatedEntityname;
	}

	private static List<String> toList(String... entries) {
		return Arrays.asList(entries);
	}

}