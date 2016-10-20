package com.invest.service.rendaVariavel.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invest.entidade.rendaVariavel.Papel;
import com.invest.entidade.rendaVariavel.SetorEnum;
import com.invest.execao.InvestimentoBusinessException;
import com.invest.repository.rendaVariavel.PapelRepository;
import com.invest.service.rendaVariavel.TabelaMagicaService;

@Service("papelService")
public class TabelaMagicaServiceImpl implements TabelaMagicaService {
	private static final Logger logger = LoggerFactory.getLogger(TabelaMagicaServiceImpl.class);

	@Autowired
	private PapelRepository papelRepository;

	public List<Papel> analizarPapeis(List<Papel> papeis) throws InvestimentoBusinessException {
		logger.info("PapelServiceImpl.analizarPapeis()");
		List<Papel> papeisCantidatos = new ArrayList<Papel>();

		for (Papel papel : papeis) {
			// System.out.println(papel);
			if (papel.getFundamento().getP_l() < 1 || papel.getFundamento().getP_l() > 30) {
				continue;
			}
			if (papel.getFundamento().getP_vp() < 0 || papel.getFundamento().getP_vp() > 20) {
				continue;
			}
			if (papel.getFundamento().getRoe() < 0) {
				continue;
			}
			if (papel.getFundamento().getDividentoYIELD() <= 0) {
				continue;
			}
			if (papel.getFundamento().getLiquidezCorrete() < 1) {
				continue;
			}
			if (papel.getFundamento().getMargemEBIT() < 0) {
				continue;
			}
			if (papel.getFundamento().getCrescimento() < 5) {
				continue;
			}
			if (papel.getFundamento().getLiquidez2Meses() < 100000) {
				continue;
			}
			papeisCantidatos.add(papel);

		}
		logger.info("Antes de aplicar as regras:  " + papeis.size());
		logger.info("Depois de aplicar as regras: " + papeisCantidatos.size());

		ordenarPorPL(papeisCantidatos);
		calcularRank(papeisCantidatos);

		ordenarPorPVP(papeisCantidatos);
		calcularRank(papeisCantidatos);

		ordernarPorROE(papeisCantidatos);
		calcularRank(papeisCantidatos);

		ordernarPorDividendoYIELD(papeisCantidatos);
		calcularRank(papeisCantidatos);

		ordernarPorLiquidezCorrente(papeisCantidatos);
		calcularRank(papeisCantidatos);

		ordernarPorMargemEBIT(papeisCantidatos);
		calcularRank(papeisCantidatos);

		// ordernarPorCresimento(papeisCantidatos);
		// calcularRank(papeisCantidatos);
		//
		// ordernarPorLiquides2Meses(papeisCantidatos);
		// calcularRank(papeisCantidatos);

		ordenarPorRank(papeisCantidatos);
		organizarRank(papeisCantidatos);

		return papeisCantidatos;

	}

	@Override
	public List<Papel> findBySetor(SetorEnum setor) throws InvestimentoBusinessException {
		logger.info("PapelServiceImpl.findBySetor() " + setor.getDesc());
		List<Papel> papeis = null;
		if (SetorEnum.TODOS.equals(setor)) {
			papeis = (List<Papel>) papelRepository.findAll();
		} else {
			papeis = (List<Papel>) papelRepository.findBySetor(setor.getId());
		}

		return papeis;

	}

	private void organizarRank(List<Papel> papeisCantidatos) {
		for (int i = 0; i < papeisCantidatos.size(); i++) {
			papeisCantidatos.get(i).setRank(i + 1);
		}

	}

	private void calcularRank(List<Papel> papeisCantidatos) {
		for (int i = 0; i < papeisCantidatos.size(); i++) {
			Papel papel = papeisCantidatos.get(i);
			papel.setRank(papel.getRank() + i);
		}

	}

	private void ordenarPorRank(List<Papel> papeisCantidatos) {
		Collections.sort(papeisCantidatos, new Comparator<Papel>() {

			@Override
			public int compare(Papel p1, Papel p2) {
				return p1.getRank().compareTo(p2.getRank());
			}

		});

	}

