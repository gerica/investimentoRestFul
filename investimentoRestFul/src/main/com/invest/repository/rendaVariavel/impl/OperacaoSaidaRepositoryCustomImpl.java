package com.invest.repository.rendaVariavel.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.invest.repository.rendaVariavel.OperacaoSaidaRepositoryCustom;

@Repository
public class OperacaoSaidaRepositoryCustomImpl implements OperacaoSaidaRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Query
	public List<Integer> recuperarAnoMesPapel(Integer ano, Integer mes, Integer idPapel) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select os.id_operacao_saida from tb_operacao_saida  os ");
		sb.append(" 		join tb_operacao_entrada oe on os.id_operacao_entrada = oe.id_operacao_entrada ");
		sb.append(" where 1=1 ");
		if (ano > 0) {
			sb.append(" and EXTRACT(YEAR FROM os.dt_operacao_saida) = ");
			sb.append(ano);
		}

		if (mes > 0) {
			sb.append(" and EXTRACT(MONTH FROM os.dt_operacao_saida) = ");
			sb.append(mes);
		}
		if (idPapel > 0) {
			sb.append(" and oe.id_papel = ");
			sb.append(idPapel);
		}

		// List<Object> lista =
		// entityManager.createNativeQuery(sb.toString()).getResultList();
		return entityManager.createNativeQuery(sb.toString()).getResultList();

	}

}
