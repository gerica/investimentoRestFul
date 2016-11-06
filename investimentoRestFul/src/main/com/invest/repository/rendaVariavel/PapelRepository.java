package com.invest.repository.rendaVariavel;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.invest.entidade.rendaVariavel.Papel;

public interface PapelRepository extends CrudRepository<Papel, Integer> {
	// @Query("SELECT p FROM Papel p WHERE p.ativo = ?1 ORDER BY p.papel asc")
	List<Papel> findAllByAtivo(Boolean paramBoolean);

	@Query("SELECT p FROM Papel p ORDER BY p.papel asc")
	List<Papel> findAllOrderByPapel();

	Papel findById(Integer paramInteger);

	List<Papel> findByNome(String paramString);

	Papel findByNomeAndPapel(String nome, String papel);

	List<Papel> findBySetor(Integer setor);

	@Query("SELECT distinct(p) FROM Papel p JOIN OperacaoEntrada oe on p.id = oe.papel.id ORDER BY p.papel asc")
	List<Papel> findHasOperacao();
}
