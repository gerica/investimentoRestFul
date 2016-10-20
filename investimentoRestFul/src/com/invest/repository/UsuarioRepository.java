package com.invest.repository;

import org.springframework.data.repository.CrudRepository;

import com.invest.entidade.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

	Usuario findByUsername(String username);

	Usuario findByUsernameAndPassword(String username, String Password);

}
