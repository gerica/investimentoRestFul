package com.invest.service.rendaVariavel;

import java.util.List;

import com.invest.entidade.rendaVariavel.Papel;
import com.invest.execao.InvestimentoBusinessException;
import com.invest.service.rendaVariavel.dto.BalancoCarteiraDTO;

public interface PapelService {

	void salvar(Papel papel) throws InvestimentoBusinessException;

	List<Papel> findAll();

	List<Papel> findAllByAtivo();

	Papel findById(Integer idPapel);

	List<BalancoCarteiraDTO> findBalancoHoje() throws InvestimentoBusinessException;

	// @Scheduled(cron = "0 0 20 * * ?")
	void schedulerAtualizarCotacao() throws InvestimentoBusinessException;

	Papel inativar(Integer idPapel) throws InvestimentoBusinessException;

	Papel ativar(Integer idPapel) throws InvestimentoBusinessException;

	void ativarDesativar(Integer idPapel) throws InvestimentoBusinessException;

}