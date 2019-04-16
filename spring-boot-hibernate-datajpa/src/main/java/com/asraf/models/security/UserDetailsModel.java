package com.asraf.models.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.asraf.entities.User;

public class UserDetailsModel implements UserDetails {

	private static final long serialVersionUID = 1L;

	private final User user;

	public UserDetailsModel(final User user) {
		this.user = user;
		this.user.getRoles().size();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return this.user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
				.collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return this.user.getPassword();
	}

	@Override
	public String getUsername() {
		return this.user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
