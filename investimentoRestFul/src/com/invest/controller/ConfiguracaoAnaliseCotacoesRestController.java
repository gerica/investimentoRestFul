package com.invest.controller;

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
import com.invest.entidade.Usuario;
import com.invest.entidade.permissao.RoleEnum;
import com.invest.entidade.rendaVariavel.ConfiguracaoAnaliseCotacoes;
import com.invest.execao.InvestimentoBusinessException;
import com.invest.service.rendaVariavel.ConfiguracaoAnaliseCotacoesService;

@RestController
public class ConfiguracaoAnaliseCotacoesRestController {
	private static final Logger logger = LoggerFactory.getLogger(ConfiguracaoAnaliseCotacoesRestController.class);

	@Autowired
	private ConfiguracaoAnaliseCotacoesService configuracaoAnaliseCotacoesService;

	@RequestMapping(method = RequestMethod.GET, value = UriConstInvestimento.URI_RECUPERAR_CONFIGURACAO_ANALISE)
	@RolesAllowed({ RoleEnum.Constants.ROLE_ADMIN, RoleEnum.Constants.ROLE_CONVIDADO })
	@ResponseBody
	public ResponseEntity<?> recuperarConfiguracaoAnalise() {
		logger.info("ConfiguracaoAnaliseCotacoesRestController.recuperarConfiguracaoAnalise()");
		ConfiguracaoAnaliseCotacoes configuracao = null;
		try {
			configuracao = configuracaoAnaliseCotacoesService.findByUsuario();
		} catch (InvestimentoBusinessException e) {
			ErrorResponse error = new ErrorResponse(e.getMessage());
			return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
		}

		SuccessResponse success = new SuccessResponse("Operação realizada com sucesso", configuracao);
		return new ResponseEntity<SuccessResponse>(success, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.POST, value = UriConstInvestimento.URI_SALVAR_CONFIGURACAO_ANALISE)
	@RolesAllowed({ RoleEnum.Constants.ROLE_ADMIN, RoleEnum.Constants.ROLE_CONVIDADO })
	@ResponseBody
	public ResponseEntity<? extends AbstractResponse> salvarConfiguracaoAnalise(
			@RequestBody ConfiguracaoAnaliseCotacoes configuracao) {
		logger.info("PapelRestController.ativarPapel()");

		try {
			configuracaoAnaliseCotacoesService.gravar(configuracao);
		} catch (InvestimentoBusinessException e) {
			ErrorResponse error = new ErrorResponse(e.getMessage());
			return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
		}

		SuccessResponse success = new SuccessResponse("Operação realizada com sucesso", configuracao);
		return new ResponseEntity<SuccessResponse>(success, HttpStatus.OK);

	}

}
