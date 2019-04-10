package com.asraf.constants;

public enum UserRole {

	ANONYMOUS("ROLE_ANONYMOUS"), ADMIN("ROLE_ADMIN");

	private final String authority;

	UserRole(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

}
