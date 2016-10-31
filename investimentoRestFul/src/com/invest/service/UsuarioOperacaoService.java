package com.invest.service;

import com.invest.entidade.Usuario;
import com.invest.entidade.rendaVariavel.OperacaoEntrada;

public interface UsuarioOperacaoService {

	void salvar(OperacaoEntrada operacao, Usuario usuario);

	void excluir(OperacaoEntrada operacao);

}
