package com.invest.service.rendaVariavel.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.invest.entidade.rendaVariavel.ConfiguracaoAnaliseCotacoes;
import com.invest.entidade.rendaVariavel.Cotacao;
import com.invest.entidade.rendaVariavel.Papel;
import com.invest.execao.InvestimentoBusinessException;
import com.invest.repository.rendaVariavel.CotacaoRepository;
import com.invest.service.HTMLParserService;
import com.invest.service.rendaVariavel.ConfiguracaoAnaliseCotacoesService;
import com.invest.service.rendaVariavel.CotacaoService;
import com.invest.service.rendaVariavel.PapelService;
import com.invest.service.rendaVariavel.dto.CotacaoGraficoDTO;
import com.invest.service.rendaVariavel.dto.CotacaoTendenciaDTO;

@Service
public class CotacaoServiceImpl implements CotacaoService {
	private class CalcularTendenciaAlta {
		private Integer diasCalculo;
		private Integer risco;

		public CalcularTendenciaAlta(Integer dias, Integer risco) {
			this.diasCalculo = dias;
			this.risco = risco;
		}

		@SuppressWarnings("unchecked")
		public void ordernarByDataDesc(List<CotacaoTendenciaDTO> tendencias) {
			// Collections.sort(tendencias, new Comparator() {
			// public int compare(CotacaoTendencia o1, CotacaoTendencia o2) {
			// return o2.getData().compareTo(o1.getData());
			// }
			// });
		}

		public List<CotacaoTendenciaDTO> reduzierQuantidadeCotacao(List<CotacaoTendenciaDTO> tendencias, int qtd) {
			List<CotacaoTendenciaDTO> result = new ArrayList<CotacaoTendenciaDTO>();
			for (int i = 0; i < qtd; i++) {
				if (tendencias.size() > i) {
					result.add((CotacaoTendenciaDTO) tendencias.get(i));
				}
			}
			return result;
		}

		private Double calcularMediaRupturas(CotacaoTendenciaDTO cotacaoTendencia) {
			if ((cotacaoTendencia.getSoma().doubleValue() > 0.0D)
					&& (cotacaoTendencia.getNumeroRupturas().intValue() > 0)) {
				return Double.valueOf(
						cotacaoTendencia.getSoma().doubleValue() / cotacaoTendencia.getNumeroRupturas().intValue());
			}
			return Double.valueOf(0.0D);
		}

		private Integer calcularNumeroRompmento(List<CotacaoTendenciaDTO> tendencias, int indice) {
			Integer soma = Integer.valueOf(0);
			if (tendencias.size() >= this.diasCalculo.intValue()) {
				for (int i = indice - 1; i >= indice - this.diasCalculo.intValue(); i--) {
					if (((CotacaoTendenciaDTO) tendencias.get(i)).getRompeu().booleanValue()) {
						soma = Integer.valueOf(soma.intValue() + 1);
					}
				}
			}
			return soma;
		}

		private Double calcularSoma(List<CotacaoTendenciaDTO> tendencias, int indice) {
			double soma = 0.0D;
			if (tendencias.size() >= this.diasCalculo.intValue()) {
				for (int i = indice - 1; i >= indice - this.diasCalculo.intValue(); i--) {
					soma += ((CotacaoTendenciaDTO) tendencias.get(i)).getRuptura().doubleValue();
				}
			}
			return Double.valueOf(soma);
		}

		private Double calcularStopLoss(CotacaoTendenciaDTO cotacaoTendencia, Cotacao cotacao) {
			if (cotacaoTendencia.getMediaRupturas().doubleValue() > 0.0D) {
				return Double.valueOf(cotacao.getMinima().doubleValue()
						- cotacaoTendencia.getMediaRupturas().doubleValue() * this.risco.intValue());
			}
			return Double.valueOf(0.0D);
		}

		private Double calcularStopWin(CotacaoTendenciaDTO cotacaoTendencia, Cotacao cotacao) {
			if (cotacaoTendencia.getMediaRupturas().doubleValue() > 0.0D) {
				return Double.valueOf(cotacao.getMaxima().doubleValue()
						+ cotacaoTendencia.getMediaRupturas().doubleValue() * this.risco.intValue());
			}
			return Double.valueOf(0.0D);
		}

