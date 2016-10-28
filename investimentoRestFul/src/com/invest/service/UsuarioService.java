package com.invest.service;

import com.invest.entidade.Usuario;
import com.invest.execao.InvestimentoBusinessException;

public interface UsuarioService {

	void registar(Usuario usuario) throws InvestimentoBusinessException;

	String getPasswordEnconding(String password) throws InvestimentoBusinessException;

	Usuario findByEmail(String email) throws InvestimentoBusinessException;

}
