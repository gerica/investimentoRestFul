package com.invest.service.rendaVariavel;

import java.util.Date;
import java.util.List;

import com.invest.entidade.rendaVariavel.Cotacao;
import com.invest.entidade.rendaVariavel.Papel;
import com.invest.execao.InvestimentoBusinessException;
import com.invest.service.rendaVariavel.dto.CotacaoTendenciaDTO;

public interface CotacaoService {

	/* Error */
	void cadastrarCotacao(org.springframework.web.multipart.MultipartFile file, Cotacao cotacaoTela) throws InvestimentoBusinessException;

	List<Cotacao> findAllByPapelOrderByDataDesc(Papel papel);

	List<CotacaoTendenciaDTO> analizarAltaStopLoss(Papel papel);

	List<CotacaoTendenciaDTO> analizarAltaStopWin(Papel papel);

	CotacaoTendenciaDTO getUltimoValorTendencia(Papel papel);

	void salvar(Cotacao cotacao) throws InvestimentoBusinessException;

	Cotacao findByDataAndPapel(Date data, Papel papel) throws InvestimentoBusinessException;

	void atualizarBMF(Cotacao cotacao) throws InvestimentoBusinessException;

	void atualizarBMF(Papel papel) throws InvestimentoBusinessException;

	Cotacao findUltimaCotacaoByPapel(Papel papel);

}