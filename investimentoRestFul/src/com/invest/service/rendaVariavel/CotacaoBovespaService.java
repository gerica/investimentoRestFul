package com.invest.service.rendaVariavel;

import com.invest.execao.InvestimentoBusinessException;

public interface CotacaoBovespaService {

	String recuperarAcao(String papel) throws InvestimentoBusinessException;

}