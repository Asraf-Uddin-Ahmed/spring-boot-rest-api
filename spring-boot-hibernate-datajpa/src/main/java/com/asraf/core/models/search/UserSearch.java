package com.asraf.core.models.search;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserSearch extends BaseSearch {

	private String email;

	private String name;

}
