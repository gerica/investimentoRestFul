package com.invest.repository.rendaVariavel;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.invest.entidade.rendaVariavel.Papel;

public interface PapelRepository extends CrudRepository<Papel, Integer> {
	List<Papel> findByNome(String paramString);

	List<Papel> findAll();

	Papel findById(Integer paramInteger);

	List<Papel> findAllByAtivo(Boolean paramBoolean);

	List<Papel> findBySetor(Integer setor);

	Papel findByNomeAndPapel(String nome, String papel);
}
