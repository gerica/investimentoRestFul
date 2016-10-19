package com.invest.security.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.invest.entidade.AppUser;
import com.invest.execao.InvestimentoBusinessException;
import com.invest.repository.AppUserRepository;
import com.invest.security.TokenUtils;
import com.invest.security.model.SpringSecurityUser;
import com.invest.security.service.AuthenticationService;

@Service(value = "authenticationService")
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AppUserRepository appUserRepository;

	@Override
	public String authentication(String username, String password) throws AuthenticationException, InvestimentoBusinessException {

		AppUser userBD = getAppUserBD(username, password);

		// Perform the authentication
		Authentication authentication = this.authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(userBD.getUsername(), userBD.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Reload password post-authentication so we can generate token
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(userBD.getUsername());
		String token = this.tokenUtils.generateToken(userDetails);

		// Return the token
		return token;
	}

	public String authenticationRequest(String token) {
		String username = this.tokenUtils.getUsernameFromToken(token);
		SpringSecurityUser user = (SpringSecurityUser) this.userDetailsService.loadUserByUsername(username);
		if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordReset())) {
			return this.tokenUtils.refreshToken(token);

		} else {
			return null;
		}
	}

	private AppUser getAppUserBD(String username, String password) throws InvestimentoBusinessException {
		AppUser userBD = appUserRepository.findByUsername(username);
		if (userBD == null) {
			throw new InvestimentoBusinessException("Usuário não cadastrado");
		}
//		String passwordEncode = getPasswordEnconding(password);
//		if (!passwordEncode.equals(userBD.getPassword())) {
//			throw new InvestimentoBusinessException("Senha incorreta.");
//		}
		return userBD;
	}

	private String getPasswordEnconding(String password) throws InvestimentoBusinessException {

		MessageDigest algorithm = null;
		byte messageDigest[] = null;
		try {
			algorithm = MessageDigest.getInstance("SHA-256");
			messageDigest = algorithm.digest(password.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new InvestimentoBusinessException(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new InvestimentoBusinessException(e.getMessage());
		}

		StringBuilder hexString = new StringBuilder();
		for (byte b : messageDigest) {
			hexString.append(String.format("%02X", 0xFF & b));
		}
		return hexString.toString();
	}

}
