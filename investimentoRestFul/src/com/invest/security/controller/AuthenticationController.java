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

import com.invest.execao.InvestimentoBusinessException;
import com.invest.security.AppConstant;
import com.invest.security.model.AuthenticationRequest;
import com.invest.security.model.AuthenticationResponse;
import com.invest.security.service.AuthenticationService;

@RestController
@RequestMapping("auth")
@PermitAll
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;

	@RequestMapping(method = RequestMethod.POST)
	// @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	// @PermitAll
	public ResponseEntity<?> authenticationRequest(@RequestBody AuthenticationRequest authenticationRequest)
			throws AuthenticationException {

		String username = authenticationRequest.getUsername();
		String password = authenticationRequest.getPassword();

		String token = null;
		try {
			token = authenticationService.authentication(username, password);
		} catch (InvestimentoBusinessException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		// Return the token
		return ResponseEntity.ok(new AuthenticationResponse(token));
	}

	@RequestMapping(value = "refresh", method = RequestMethod.GET)
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
