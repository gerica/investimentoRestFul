package com.invest.service.rendaVariavel;

import java.util.List;

import com.invest.entidade.rendaVariavel.OperacaoEntrada;
import com.invest.entidade.rendaVariavel.OperacaoSaida;
import com.invest.execao.InvestimentoBusinessException;

public interface OperacaoSaidaService {

	OperacaoSaida findById(Integer idOperacao);

	void salvar(OperacaoSaida operacao) throws InvestimentoBusinessException;

	List<OperacaoSaida> findByOperacaoEntrada(OperacaoEntrada operacaoEntrada);
	
	List<OperacaoSaida> findOperacaoSaida();

}