package com.invest.service.rendaVariavel;

import java.util.Date;
import java.util.List;

import com.invest.entidade.rendaVariavel.Cotacao;
import com.invest.entidade.rendaVariavel.Papel;
import com.invest.execao.InvestimentoBusinessException;
import com.invest.service.rendaVariavel.dto.CotacaoTendenciaDTO;

public interface CotacaoService {

	List<Cotacao> findAllByPapelOrderByDataDesc(Papel papel);

	List<CotacaoTendenciaDTO> analizarAltaStopLoss(Papel papel) throws InvestimentoBusinessException;

	List<CotacaoTendenciaDTO> analizarAltaStopWin(Papel papel) throws InvestimentoBusinessException;

	CotacaoTendenciaDTO getUltimoValorTendencia(Papel papel) throws InvestimentoBusinessException;

	Cotacao findByDataAndPapel(Date data, Papel papel) throws InvestimentoBusinessException;

	void atualizarHistoricoBMF() throws InvestimentoBusinessException;

	void atualizarHistoricoBMF(Papel papel) throws InvestimentoBusinessException;

	void atualizarAtualBMF() throws InvestimentoBusinessException;

	void atualizarAtualBMF(Papel papel) throws InvestimentoBusinessException;

	Cotacao findUltimaCotacaoByPapel(Papel papel);

}