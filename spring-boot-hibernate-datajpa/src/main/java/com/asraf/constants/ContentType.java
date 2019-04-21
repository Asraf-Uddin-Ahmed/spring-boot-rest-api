package com.asraf.constants;

public enum ContentType {

	APPLICATION_JSON("application/json;charset=UTF-8"), APPLICATION_XML("application/xml;charset=UTF-8");

	private final String contentTypeWithCharset;

	ContentType(String contentTypeWithCharset) {
		this.contentTypeWithCharset = contentTypeWithCharset;
	}

	@Override
	public String toString() {
		return this.contentTypeWithCharset;
	}

}
