package com.invest.service;

import com.invest.entidade.Usuario;

public interface UsuarioService {

	Usuario loadUserByUsername(String username);

	long post(Usuario usuario);

	Usuario get(long id);

	Usuario patch(Usuario usuario);

	boolean delete(long id);
}
