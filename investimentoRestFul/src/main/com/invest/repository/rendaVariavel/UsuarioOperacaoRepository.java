package com.invest.repository.rendaVariavel;

import org.springframework.data.repository.CrudRepository;

import com.invest.entidade.rendaVariavel.OperacaoEntrada;
import com.invest.entidade.rendaVariavel.UsuarioOperacao;

public interface UsuarioOperacaoRepository extends CrudRepository<UsuarioOperacao, Integer> {

	void deleteByOperacaoEntrada(OperacaoEntrada operacao);

}
