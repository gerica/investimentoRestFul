package com.invest.security.service;

import org.springframework.security.core.AuthenticationException;

import com.invest.execao.InvestimentoBusinessException;

public interface AuthenticationService {

	public String authentication(String username, String password) throws AuthenticationException, InvestimentoBusinessException;

	public String authenticationRequest(String token);

}
