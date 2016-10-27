package com.invest.controller;

import javax.annotation.security.PermitAll;

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
import com.invest.execao.InvestimentoBusinessException;
import com.invest.service.UsuarioService;

@RestController
public class UsuarioRestController {
	private static final Logger logger = LoggerFactory.getLogger(PapelRestController.class);

	@Autowired
	private UsuarioService usuarioService;

	@RequestMapping(method = RequestMethod.POST, value = UriConstInvestimento.URI_REGISTRAR_USUARIO)
	@PermitAll
	@ResponseBody
	public ResponseEntity<? extends AbstractResponse> registrarUsuario(@RequestBody Usuario usuario) {
		logger.info("OperacaoRestController.salvarOperacao()");

		try {
			usuarioService.registar(usuario);
		} catch (InvestimentoBusinessException e) {
			ErrorResponse error = new ErrorResponse(e.getMessage());
			return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
		}

		SuccessResponse success = new SuccessResponse("Operação realizada com sucesso");
		return new ResponseEntity<SuccessResponse>(success, HttpStatus.OK);

	}

}
