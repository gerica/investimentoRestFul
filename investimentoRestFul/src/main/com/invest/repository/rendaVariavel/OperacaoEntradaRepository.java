package com.invest.repository.rendaVariavel;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.invest.entidade.rendaVariavel.OperacaoEntrada;

public abstract interface OperacaoEntradaRepository extends CrudRepository<OperacaoEntrada, Integer> {
	public abstract List<OperacaoEntrada> findAll();

	public abstract OperacaoEntrada findById(Integer paramInteger);

	public abstract List<OperacaoEntrada> findAllByAtivoOrderByData(boolean paramBoolean);

	public abstract List<OperacaoEntrada> findByDataLessThanEqualAndDataGreaterThanEqual(Date paramDate1,
			Date paramDate2);

	public abstract List<OperacaoEntrada> findByDataLessThanEqualAndAtivo(Date paramDate, boolean paramBoolean);

	// @Query("SELECT oe FROM OperacaoEntrada oe WHERE oe.ativo = true and
	// oe.quantidade > 0")
	public abstract List<OperacaoEntrada> findByDataGreaterThan(Date data);
	
	public abstract List<OperacaoEntrada> findByData(Date data);

}