	/**
	 * QUANTO MAIOR MELHOR
	 * 
	 * @param papeisCantidatos
	 */
	private void ordernarPorLiquides2Meses(List<Papel> papeisCantidatos) {
		Collections.sort(papeisCantidatos, new Comparator<Papel>() {

			@Override
			public int compare(Papel p1, Papel p2) {
				return p2.getFundamento().getLiquidez2Meses().compareTo(p1.getFundamento().getLiquidez2Meses());
			}

		});

	}

	/**
	 * Regra maior que 5%, QUANTO MAIOR MELHOR
	 * 
	 * @param papeisCantidatos
	 */
	private void ordernarPorCresimento(List<Papel> papeisCantidatos) {
		Collections.sort(papeisCantidatos, new Comparator<Papel>() {

			@Override
			public int compare(Papel p1, Papel p2) {
				return p2.getFundamento().getCrescimento().compareTo(p1.getFundamento().getCrescimento());
			}

		});

	}

	/**
	 * Regra TER� QUE SER >0, QUANTO MAIOR MELHOR
	 * 
	 * @param papeisCantidatos
	 */
	private void ordernarPorMargemEBIT(List<Papel> papeisCantidatos) {
		Collections.sort(papeisCantidatos, new Comparator<Papel>() {

			@Override
			public int compare(Papel p1, Papel p2) {
				return p2.getFundamento().getMargemEBIT().compareTo(p1.getFundamento().getMargemEBIT());
			}

		});

	}

	/**
	 * Regra maior que 1. Quanto MAIOR MELHOR
	 * 
	 * @param papeisCantidatos
	 */
	private void ordernarPorLiquidezCorrente(List<Papel> papeisCantidatos) {
		Collections.sort(papeisCantidatos, new Comparator<Papel>() {

			@Override
			public int compare(Papel p1, Papel p2) {
				return p2.getFundamento().getLiquidezCorrete().compareTo(p1.getFundamento().getLiquidezCorrete());
			}

		});
	}

	/**
	 * Regra,maior que 0% e quanto MAIOR MELHOR
	 * 
	 * @param papeisCantidatos
	 */
	private void ordernarPorROE(List<Papel> papeisCantidatos) {
		Collections.sort(papeisCantidatos, new Comparator<Papel>() {

			@Override
			public int compare(Papel p1, Papel p2) {
				return p2.getFundamento().getRoe().compareTo(p1.getFundamento().getRoe());
			}

		});

	}

	/**
	 * Regra, maior que 0. Quanto MAIOR MELHOR
	 * 
	 * @param papeisCantidatos
	 */
	private void ordernarPorDividendoYIELD(List<Papel> papeisCantidatos) {
		Collections.sort(papeisCantidatos, new Comparator<Papel>() {

			@Override
			public int compare(Papel p1, Papel p2) {
				return p2.getFundamento().getDividentoYIELD().compareTo(p1.getFundamento().getDividentoYIELD());
			}

		});

	}

	/**
	 * O valor estar� entre entre 0 e 20 e quanto MENOR MELHOR
	 * 
	 * @param papeisCantidatos
	 */
	private void ordenarPorPVP(List<Papel> papeisCantidatos) {
		Collections.sort(papeisCantidatos, new Comparator<Papel>() {

			@Override
			public int compare(Papel p1, Papel p2) {
				return p1.getFundamento().getP_vp().compareTo(p2.getFundamento().getP_vp());
			}

		});
	}

	/**
	 * O valor estrar� entre entre 1 e 30, e quanto MENOR MELHOR
	 * 
	 * @param papeisCantidatos
	 */
	private void ordenarPorPL(List<Papel> papeisCantidatos) {
		Collections.sort(papeisCantidatos, new Comparator<Papel>() {

			@Override
			public int compare(Papel p1, Papel p2) {
				return p1.getFundamento().getP_l().compareTo(p2.getFundamento().getP_l());
			}

		});

	}

}
