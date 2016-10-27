package com.invest.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.invest.controller.dto.AbstractResponse;
import com.invest.controller.dto.ErrorResponse;
import com.invest.controller.dto.SuccessResponse;
import com.invest.entidade.rendaVariavel.OperacaoEntrada;
import com.invest.entidade.rendaVariavel.Papel;
import com.invest.execao.InvestimentoBusinessException;
import com.invest.service.rendaVariavel.OperacaoEntradaService;

@RestController
public class OperacaoRestController {
	private static final Logger logger = LoggerFactory.getLogger(OperacaoRestController.class);

	@Autowired
	private OperacaoEntradaService entradaService;

	@RequestMapping(method = RequestMethod.POST, value = UriConstInvestimento.URI_SALVAR_OPERACAO)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@ResponseBody
	public ResponseEntity<? extends AbstractResponse> salvarOperacao(@RequestBody OperacaoEntrada operacao) {
		logger.info("OperacaoRestController.salvarOperacao()");

		try {
			entradaService.salvar(operacao);
		} catch (InvestimentoBusinessException e) {
			ErrorResponse error = new ErrorResponse(e.getMessage());
			return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
		}

		SuccessResponse success = new SuccessResponse("Operação realizada com sucesso");
		return new ResponseEntity<SuccessResponse>(success, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = UriConstInvestimento.URI_RECUPERAR_OPERACAO_ENTRADA)
	// @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@ResponseBody
	public ResponseEntity<OperacaoEntrada> getOperacao() {
		logger.info("OperacaoRestController.salvarOperacao()");
		OperacaoEntrada operacao = new OperacaoEntrada();
		operacao.setData(new Date());
		operacao.setTipoOperacao("Comprar");
		operacao.setPrecoUnitario(15.36);
		operacao.setQuantidade(100);
		operacao.setDespesa(5.65);
		operacao.setObservacao("vamos ver");

		Papel papel = new Papel();
		papel.setNome("CIA HERING");
		papel.setPapel("HGTX3");
		operacao.setPapel(papel);

		return new ResponseEntity<OperacaoEntrada>(operacao, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = UriConstInvestimento.URI_RECUPERAR_TODAS_OPERACAO_ENTRADA)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@ResponseBody
	public List<OperacaoEntrada> getAllOperacaoEntrada() {
		logger.info("OperacaoRestController.getAllOperacaoEntrada()");

		return entradaService.findAllOperacaoAtiva();
	}

}
