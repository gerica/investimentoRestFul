package com.invest.security.model;

import com.invest.entidade.Usuario;

public class AuthenticationResponse {

	private String token;

	private Usuario usuario;

	public AuthenticationResponse() {
		super();
	}

	public AuthenticationResponse(String token) {
		this.setToken(token);
	}

	public AuthenticationResponse(String token, Usuario usuario) {
		this.setToken(token);
		this.setUsuario(usuario);
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
