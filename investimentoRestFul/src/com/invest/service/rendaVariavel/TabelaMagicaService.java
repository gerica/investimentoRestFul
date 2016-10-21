package com.invest.service.rendaVariavel;

import java.util.List;

import com.invest.entidade.rendaVariavel.Papel;
import com.invest.entidade.rendaVariavel.SetorEnum;
import com.invest.execao.InvestimentoBusinessException;

public interface TabelaMagicaService {

	public List<Papel> analizarPapeis(List<Papel> papeis) throws InvestimentoBusinessException;

	public List<Papel> findBySetor(SetorEnum setor) throws InvestimentoBusinessException;

}
