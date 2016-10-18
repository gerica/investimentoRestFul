package com.invest.service.impl;

import org.springframework.stereotype.Service;

import com.invest.entidade.Usuario;

//@Service("usuarioService")
public class UsuarioServiceImpl {

	
	public Usuario loadUserByUsername(String username) {
		Usuario u = new Usuario();
		u.setId(1l);
		u.setNome("rogerio-");
		u.setEmail("rogerio@gmail.com");
		u.setSenha("123");
		u.setAuthorities("todos");
		return u;
	}

}
