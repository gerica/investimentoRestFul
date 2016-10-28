package com.invest.security.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.invest.entidade.Usuario;
import com.invest.execao.InvestimentoBusinessException;
import com.invest.repository.UsuarioRepository;
import com.invest.security.AppConstant;
import com.invest.security.TokenUtils;
import com.invest.security.service.AuthenticationService;
import com.invest.service.UsuarioService;

@Service(value = "authenticationService")
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UsuarioRepository appUserRepository;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired(required = true)
	private HttpServletRequest request;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	@Transactional
	public Usuario loadUserByUsername(String username) {
		// sessionFactory.getCurrentSession().createCriteria(AppUser.class).add(Restrictions.eq("username",
		// username));
		return (Usuario) sessionFactory.getCurrentSession().createCriteria(Usuario.class)
				.add(Restrictions.eq("username", username)).uniqueResult();
	}

	@Transactional
	@Override
	public long post(Usuario appUser) {
		return (long) sessionFactory.getCurrentSession().save(appUser);
	}

	@Transactional
	@Override
	public Usuario get(long id) {
		return (Usuario) sessionFactory.getCurrentSession().get(Usuario.class, id);
	}

	@Transactional
	@Override
	public Usuario get() {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authToken = httpRequest.getHeader(AppConstant.tokenHeader);
		String username = this.tokenUtils.getUsernameFromToken(authToken);

		sessionFactory.getCurrentSession().get(Usuario.class, username);

		return loadUserByUsername(username);
	}

	@Transactional
	@Override
	public Usuario patch(Usuario appUser) {
		sessionFactory.getCurrentSession().update(appUser);
		return get(appUser.getId());
	}

	@Transactional
	@Override
	public boolean delete(long id) {
		sessionFactory.getCurrentSession().delete(get(id));
		return true;
	}

	@Override
	public String authentication(String email, String password)
			throws AuthenticationException, InvestimentoBusinessException {

		Usuario userBD = getAppUserBD(email, password);

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
		Usuario user = (Usuario) this.userDetailsService.loadUserByUsername(username);
		if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordReset())) {
			return this.tokenUtils.refreshToken(token);

		} else {
			return null;
		}
	}

	private Usuario getAppUserBD(String email, String password) throws InvestimentoBusinessException {
		Usuario userBD = appUserRepository.findByEmail(email.toUpperCase());
		if (userBD == null) {
			throw new InvestimentoBusinessException("Usuário não cadastrado com esse email: " + email);
		}
		String passwordEncode = usuarioService.getPasswordEnconding(password);
		if (!passwordEncode.equals(userBD.getPassword())) {
			throw new InvestimentoBusinessException("Senha incorreta.");
		}
		return userBD;
	}

}
