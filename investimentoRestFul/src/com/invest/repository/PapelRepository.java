package com.invest.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.invest.entidade.Papel;

public interface PapelRepository  extends CrudRepository<Papel, Integer> {

	List<Papel> findBySetor(Integer setor);

	Papel findByNomeAndPapel(String nome, String papel);

}
