package com.invest.security.controller;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.invest.controller.UriConstInvestimento;
import com.invest.entidade.Usuario;
import com.invest.execao.InvestimentoBusinessException;
import com.invest.security.AppConstant;
import com.invest.security.model.AuthenticationResponse;
import com.invest.security.service.AuthenticationService;
import com.invest.service.UsuarioService;

@RestController
@PermitAll
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private UsuarioService usuarioService;

	@RequestMapping(value = UriConstInvestimento.URI_AUTH, method = RequestMethod.POST)
	@PermitAll
	public ResponseEntity<?> authenticationRequest(@RequestBody Usuario usuario) throws AuthenticationException {

		String token = null;
		Usuario usuarioAuth = null;

		try {
			token = authenticationService.authentication(usuario.getEmail(), usuario.getPassword());
			usuarioAuth = usuarioService.findByEmail(usuario.getEmail());

		} catch (InvestimentoBusinessException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		// Return the token
		return ResponseEntity.ok(new AuthenticationResponse(token, usuarioAuth));
	}

	@RequestMapping(value = UriConstInvestimento.URI_REFRESH, method = RequestMethod.GET)
	public ResponseEntity<?> authenticationRequest(HttpServletRequest request) {
		String token = request.getHeader(AppConstant.tokenHeader);

		String refreshedToken = authenticationService.authenticationRequest(token);
		if (refreshedToken != null) {

			return ResponseEntity.ok(new AuthenticationResponse(refreshedToken));
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

}