		private Double calcularValorProtegidoLoss(List<CotacaoTendenciaDTO> tendencias, int indice) {
			if (tendencias.size() >= 3) {
				int i = indice - 1;
				if (i >= indice - 3) {
					CotacaoTendenciaDTO terceiro = (CotacaoTendenciaDTO) tendencias.get(i);
					CotacaoTendenciaDTO segundo = (CotacaoTendenciaDTO) tendencias.get(i - 1);
					CotacaoTendenciaDTO primeiro = (CotacaoTendenciaDTO) tendencias.get(i - 2);
					if ((primeiro.getStop().doubleValue() > 0.0D) && (segundo.getStop().doubleValue() > 0.0D)
							&& (terceiro.getStop().doubleValue() > 0.0D)) {
						Double initMax = Double
								.valueOf(Math.max(primeiro.getStop().doubleValue(), segundo.getStop().doubleValue()));
						return Double.valueOf(Math.max(initMax.doubleValue(), terceiro.getStop().doubleValue()));
					}
				}
			}
			return Double.valueOf(0.0D);
		}

		private Double calcularValorProtegidoWin(List<CotacaoTendenciaDTO> tendencias, int indice) {
			if (tendencias.size() >= 3) {
				int i = indice - 1;
				if (i >= indice - 3) {
					CotacaoTendenciaDTO terceiro = (CotacaoTendenciaDTO) tendencias.get(i);
					CotacaoTendenciaDTO segundo = (CotacaoTendenciaDTO) tendencias.get(i - 1);
					CotacaoTendenciaDTO primeiro = (CotacaoTendenciaDTO) tendencias.get(i - 2);
					if ((primeiro.getWin().doubleValue() > 0.0D) && (segundo.getWin().doubleValue() > 0.0D)
							&& (terceiro.getWin().doubleValue() > 0.0D)) {
						Double initMax = Double
								.valueOf(Math.max(primeiro.getWin().doubleValue(), segundo.getWin().doubleValue()));
						return Double.valueOf(Math.max(initMax.doubleValue(), terceiro.getWin().doubleValue()));
					}
				}
			}
			return Double.valueOf(0.0D);
		}

		private Double calularRupturaBaixa(LinkedList<Cotacao> cotacaos, int indice) {
			if (cotacaos.size() > 1) {
				Cotacao atual = (Cotacao) cotacaos.get(indice - 1);
				Cotacao anterior = (Cotacao) cotacaos.get(indice - 2);
				if (anterior.getMinima().doubleValue() > atual.getMinima().doubleValue()) {
					return Double.valueOf(anterior.getMinima().doubleValue() - atual.getMinima().doubleValue());
				}
			}
			return Double.valueOf(0.0D);
		}

		private boolean verificarRuptura(List<Cotacao> cotacaos, int indice) {
			if (cotacaos.size() > 1) {
				Cotacao atual = (Cotacao) cotacaos.get(indice - 1);
				Cotacao anterior = (Cotacao) cotacaos.get(indice - 2);
				if (atual.getMinima().doubleValue() > anterior.getMinima().doubleValue()) {
					return true;
				}
			}
			return false;
		}
	}

	private static final Logger logger = LoggerFactory.getLogger(CotacaoServiceImpl.class);

	@Autowired
	private CotacaoRepository cotacaoRepository;

	@Autowired
	private HTMLParserService htmlParser;

	@Autowired
	private PapelService papelService;

	@Autowired
	private ConfiguracaoAnaliseCotacoesService analiseCotacoesService;

