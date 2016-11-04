package com.invest.service.rendaVariavel;

import com.invest.entidade.Usuario;
import com.invest.entidade.rendaVariavel.ConfiguracaoAnaliseCotacoes;
import com.invest.execao.InvestimentoBusinessException;

public interface ConfiguracaoAnaliseCotacoesService {

	ConfiguracaoAnaliseCotacoes findByUsuario() throws InvestimentoBusinessException;

	void gravar(ConfiguracaoAnaliseCotacoes config) throws InvestimentoBusinessException;

}