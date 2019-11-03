package com.asraf.rsrc.resources;

import java.util.HashMap;
import java.util.Map;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public abstract class BaseResource extends ResourceSupport {

	private Map<String, Object> properties = new HashMap<>();

	@JsonAnyGetter
	public Map<String, Object> getProperties() {
		return properties;
	}

	protected BaseResource addKeyValuePair(String key, Object value) {
		this.properties.put(key, value);
		return this;
	}

}
