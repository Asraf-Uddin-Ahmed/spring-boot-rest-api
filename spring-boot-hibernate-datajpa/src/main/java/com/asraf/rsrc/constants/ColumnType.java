package com.asraf.rsrc.constants;

public enum ColumnType {

	STRING(String.class), INTEGER(Integer.class), LONG(Long.class), BOOLEAN(Boolean.class), FLOAT(Float.class);

	private final Class<?> clazz;

	private ColumnType(final Class<?> clazz) {
		this.clazz = clazz;
	}

	public Class<?> getTypeClass() {
		return this.clazz;
	}

}
