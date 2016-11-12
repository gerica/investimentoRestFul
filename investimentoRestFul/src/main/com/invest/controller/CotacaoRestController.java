package com.invest.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.invest.controller.dto.ErrorResponse;
import com.invest.controller.dto.SuccessResponse;
import com.invest.entidade.permissao.RoleEnum;
import com.invest.entidade.rendaVariavel.Cotacao;
import com.invest.entidade.rendaVariavel.Papel;
import com.invest.execao.InvestimentoBusinessException;
import com.invest.service.rendaVariavel.CotacaoService;
import com.invest.service.rendaVariavel.dto.CotacaoGraficoDTO;
import com.invest.service.rendaVariavel.dto.HistoricoCarteiraDTO;

@RestController
public class CotacaoRestController {
	private static final Logger logger = LoggerFactory.getLogger(CotacaoRestController.class);

	@Autowired
	private CotacaoService cotacaoService;

	@RequestMapping(method = RequestMethod.GET, value = UriConstInvestimento.URI_ATUALIZAR_HISTORICO_BMF)
	@RolesAllowed({ RoleEnum.Constants.ROLE_ADMIN, RoleEnum.Constants.ROLE_CONVIDADO })
	@ResponseBody
	public ResponseEntity<?> atualizarHistoricoBMF() {
		logger.info("CotacaoRestController.atualizarHistoricoBMF()");
		try {
			cotacaoService.atualizarHistoricoBMF();
		} catch (InvestimentoBusinessException e) {
			ErrorResponse error = new ErrorResponse(e.getMessage());
			return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
		}

		SuccessResponse success = new SuccessResponse("Operação realizada com sucesso");
		return new ResponseEntity<SuccessResponse>(success, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = UriConstInvestimento.URI_ATUALIZAR_ATUAL_BMF)
	@RolesAllowed({ RoleEnum.Constants.ROLE_ADMIN, RoleEnum.Constants.ROLE_CONVIDADO })
	@ResponseBody
	public ResponseEntity<?> atualizarAtualBMF() {
		logger.info("CotacaoRestController.atualizarAtualBMF()");
		try {
			cotacaoService.atualizarAtualBMF();
		} catch (InvestimentoBusinessException e) {
			ErrorResponse error = new ErrorResponse(e.getMessage());
			return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
		}

		SuccessResponse success = new SuccessResponse("Operação realizada com sucesso");
		return new ResponseEntity<SuccessResponse>(success, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = UriConstInvestimento.URI_RECUPERAR_ULTIMA_COTACAO)
	@RolesAllowed({ RoleEnum.Constants.ROLE_ADMIN, RoleEnum.Constants.ROLE_CONVIDADO })
	@ResponseBody
	public ResponseEntity<?> recuperarUltimaCotacao(@PathVariable(value = "idPapel") Integer idPapel) {
		logger.info("CotacaoRestController.recuperarUltimaCotacao()");
		Cotacao cotacao = null;
		try {
			Papel p = new Papel();
			p.setId(idPapel);
			cotacao = cotacaoService.findUltimaCotacaoByPapel(p);
		} catch (InvestimentoBusinessException e) {
			ErrorResponse error = new ErrorResponse(e.getMessage());
			return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
		}

		SuccessResponse success = new SuccessResponse("Operação realizada com sucesso", cotacao);
		return new ResponseEntity<SuccessResponse>(success, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = UriConstInvestimento.URI_RECUPERAR_COTACAO_POR_PAPEL)
	@RolesAllowed({ RoleEnum.Constants.ROLE_ADMIN, RoleEnum.Constants.ROLE_CONVIDADO })
	@ResponseBody
	public ResponseEntity<?> recuperarCotacoesPorPapel(@PathVariable(value = "idPapel") Integer idPapel) {
		logger.info("CotacaoRestController.recuperarCotacoesPorPapel()");
		List<CotacaoGraficoDTO> result = null;
		try {
			result = cotacaoService.findCotacaoPorPapel(idPapel);
		} catch (InvestimentoBusinessException e) {
			ErrorResponse error = new ErrorResponse(e.getMessage());
			return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
		}

		SuccessResponse success = new SuccessResponse("Operação realizada com sucesso", result);
		return new ResponseEntity<SuccessResponse>(success, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = UriConstInvestimento.URI_RECUPERAR_BALANCO_CARTEIRA)
	@RolesAllowed({ RoleEnum.Constants.ROLE_ADMIN, RoleEnum.Constants.ROLE_CONVIDADO })
	@ResponseBody
	public ResponseEntity<?> recuperarBalancoCarteira() {
		logger.info("CotacaoRestController.recuperarBalancoCarteira()");
		List<HistoricoCarteiraDTO> result = null;
		try {
			result = cotacaoService.findLucroPrejuizo();
		} catch (InvestimentoBusinessException e) {
			ErrorResponse error = new ErrorResponse(e.getMessage());
			return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
		}

		SuccessResponse success = new SuccessResponse("Operação realizada com sucesso", result);
		return new ResponseEntity<SuccessResponse>(success, HttpStatus.OK);

	}

}
