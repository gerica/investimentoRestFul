package com.invest.service.rendaVariavel.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.invest.entidade.rendaVariavel.Cotacao;
import com.invest.entidade.rendaVariavel.OperacaoEntrada;
import com.invest.entidade.rendaVariavel.OperacaoSaida;
import com.invest.entidade.rendaVariavel.Papel;
import com.invest.execao.InvestimentoBusinessException;
import com.invest.repository.rendaVariavel.OperacaoEntradaRepository;
import com.invest.service.rendaVariavel.CotacaoService;
import com.invest.service.rendaVariavel.OperacaoEntradaService;
import com.invest.service.rendaVariavel.OperacaoSaidaService;
import com.invest.service.rendaVariavel.PapelService;
import com.invest.service.rendaVariavel.dto.CotacaoTendenciaDTO;
import com.invest.service.rendaVariavel.dto.HistoricoRendaVariavelDTO;

@Service
public class OperacaoEntradaServiceImpl implements OperacaoEntradaService {
	
	private static final Logger logger = LoggerFactory.getLogger(OperacaoEntradaServiceImpl.class);
	
	@Autowired
	private OperacaoEntradaRepository operacaoEntradaRepository;
	@Autowired
	private CotacaoService cotacaoService;
	@Autowired
	private PapelService papelService;
	@Autowired
	private OperacaoSaidaService operacaoSaidaService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.invest.service.rendaVariavel.impl.OperacaoEntradaService#salvar(com.
	 * invest.entidade.rendaVariavel.OperacaoEntrada)
	 */
	@Override
//	@Transactional
	public void salvar(OperacaoEntrada operacao) throws InvestimentoBusinessException {
		logger.info("OperacaoEntradaServiceImpl.salvar()");

		Papel papel = this.papelService.findById(operacao.getPapel().getId());
		CotacaoTendenciaDTO cotacaoTendencia = this.cotacaoService.getUltimoValorTendencia(papel);
		if (cotacaoTendencia != null) {
			operacao.setStopLoss(cotacaoTendencia.getStop());
		}
		this.operacaoEntradaRepository.save(operacao);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.invest.service.rendaVariavel.impl.OperacaoEntradaService#inativar(
	 * java.lang.Integer)
	 */
	@Override
	public void inativar(Integer idOperacao) throws InvestimentoBusinessException {
		OperacaoEntrada operacaoEntrada = this.operacaoEntradaRepository.findById(idOperacao);
		operacaoEntrada.setAtivo(Boolean.valueOf(false));
		salvar(operacaoEntrada);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.invest.service.rendaVariavel.impl.OperacaoEntradaService#
	 * findAllOperacaoAtiva()
	 */
	@Override
	public List<OperacaoEntrada> findAllOperacaoAtiva() {
		return this.operacaoEntradaRepository.findAllByAtivoOrderByData(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.invest.service.rendaVariavel.impl.OperacaoEntradaService#
	 * avaliarEntrada(java.lang.Integer)
	 */
	@Override
	public void avaliarEntrada(Integer idOperacao) throws InvestimentoBusinessException {
		OperacaoEntrada operacaoEntrada = this.operacaoEntradaRepository.findById(idOperacao);
		if ("comprar".equals(operacaoEntrada.getTipoOperacao())) {
			avaliarEntradaCompra(operacaoEntrada);
		} else {
			"vender".equals(operacaoEntrada.getTipoOperacao());
		}
	}

	private void avaliarEntradaCompra(OperacaoEntrada operacaoEntrada) throws InvestimentoBusinessException {
		if ((operacaoEntrada.getAvaliacaoEntrada() != null) && (operacaoEntrada.getAvaliacaoEntrada().doubleValue() > 0.0D)) {
			throw new InvestimentoBusinessException("A avalia��o j� foi realizada para esse papel. ");
		}
		Cotacao cotacao = this.cotacaoService.findByDataAndPapel(operacaoEntrada.getData(), operacaoEntrada.getPapel());
		if (cotacao == null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			throw new InvestimentoBusinessException(
					"N�o existe cota��o cadastrada para essa data. " + dateFormat.format(operacaoEntrada.getData()));
		}
		operacaoEntrada.setAvaliacaoEntrada(calcularAvaliacaoEntrada(cotacao, operacaoEntrada));
		this.operacaoEntradaRepository.save(operacaoEntrada);
	}

	private Double calcularAvaliacaoEntrada(Cotacao cotacao, OperacaoEntrada operacaoEntrada) {
		Double maxima = cotacao.getMaxima();
		Double minima = cotacao.getMinima();
		Double precoPago = Double.valueOf(operacaoEntrada.getPrecoUnitario().doubleValue());

		Double qtdDia = Double.valueOf(maxima.doubleValue() - minima.doubleValue());
		Double diferencaPaga = Double.valueOf(precoPago.doubleValue() - minima.doubleValue());

		return Double.valueOf(diferencaPaga.doubleValue() * 100.0D / qtdDia.doubleValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.invest.service.rendaVariavel.impl.OperacaoEntradaService#findById(
	 * java.lang.Integer)
	 */
	@Override
	public OperacaoEntrada findById(Integer idOperacao) {
		return this.operacaoEntradaRepository.findById(idOperacao);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.invest.service.rendaVariavel.impl.OperacaoEntradaService#fechar(com.
	 * invest.entidade.rendaVariavel.OperacaoEntrada)
	 */
	@Override
	public void fechar(OperacaoEntrada entrada) {
		entrada.setAtivo(Boolean.valueOf(false));
		this.operacaoEntradaRepository.save(entrada);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.invest.service.rendaVariavel.impl.OperacaoEntradaService#
	 * findAllOperacao()
	 */
	@Override
	public List<HistoricoRendaVariavelDTO> findAllOperacao() {
		List<HistoricoRendaVariavelDTO> historicos = new ArrayList<HistoricoRendaVariavelDTO>();
		List<OperacaoEntrada> entradas = this.operacaoEntradaRepository.findAllByAtivoOrderByData(false);
		for (OperacaoEntrada entrada : entradas) {
			List<OperacaoSaida> saidas = this.operacaoSaidaService.findByOperacaoEntrada(entrada);
			historicos.add(new HistoricoRendaVariavelDTO(entrada, saidas));
		}
		return historicos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.invest.service.rendaVariavel.impl.OperacaoEntradaService#
	 * findByDataLessThanEqualAndDataGreaterThanEqual(java.util.Date,
	 * java.util.Date)
	 */
	@Override
	public List<OperacaoEntrada> findByDataLessThanEqualAndDataGreaterThanEqual(Date dataMaior, Date dataMenor) {
		return this.operacaoEntradaRepository.findByDataLessThanEqualAndDataGreaterThanEqual(dataMaior, dataMenor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.invest.service.rendaVariavel.impl.OperacaoEntradaService#
	 * findByDataLessThanEqualAndAtivo(java.util.Date)
	 */
	@Override
	public List<OperacaoEntrada> findByDataLessThanEqualAndAtivo(Date dataMaior) {
		return this.operacaoEntradaRepository.findByDataLessThanEqualAndAtivo(dataMaior, true);
	}
}
