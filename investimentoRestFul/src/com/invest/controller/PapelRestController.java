package com.invest.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.invest.controller.dto.AbstractResponse;
import com.invest.controller.dto.ErrorResponse;
import com.invest.controller.dto.SuccessResponse;
import com.invest.entidade.permissao.RoleEnum;
import com.invest.entidade.rendaVariavel.Papel;
import com.invest.entidade.rendaVariavel.SetorEnum;
import com.invest.execao.InvestimentoBusinessException;
import com.invest.service.rendaVariavel.PapelService;
import com.invest.service.rendaVariavel.TabelaMagicaService;
import com.invest.service.rendaVariavel.dto.BalancoCarteiraDTO;

@RestController
public class PapelRestController {
	private static final Logger logger = LoggerFactory.getLogger(PapelRestController.class);

	@Autowired
	private TabelaMagicaService tabelaMagicaService;

	@Autowired
	private PapelService papelService;

	@GetMapping(UriConstInvestimento.URI_TABELA_MAGICA)
	@RolesAllowed({ RoleEnum.Constants.ROLE_ADMIN, RoleEnum.Constants.ROLE_CONVIDADO })
	@ResponseBody
	public List<Papel> getTabelaMagica() {
		logger.info("PapelRestController.getTabelaMagica()");

		List<Papel> papeis = null;
		try {
			papeis = tabelaMagicaService.analizarPapeis(tabelaMagicaService.findBySetor(SetorEnum.TODOS));

		} catch (InvestimentoBusinessException e) {
			e.printStackTrace();
		}
		return papeis;
	}

	@RequestMapping(method = RequestMethod.GET, value = UriConstInvestimento.URI_RECUPERAR_PAPEIS_ATIVO)
	@RolesAllowed({ RoleEnum.Constants.ROLE_ADMIN, RoleEnum.Constants.ROLE_CONVIDADO })
	@ResponseBody
	public List<Papel> recuperarPapeisAtivos() {
		logger.info("PapelRestController.recuperarPaeisAtivos()");
		List<Papel> papeis = papelService.findAllByAtivo();

		return papeis;
	}

	@RequestMapping(method = RequestMethod.GET, value = UriConstInvestimento.URI_RECUPERAR_TODOS_PAPEIS)
	@RolesAllowed({ RoleEnum.Constants.ROLE_ADMIN, RoleEnum.Constants.ROLE_CONVIDADO })
	@ResponseBody
	public List<Papel> recuperarTodosPapeis() {
		logger.info("PapelRestController.recuperarTodosPaeis()");
		List<Papel> papeis = papelService.findAll();

		return papeis;
	}

	@RequestMapping(method = RequestMethod.POST, value = UriConstInvestimento.URI_ATIVAR_DESATIVAR_PAPEL)
	@RolesAllowed({ RoleEnum.Constants.ROLE_ADMIN, RoleEnum.Constants.ROLE_CONVIDADO })
	@ResponseBody
	public ResponseEntity<? extends AbstractResponse> ativarDesativarPapel(@RequestBody Papel papel) {
		logger.info("PapelRestController.ativarPapel()");

		try {
			papelService.ativarDesativar(papel.getId());
		} catch (InvestimentoBusinessException e) {
			ErrorResponse error = new ErrorResponse(e.getMessage());
			return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
		}

		SuccessResponse success = new SuccessResponse("Operação realizada com sucesso");
		return new ResponseEntity<SuccessResponse>(success, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = UriConstInvestimento.URI_RECUPERAR_BALANCO_HOJE)
	@RolesAllowed({ RoleEnum.Constants.ROLE_ADMIN, RoleEnum.Constants.ROLE_CONVIDADO })
	@ResponseBody
	public ResponseEntity<? extends AbstractResponse> recuperarBalancoHoje() {
		logger.info("PapelRestController.recuperarBalancoHoje()");
		List<BalancoCarteiraDTO> result;

		try {
			result = papelService.findBalancoHoje();
		} catch (InvestimentoBusinessException e) {
			ErrorResponse error = new ErrorResponse(e.getMessage());
			return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
		}

		SuccessResponse success = new SuccessResponse("Operação realizada com sucesso", result);
		return new ResponseEntity<SuccessResponse>(success, HttpStatus.OK);
	}

}
