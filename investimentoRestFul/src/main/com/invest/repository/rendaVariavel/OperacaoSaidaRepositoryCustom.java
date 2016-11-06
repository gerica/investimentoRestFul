package com.invest.repository.rendaVariavel;

import java.util.List;

public interface OperacaoSaidaRepositoryCustom {

	List<Integer> recuperarAnoMesPapel(Integer ano, Integer mes, Integer idPapel);

}
