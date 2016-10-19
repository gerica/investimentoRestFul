package com.invest.repository.rendaVariavel;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.invest.entidade.rendaVariavel.OperacaoEntrada;
import com.invest.entidade.rendaVariavel.OperacaoSaida;

public abstract interface OperacaoSaidaRepository extends CrudRepository<OperacaoSaida, Integer> {
	public abstract List<OperacaoSaida> findAll();

	public abstract OperacaoSaida findById(Integer paramInteger);

	public abstract OperacaoSaida findAllByAtivo(boolean paramBoolean);

	public abstract List<OperacaoSaida> findByOperacaoEntrada(OperacaoEntrada paramOperacaoEntrada);
}
