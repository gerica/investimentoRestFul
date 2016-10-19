package com.invest.service.rendaVariavel.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invest.entidade.rendaVariavel.Cotacao;
import com.invest.entidade.rendaVariavel.OperacaoEntrada;
import com.invest.entidade.rendaVariavel.Papel;
import com.invest.execao.InvestimentoBusinessException;
import com.invest.repository.rendaVariavel.PapelRepository;
import com.invest.service.rendaVariavel.CotacaoService;
import com.invest.service.rendaVariavel.OperacaoEntradaService;
import com.invest.service.rendaVariavel.PapelService;
import com.invest.service.rendaVariavel.dto.BalancoCarteiraDTO;
import com.invest.util.FormatadorUtil;

@Service
public class PapelServiceImpl implements PapelService {
	private static final Logger logger = LoggerFactory.getLogger(PapelServiceImpl.class);
	@Autowired
	private PapelRepository papelRepository;

	@Autowired
	private CotacaoService cotacaoService;

	@Autowired
	private OperacaoEntradaService operacaoEntradaService;

	/* (non-Javadoc)
	 * @see com.invest.service.rendaVariavel.PapelService#salvar(com.invest.entidade.rendaVariavel.Papel)
	 */
	@Override
	public void salvar(Papel papel) throws InvestimentoBusinessException {
		logger.debug("salvar");
		this.validar(papel);
		this.papelRepository.save(papel);
	}

	/* (non-Javadoc)
	 * @see com.invest.service.rendaVariavel.PapelService#findAll()
	 */
	@Override
	public List<Papel> findAll() {
		return this.papelRepository.findAll();
	}

	/* (non-Javadoc)
	 * @see com.invest.service.rendaVariavel.PapelService#findAllByAtivo()
	 */
	@Override
	public List<Papel> findAllByAtivo() {
		return this.papelRepository.findAllByAtivo(Boolean.valueOf(true));
	}

	/* (non-Javadoc)
	 * @see com.invest.service.rendaVariavel.PapelService#findById(java.lang.Integer)
	 */
	@Override
	public Papel findById(Integer idPapel) {
		return this.papelRepository.findById(idPapel);
	}

	/* (non-Javadoc)
	 * @see com.invest.service.rendaVariavel.PapelService#findBalancoHoje()
	 */
	@Override
	public List<BalancoCarteiraDTO> findBalancoHoje() {
		List<OperacaoEntrada> entradas = this.operacaoEntradaService.findAllOperacaoAtiva();
		BalancoCarteiraDTO dto = null;
		ArrayList<BalancoCarteiraDTO> balancos = new ArrayList<BalancoCarteiraDTO>();
		for (OperacaoEntrada entrada : entradas) {
			Cotacao cotacao = this.cotacaoService.findUltimaCotacaoByPapel(entrada.getPapel());
			if (cotacao == null)
				continue;
			dto = new BalancoCarteiraDTO();
			dto.setPapel(entrada.getPapel().getNome());
			dto.setDataInvestimento(entrada.getData());
			dto.setDataUltimaCotacao(cotacao.getData());
			dto.setValorInvestimento(entrada.getPrecoUnitario());
			dto.setValorUltimaCotacao(cotacao.getFechamento());
			dto.setTotalInvestimento(this.calcularTotalInvestimento(entrada));
			dto.setPorcentagemLucroPrejuizo(this.calcularProcentagemLucroPrejuizo(entrada, cotacao));
			dto.setSaldoLucroPrejuizo(this.calcularSaldo(entrada, cotacao));
			dto.setLucroPrejuizo(this.calcularQtdLucroPrejuizo(entrada, cotacao));
			balancos.add(dto);
		}
		return balancos;
	}

	// @Scheduled(cron = "0 0 20 * * ?")
	/* (non-Javadoc)
	 * @see com.invest.service.rendaVariavel.PapelService#schedulerAtualizarCotacao()
	 */
	@Override
	public void schedulerAtualizarCotacao() throws InvestimentoBusinessException {
		List<Papel> papeis = this.papelRepository.findAllByAtivo(Boolean.valueOf(true));
		for (Papel papel : papeis) {
			try {
				this.cotacaoService.atualizarBMF(papel);
				continue;
			} catch (InvestimentoBusinessException e) {
				logger.debug(e.getMessage());
				throw new InvestimentoBusinessException(e.getMessage());
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.invest.service.rendaVariavel.PapelService#inativar(java.lang.Integer)
	 */
	@Override
	public Papel inativar(Integer idPapel) throws InvestimentoBusinessException {
		Papel papel = this.papelRepository.findById(idPapel);
		papel.setAtivo(Boolean.valueOf(false));
		this.salvar(papel);
		return papel;
	}

	private Double calcularSaldo(OperacaoEntrada entrada, Cotacao cotacao) {
		return (double) entrada.getQuantidade().intValue() * cotacao.getFechamento();
	}

	private Double calcularProcentagemLucroPrejuizo(OperacaoEntrada entrada, Cotacao cotacao) {
		Double valorEntrada = (double) entrada.getPrecoUnitario();
		Double valorAtual = cotacao.getFechamento();
		Double diferenca = valorAtual - valorEntrada;
		return diferenca * 100.0 / valorEntrada / 100.0;
	}

	private Double calcularQtdLucroPrejuizo(OperacaoEntrada entrada, Cotacao cotacao) {
		return cotacao.getFechamento() - entrada.getPrecoUnitario();
	}

	private Double calcularTotalInvestimento(OperacaoEntrada entrada) {
		return (double) entrada.getQuantidade().intValue() * entrada.getPrecoUnitario();
	}

	private void validar(Papel papel) throws InvestimentoBusinessException {
		logger.debug("validar carteira");
		List list = this.papelRepository.findByNome(papel.getNome());
		if (!list.isEmpty()) {
			Papel objBanco;
			if (list.size() == 1 && (objBanco = (Papel) list.iterator().next()).getId().equals(papel.getId())) {
				return;
			}
			throw new InvestimentoBusinessException("Nome de Empresa j\u00e1 cadastrado");
		}
	}

	private class CalcularMediaMovelExponecial {
		private static final int QTD_DIAS = 14;
		private List<Double> mmeAnteriores;

		private CalcularMediaMovelExponecial() {
			this.mmeAnteriores = new ArrayList<Double>();
		}

		public String calcularMMeMaximoMinimo(final Cotacao cotacao) {
			Double mme = 0.0;
			final Double valor = cotacao.getFechamento();
			Double mmeAnterior = 0.0;
			if (!this.mmeAnteriores.isEmpty()) {
				mmeAnterior = this.mmeAnteriores.get(this.mmeAnteriores.size() - 1);
			}
			mme = (valor - mmeAnterior) * 0.13333333333333333 + mmeAnterior;
			this.mmeAnteriores.add(mme);
			return FormatadorUtil.formatarMoedaEN(mme);
		}
	}
}