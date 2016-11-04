package com.invest.service;

import java.util.List;

import com.invest.entidade.rendaVariavel.Cotacao;
import com.invest.execao.InvestimentoBusinessException;

public interface HTMLParserService {

	List<Cotacao> lerCotacoesHistorica(String papel) throws InvestimentoBusinessException;
	
	Cotacao lerCotacaoAtual(String papel) throws InvestimentoBusinessException;

}