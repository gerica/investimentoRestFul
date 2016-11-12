package com.invest.service.rendaVariavel;

import java.util.Date;
import java.util.List;

import com.invest.entidade.rendaVariavel.OperacaoEntrada;
import com.invest.execao.InvestimentoBusinessException;
import com.invest.service.rendaVariavel.dto.HistoricoRendaVariavelDTO;

public interface OperacaoEntradaService {

	void avaliarEntrada(Integer idOperacao) throws InvestimentoBusinessException;
	
	void excluir(OperacaoEntrada operacao) throws InvestimentoBusinessException;

	void fechar(OperacaoEntrada entrada);

	List<HistoricoRendaVariavelDTO> findAllOperacao();

	List<OperacaoEntrada> findAllOperacaoAtiva();

	List<OperacaoEntrada> findByDataLessThanEqualAndAtivo(Date dataMaior);

	List<OperacaoEntrada> findByDataLessThanEqualAndDataGreaterThanEqual(Date dataMaior, Date dataMenor);

	OperacaoEntrada findById(Integer idOperacao);

	List<OperacaoEntrada> findByData(Date data);

	void inativar(Integer idOperacao) throws InvestimentoBusinessException;

	void salvar(OperacaoEntrada operacao) throws InvestimentoBusinessException;

	void salvarSemRegra(OperacaoEntrada operacao) throws InvestimentoBusinessException;

}