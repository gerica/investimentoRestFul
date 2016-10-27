package com.invest.security.service;

import org.springframework.security.core.AuthenticationException;

import com.invest.entidade.Usuario;
import com.invest.execao.InvestimentoBusinessException;

public interface AuthenticationService {

	public String authentication(String username, String password) throws AuthenticationException, InvestimentoBusinessException;

	public String authenticationRequest(String token);

	Usuario loadUserByUsername(String username);

	long post(Usuario usuario);

	Usuario get(long id);
	
	Usuario get();

	Usuario patch(Usuario usuario);

	boolean delete(long id);

}
