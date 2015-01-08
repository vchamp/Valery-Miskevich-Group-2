package com.epam.trn.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.epam.trn.dao.UserDao;
import com.epam.trn.model.User;
import com.epam.trn.model.UserRole;

public class AuthService implements UserDetailsService, Serializable {

	private static final long serialVersionUID = -4807898101977350488L;
	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		//TODO: find by login AND password, koz if there is no such login then we will see unexpected exception
		User user = userDao.findByLogin(login);
		UserDetails userDetails = null;
		if (user != null) {
			userDetails = new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), getGrantedAuthority(user));
		}
		return userDetails;
	}

	private Collection<GrantedAuthority> getGrantedAuthority(User user) {
		Set<GrantedAuthority> result = new HashSet<GrantedAuthority>();
		for (UserRole role : user.getRoles()) {
			result.add(new SimpleGrantedAuthority(role.getName()));
			user.addRole(role);
		}
		return result;
	}

	public static boolean hasAdminRole() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if ("ROLE_ADMIN".equals(grantedAuthority.getAuthority()))
				return true;
		}
		return false;
	}
}
