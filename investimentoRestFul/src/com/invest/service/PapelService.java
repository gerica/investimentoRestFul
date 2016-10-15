package com.invest.service;

import java.util.List;

import com.invest.entidade.Papel;
import com.invest.entidade.SetorEnum;
import com.invest.execao.InvestimentoBusinessException;

public interface PapelService {

	public List<Papel> analizarPapeis(List<Papel> papeis) throws InvestimentoBusinessException;

	public List<Papel> findBySetor(SetorEnum setor) throws InvestimentoBusinessException;

//	public List<Papel> findAll() throws FundamentoBusinessException;

}
