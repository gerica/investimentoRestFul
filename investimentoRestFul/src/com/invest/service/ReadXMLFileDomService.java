package com.invest.service;

import java.util.Date;
import java.util.List;

import com.invest.entidade.rendaVariavel.Cotacao;
import com.invest.execao.InvestimentoBusinessException;

public interface ReadXMLFileDomService {

	List<Cotacao> read(String content) throws InvestimentoBusinessException;

	Date obterData(String dataString);

}