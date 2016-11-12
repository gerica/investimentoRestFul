package com.invest.repository.rendaVariavel;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.invest.entidade.rendaVariavel.Cotacao;
import com.invest.entidade.rendaVariavel.OperacaoEntrada;
import com.invest.entidade.rendaVariavel.Papel;

public abstract interface CotacaoRepository extends PagingAndSortingRepository<Cotacao, Integer> {
	public abstract List<Cotacao> findAllByPapelOrderByDataAsc(Papel paramPapel);

	public abstract List<Cotacao> findAllByPapelOrderByDataDesc(Papel paramPapel);

	@Query("SELECT c FROM Cotacao c "
			+ " INNER JOIN c.papel as p,"
			+ " OperacaoEntrada oe "
			+ " WHERE oe.papel = p"
			+ " and oe.ativo = true"
			+ " and oe.quantidade > 0"
			+ "	order by c.data desc")
	public abstract List<Cotacao> findAllCocacaoHasOperacao();

	public abstract Cotacao findByData(Date paramDate);

	public abstract List<Cotacao> findByDataAndPapel(Date paramDate, Papel paramPapel);

	public abstract List<Cotacao> findByPapelOrderByDataDesc(Papel papel, Pageable pageable);

	@Query("SELECT distinct(c) FROM Cotacao c WHERE c.data IN (SELECT max(c1.data) FROM Cotacao c1 WHERE c1.papel = ?2) and c.papel = ?1")
	public abstract Cotacao findUltimaCotacaoByPapel(Papel paramPapel1, Papel paramPapel2);
	
	public abstract List<Cotacao> findByPapelAndData(Papel papel, Date data);
	
	public abstract List<Cotacao> findByPapelAndDataGreaterThan(Papel papel, Date data);
}
