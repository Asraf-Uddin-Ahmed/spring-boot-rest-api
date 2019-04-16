package com.asraf.constants;

public interface ScopeTypes {
	final String READ = "#oauth2.hasScope('read')";
	final String WRITE = "#oauth2.hasScope('write')";
	final String DELETE = "#oauth2.hasScope('delete')";
}
