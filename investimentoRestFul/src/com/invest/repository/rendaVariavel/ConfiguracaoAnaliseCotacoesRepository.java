package com.invest.repository.rendaVariavel;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.invest.entidade.Usuario;
import com.invest.entidade.rendaVariavel.ConfiguracaoAnaliseCotacoes;

public abstract interface ConfiguracaoAnaliseCotacoesRepository extends PagingAndSortingRepository<ConfiguracaoAnaliseCotacoes, Integer> {
	public abstract ConfiguracaoAnaliseCotacoes findByUsuario(Usuario paramUsuario);
}
