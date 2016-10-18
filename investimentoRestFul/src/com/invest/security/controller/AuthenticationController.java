package com.invest.security.controller;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.invest.security.AppConstant;
import com.invest.security.TokenUtils;
import com.invest.security.model.AuthenticationRequest;
import com.invest.security.model.AuthenticationResponse;
import com.invest.security.model.SpringSecurityUser;

@RestController
@RequestMapping("auth")
@PermitAll
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private UserDetailsService userDetailsService;

	@RequestMapping(method = RequestMethod.POST)
	// @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PermitAll
	public ResponseEntity<?> authenticationRequest(@RequestBody AuthenticationRequest authenticationRequest)
			throws AuthenticationException {

		// Perform the authentication
		Authentication authentication = this.authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Reload password post-authentication so we can generate token
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		String token = this.tokenUtils.generateToken(userDetails);

		// Return the token
		return ResponseEntity.ok(new AuthenticationResponse(token));
	}

	@RequestMapping(value = "refresh", method = RequestMethod.GET)
	public ResponseEntity<?> authenticationRequest(HttpServletRequest request) {
		String token = request.getHeader(AppConstant.tokenHeader);
		String username = this.tokenUtils.getUsernameFromToken(token);
		SpringSecurityUser user = (SpringSecurityUser) this.userDetailsService.loadUserByUsername(username);
		if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordReset())) {
			String refreshedToken = this.tokenUtils.refreshToken(token);
			return ResponseEntity.ok(new AuthenticationResponse(refreshedToken));
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

}