	@Override
	public List<CotacaoTendenciaDTO> analizarAltaStopLoss(Papel papel) throws InvestimentoBusinessException {
		ConfiguracaoAnaliseCotacoes configuracao = this.analiseCotacoesService.findByUsuario();
		List<CotacaoTendenciaDTO> tendencias = new ArrayList<CotacaoTendenciaDTO>();
		List<Cotacao> cotacoes = this.cotacaoRepository.findAllByPapelOrderByDataAsc(papel);
		if (!cotacoes.isEmpty()) {
			LinkedList<Cotacao> cotacaoTemp = new LinkedList();

			int indice = 0;
			CalcularTendenciaAlta alta = new CalcularTendenciaAlta(configuracao.getQtdDiasCalculoStopLoss(),
					configuracao.getRiscoStopLoss());
			for (Cotacao cotacao : cotacoes) {
				CotacaoTendenciaDTO cotacaoTendencia = new CotacaoTendenciaDTO();
				cotacaoTemp.add(cotacao);
				tendencias.add(cotacaoTendencia);
				indice++;
				cotacaoTendencia.setId(cotacao.getId());
				cotacaoTendencia.setData(cotacao.getData());
				cotacaoTendencia.setRuptura(alta.calularRupturaBaixa(cotacaoTemp, indice));
				cotacaoTendencia.setSoma(alta.calcularSoma(tendencias, indice));
				cotacaoTendencia.setRompeu(Boolean.valueOf(alta.verificarRuptura(cotacaoTemp, indice)));
				cotacaoTendencia.setNumeroRupturas(alta.calcularNumeroRompmento(tendencias, indice));
				cotacaoTendencia.setMediaRupturas(alta.calcularMediaRupturas(cotacaoTendencia));
				cotacaoTendencia.setStop(alta.calcularStopLoss(cotacaoTendencia, cotacao));
				cotacaoTendencia.setValorProtegido(alta.calcularValorProtegidoLoss(tendencias, indice));
			}
			alta.ordernarByDataDesc(tendencias);
			tendencias = alta.reduzierQuantidadeCotacao(tendencias,
					configuracao.getQtdDiasApresentarCotacoes().intValue());
		}
		return tendencias;
	}

	@Override
	public List<CotacaoTendenciaDTO> analizarAltaStopWin(Papel papel) throws InvestimentoBusinessException {
		ConfiguracaoAnaliseCotacoes configuracao = this.analiseCotacoesService.findByUsuario();
		List<CotacaoTendenciaDTO> tendencias = new ArrayList<CotacaoTendenciaDTO>();
		List<Cotacao> cotacoes = this.cotacaoRepository.findAllByPapelOrderByDataAsc(papel);
		if (!cotacoes.isEmpty()) {
			LinkedList<Cotacao> cotacaoTemp = new LinkedList();

			int indice = 0;
			CalcularTendenciaAlta alta = new CalcularTendenciaAlta(configuracao.getQtdDiasCalculoStopWin(),
					configuracao.getRiscoStopWin());
			for (Cotacao cotacao : cotacoes) {
				CotacaoTendenciaDTO cotacaoTendencia = new CotacaoTendenciaDTO();
				cotacaoTemp.add(cotacao);
				tendencias.add(cotacaoTendencia);
				indice++;
				cotacaoTendencia.setId(cotacao.getId());
				cotacaoTendencia.setData(cotacao.getData());
				cotacaoTendencia.setRuptura(alta.calularRupturaBaixa(cotacaoTemp, indice));
				cotacaoTendencia.setSoma(alta.calcularSoma(tendencias, indice));
				cotacaoTendencia.setRompeu(Boolean.valueOf(alta.verificarRuptura(cotacaoTemp, indice)));
				cotacaoTendencia.setNumeroRupturas(alta.calcularNumeroRompmento(tendencias, indice));
				cotacaoTendencia.setMediaRupturas(alta.calcularMediaRupturas(cotacaoTendencia));
				cotacaoTendencia.setWin(alta.calcularStopWin(cotacaoTendencia, cotacao));
				cotacaoTendencia.setValorProtegido(alta.calcularValorProtegidoWin(tendencias, indice));
			}
			alta.ordernarByDataDesc(tendencias);
			tendencias = alta.reduzierQuantidadeCotacao(tendencias,
					configuracao.getQtdDiasApresentarCotacoes().intValue());
		}
		return tendencias;
	}

	@Override
	public void atualizarAtualBMF() throws InvestimentoBusinessException {
		List<Papel> papeis = papelService.findAllByAtivo();
		for (Papel papel : papeis) {
			Cotacao cotacao = this.htmlParser.lerCotacaoAtual(papel.getPapel());
			if (cotacao != null) {
				Cotacao cotacaoBanco = findByDataAndPapel(cotacao.getData(), papel);
				if (cotacaoBanco != null) {
					cotacaoBanco.setAbertura(cotacao.getAbertura());
					cotacaoBanco.setFechamento(cotacao.getFechamento());
					cotacaoBanco.setMaxima(cotacao.getMaxima());
					cotacaoBanco.setMinima(cotacao.getMinima());

					salvar(cotacao);
				} else {
					cotacao.setPapel(papel);
					salvar(cotacao);

				}
			}
		}
	}

	public void atualizarAtualBMF(Papel papel) throws InvestimentoBusinessException {

		Cotacao cotacao = this.htmlParser.lerCotacaoAtual(papel.getPapel());
		if (cotacao != null) {
			cotacao.setPapel(papel);
			salvar(cotacao);
		}

	}

