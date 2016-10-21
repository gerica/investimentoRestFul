package com.invest.repository.rendaVariavel;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.invest.entidade.rendaVariavel.Papel;

public interface PapelRepository extends CrudRepository<Papel, Integer> {
	List<Papel> findByNome(String paramString);

	@Query("SELECT p FROM Papel p ORDER BY p.papel asc")
	List<Papel> findAllOrderByPapel();

	Papel findById(Integer paramInteger);

	List<Papel> findAllByAtivo(Boolean paramBoolean);

	List<Papel> findBySetor(Integer setor);

	Papel findByNomeAndPapel(String nome, String papel);
}
