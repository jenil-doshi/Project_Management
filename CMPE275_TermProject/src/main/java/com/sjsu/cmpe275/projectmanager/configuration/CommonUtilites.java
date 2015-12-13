package com.sjsu.cmpe275.projectmanager.configuration;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class CommonUtilites {

	@SuppressWarnings("unchecked")
	public static String getRole() {
		String role = null;
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext()
				.getAuthentication().getAuthorities();
		for (GrantedAuthority authority : authorities) {
			if (authority.getAuthority().equalsIgnoreCase(Constants.ROLE_ADMIN)) {
				role = "role_admin";
				break;
			} else if (authority.getAuthority().equalsIgnoreCase(Constants.ROLE_USER)) {
				role = "role_user";
				break;
			}
		}
		return role;

	}
}