	@Override
	public void atualizarHistoricoBMF() throws InvestimentoBusinessException {
		List<Papel> papeis = papelService.findAllByAtivo();
		for (Papel papel : papeis) {
			List<Cotacao> cotacoes = this.htmlParser.lerCotacoesHistorica(papel.getPapel());
			if (!cotacoes.isEmpty()) {
				for (Cotacao c : cotacoes) {
					c.setPapel(papel);
					salvar(c);
				}
			}
		}
	}

	@Override
	public void atualizarHistoricoBMF(Papel papel) throws InvestimentoBusinessException {
		List<Cotacao> cotacoes = this.htmlParser.lerCotacoesHistorica(papel.getPapel());
		if (!cotacoes.isEmpty()) {
			for (Cotacao c : cotacoes) {
				c.setPapel(papel);
				salvar(c);
			}
		}

	}

	@Override
	public List<Cotacao> findAllByPapelOrderByDataDesc(Papel papel) {
		return this.cotacaoRepository.findAllByPapelOrderByDataDesc(papel);
	}

	@Override
	public Cotacao findByDataAndPapel(Date data, Papel papel) throws InvestimentoBusinessException {
		List<Cotacao> cotacoes = this.cotacaoRepository.findByDataAndPapel(data, papel);
		if (cotacoes.size() > 1) {
			throw new InvestimentoBusinessException("Existe duas ou mais cota��es cadastada para esse dia: ");
		}
		if (cotacoes.size() == 1) {
			return (Cotacao) cotacoes.get(0);
		}
		return null;
	}

	@Override
	public List<CotacaoGraficoDTO> findCotacaoPorPapel(Integer idPapel) throws InvestimentoBusinessException {
		ConfiguracaoAnaliseCotacoes configuracao = this.analiseCotacoesService.findByUsuario();
		Papel papel = new Papel();
		papel.setId(idPapel);
		List<Cotacao> listCotacao = cotacaoRepository.findByPapelOrderByDataDesc(papel,
				new PageRequest(0, configuracao.getQtdDiasApresentarCotacoes()));

		CotacaoGraficoDTO dto = null;
		List<CotacaoGraficoDTO> result = new ArrayList<CotacaoGraficoDTO>();
		for (Cotacao cotacao : listCotacao) {
			dto = new CotacaoGraficoDTO();
			dto.setData(cotacao.getData());
			dto.setFechamento(cotacao.getFechamento());
			result.add(dto);
		}
		return result;
	}

	@Override
	public Cotacao findUltimaCotacaoByPapel(Papel papel) {
		return this.cotacaoRepository.findUltimaCotacaoByPapel(papel, papel);
	}

	@Override
	public CotacaoTendenciaDTO getUltimoValorTendencia(Papel papel) throws InvestimentoBusinessException {
		List<CotacaoTendenciaDTO> list = analizarAltaStopLoss(papel);
		if (!list.isEmpty()) {
			return (CotacaoTendenciaDTO) list.iterator().next();
		}
		return null;
	}

	private void salvar(Cotacao cotacao) throws InvestimentoBusinessException {
		logger.debug("salvar");
		cotacao = validar(cotacao);
		this.cotacaoRepository.save(cotacao);
	}

	private Cotacao validar(Cotacao cotacao) throws InvestimentoBusinessException {
		logger.debug("validar carteira");

		List<Cotacao> cotacoes = this.cotacaoRepository.findByDataAndPapel(cotacao.getData(), cotacao.getPapel());
		if (cotacoes.size() > 1) {
			throw new InvestimentoBusinessException("Existe duas ou mais cotações cadastada para esse dia: ");
		} else if (cotacoes.size() == 1) {
			Cotacao cotacaoBd = cotacoes.get(0);
			cotacaoBd.setAbertura(cotacao.getAbertura());
			cotacaoBd.setFechamento(cotacao.getFechamento());
			cotacaoBd.setMaxima(cotacao.getMaxima());
			cotacaoBd.setMinima(cotacao.getMinima());
			return cotacaoBd;
		}
		return cotacao;

		// if (cotacoes.size() == 1 && cotacao.getData().before(new Date())) {
		// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// throw new InvestimentoBusinessException(
		// "Cotacao já cadastrada para essa data: " +
		// dateFormat.format(cotacao.getData()));
		// }
	}
}
