package com.invest.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.invest.entidade.Usuario;
import com.invest.security.model.SpringSecurityUser;
import com.invest.service.UsuarioService;

@Service(value = "userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario appUser = this.usuarioService.loadUserByUsername(username);

		if (appUser == null) {
			throw new UsernameNotFoundException(String.format("No appUser found with username '%s'.", username));
		} else {
			return new SpringSecurityUser(appUser.getId(), appUser.getUsername(), appUser.getPassword(), null, null,
					AuthorityUtils.commaSeparatedStringToAuthorityList(appUser.getAuthorities()));
		}
	}

}
