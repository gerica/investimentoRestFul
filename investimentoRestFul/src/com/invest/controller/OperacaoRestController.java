package com.invest.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.invest.controller.dto.AbstractResponse;
import com.invest.controller.dto.ErrorResponse;
import com.invest.controller.dto.SuccessResponse;
import com.invest.entidade.permissao.RoleEnum;
import com.invest.entidade.rendaVariavel.OperacaoEntrada;
import com.invest.entidade.rendaVariavel.OperacaoSaida;
import com.invest.execao.InvestimentoBusinessException;
import com.invest.service.rendaVariavel.OperacaoEntradaService;
import com.invest.service.rendaVariavel.OperacaoSaidaService;

@RestController
public class OperacaoRestController {
	private static final Logger logger = LoggerFactory.getLogger(OperacaoRestController.class);

	@Autowired
	private OperacaoEntradaService entradaService;

	@Autowired
	private OperacaoSaidaService saidaService;

	@RequestMapping(method = RequestMethod.POST, value = UriConstInvestimento.URI_SALVAR_OPERACAO_ENTRADA)
	@RolesAllowed({ RoleEnum.Constants.ROLE_ADMIN, RoleEnum.Constants.ROLE_CONVIDADO })
	@ResponseBody
	public ResponseEntity<? extends AbstractResponse> salvarOperacaoEntrada(@RequestBody OperacaoEntrada operacao) {
		logger.info("OperacaoRestController.salvarOperacaoEntrada()");

		try {
			entradaService.salvar(operacao);
		} catch (InvestimentoBusinessException e) {
			ErrorResponse error = new ErrorResponse(e.getMessage());
			return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
		}

		SuccessResponse success = new SuccessResponse("Operação realizada com sucesso");
		return new ResponseEntity<SuccessResponse>(success, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.POST, value = UriConstInvestimento.URI_SALVAR_OPERACAO_SAIDA)
	@RolesAllowed({ RoleEnum.Constants.ROLE_ADMIN, RoleEnum.Constants.ROLE_CONVIDADO })
	@ResponseBody
	public ResponseEntity<? extends AbstractResponse> salvarOperacaoSaida(@RequestBody OperacaoSaida operacao) {
		logger.info("OperacaoRestController.salvarOperacaoSaida()");

		try {
			saidaService.salvar(operacao);
		} catch (InvestimentoBusinessException e) {
			ErrorResponse error = new ErrorResponse(e.getMessage());
			return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
		}

		SuccessResponse success = new SuccessResponse("Operação realizada com sucesso");
		return new ResponseEntity<SuccessResponse>(success, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.POST, value = UriConstInvestimento.URI_EXCLUIR_OPERACAO_ENTRADA)
	@RolesAllowed({ RoleEnum.Constants.ROLE_ADMIN, RoleEnum.Constants.ROLE_CONVIDADO })
	@ResponseBody
	public ResponseEntity<? extends AbstractResponse> excluirOperacaoEntrada(@RequestBody OperacaoEntrada operacao) {
		logger.info("OperacaoRestController.excluirOperacaoEntrada()");
		try {
			entradaService.excluir(operacao);
		} catch (InvestimentoBusinessException e) {
			ErrorResponse error = new ErrorResponse(e.getMessage());
			return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
		}

		SuccessResponse success = new SuccessResponse("Operação realizada com sucesso");
		return new ResponseEntity<SuccessResponse>(success, HttpStatus.OK);

	}
	
	@RequestMapping(method = RequestMethod.POST, value = UriConstInvestimento.URI_EDITAR_OPERACAO_ENTRADA)
	@RolesAllowed({ RoleEnum.Constants.ROLE_ADMIN, RoleEnum.Constants.ROLE_CONVIDADO })
	@ResponseBody
	public ResponseEntity<? extends AbstractResponse> editarOperacaoEntrada(@RequestBody OperacaoEntrada operacao) {
		logger.info("OperacaoRestController.editarOperacaoEntrada()");
		try {
			entradaService.salvar(operacao);
		} catch (InvestimentoBusinessException e) {
			ErrorResponse error = new ErrorResponse(e.getMessage());
			return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
		}
		
		SuccessResponse success = new SuccessResponse("Operação realizada com sucesso");
		return new ResponseEntity<SuccessResponse>(success, HttpStatus.OK);
		
	}

	@RequestMapping(method = RequestMethod.GET, value = UriConstInvestimento.URI_RECUPERAR_OPERACAO_ENTRADA_ABERTA)
	@RolesAllowed({ RoleEnum.Constants.ROLE_ADMIN, RoleEnum.Constants.ROLE_CONVIDADO })
	@ResponseBody
	public List<OperacaoEntrada> recuperarOperacaoEntradaAberta() {
		logger.info("OperacaoRestController.recuperarOperacaoEntradaAberta()");

		return entradaService.findAllOperacaoAtiva();
	}

	@RequestMapping(method = RequestMethod.GET, value = UriConstInvestimento.URI_RECUPERAR_OPERACAO_SAIDA)
	@RolesAllowed({ RoleEnum.Constants.ROLE_ADMIN, RoleEnum.Constants.ROLE_CONVIDADO })
	@ResponseBody
	public List<OperacaoSaida> recuperarOperacaoSaida() {
		logger.info("OperacaoRestController.recuperarOperacaoSaida()");

		return saidaService.findOperacaoSaida();
	}

}
