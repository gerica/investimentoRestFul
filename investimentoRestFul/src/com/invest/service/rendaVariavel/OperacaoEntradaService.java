package com.invest.service.rendaVariavel;

import java.util.Date;
import java.util.List;

import com.invest.entidade.rendaVariavel.OperacaoEntrada;
import com.invest.execao.InvestimentoBusinessException;
import com.invest.service.rendaVariavel.dto.HistoricoRendaVariavelDTO;

public interface OperacaoEntradaService {

	void salvar(OperacaoEntrada operacao) throws InvestimentoBusinessException;

	void inativar(Integer idOperacao) throws InvestimentoBusinessException;

	List<OperacaoEntrada> findAllOperacaoAtiva();

	void avaliarEntrada(Integer idOperacao) throws InvestimentoBusinessException;

	OperacaoEntrada findById(Integer idOperacao);

	void fechar(OperacaoEntrada entrada);

	List<HistoricoRendaVariavelDTO> findAllOperacao();

	List<OperacaoEntrada> findByDataLessThanEqualAndDataGreaterThanEqual(Date dataMaior, Date dataMenor);

	List<OperacaoEntrada> findByDataLessThanEqualAndAtivo(Date dataMaior);

	void excluir(OperacaoEntrada operacao) throws InvestimentoBusinessException;

}