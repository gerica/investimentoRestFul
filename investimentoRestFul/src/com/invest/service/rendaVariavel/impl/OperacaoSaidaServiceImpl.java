package com.invest.service.rendaVariavel.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invest.entidade.rendaVariavel.OperacaoEntrada;
import com.invest.entidade.rendaVariavel.OperacaoSaida;
import com.invest.execao.InvestimentoBusinessException;
import com.invest.repository.rendaVariavel.OperacaoSaidaRepository;
import com.invest.service.rendaVariavel.OperacaoEntradaService;
import com.invest.service.rendaVariavel.OperacaoSaidaService;

@Service
public class OperacaoSaidaServiceImpl implements OperacaoSaidaService {
	private static final Logger logger = LoggerFactory.getLogger(OperacaoSaidaServiceImpl.class);
	@Autowired
	private OperacaoSaidaRepository operacaoSaidaRepository;
	@Autowired
	private OperacaoEntradaService operacaoEntradaService;

	/* (non-Javadoc)
	 * @see com.invest.service.rendaVariavel.OperacaoSaidaService#findById(java.lang.Integer)
	 */
	@Override
	public OperacaoSaida findById(Integer idOperacao) {
		logger.info("find by id");
		return this.operacaoSaidaRepository.findById(idOperacao);
	}

	/* (non-Javadoc)
	 * @see com.invest.service.rendaVariavel.OperacaoSaidaService#salvar(com.invest.entidade.rendaVariavel.OperacaoSaida)
	 */
	@Override
	public void salvar(OperacaoSaida operacao) throws InvestimentoBusinessException {
		logger.info("salvar operação de saída");
		
		OperacaoEntrada entrada = this.operacaoEntradaService.findById(operacao.getOperacaoEntrada().getId());
		if (operacao.getQuantidade().intValue() > entrada.getQuantidade().intValue()) {
			throw new InvestimentoBusinessException("A quantidade que foi informada é maior que a quantida na operação de entrada.");
		}
		if (operacao.getQuantidade().intValue() < entrada.getQuantidade().intValue()) {
			entrada.setQuantidade(Integer.valueOf(entrada.getQuantidade().intValue() - operacao.getQuantidade().intValue()));
		} else {
			entrada.setQuantidade(Integer.valueOf(entrada.getQuantidade().intValue() - operacao.getQuantidade().intValue()));
			entrada.setAtivo(Boolean.valueOf(false));
		}
		if (entrada.getQuantidade() == 0) {
			this.operacaoEntradaService.fechar(entrada);
		} else {
			this.operacaoEntradaService.salvarSemRegra(entrada);
		}
		this.operacaoSaidaRepository.save(operacao);
	}

	/* (non-Javadoc)
	 * @see com.invest.service.rendaVariavel.OperacaoSaidaService#findByOperacaoEntrada(com.invest.entidade.rendaVariavel.OperacaoEntrada)
	 */
	@Override
	public List<OperacaoSaida> findByOperacaoEntrada(OperacaoEntrada operacaoEntrada) {
		return this.operacaoSaidaRepository.findByOperacaoEntrada(operacaoEntrada);
	}

	@Override
	public List<OperacaoSaida> findOperacaoSaidaFechado() {
		return operacaoSaidaRepository.findOperacaoSaidaFechado();
	}
}
