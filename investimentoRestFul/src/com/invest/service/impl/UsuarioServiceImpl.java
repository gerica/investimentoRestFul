package com.invest.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invest.entidade.Usuario;
import com.invest.entidade.permissao.RoleEnum;
import com.invest.execao.InvestimentoBusinessException;
import com.invest.repository.UsuarioRepository;
import com.invest.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public void registar(Usuario usuario) throws InvestimentoBusinessException {
		validarEmail(usuario);
		validarSenha(usuario);
		verificarDuplicidade(usuario);
		usuario.setAuthoritiesBd(RoleEnum.Constants.ROLE_CONVIDADO);
		usuario.setPassword(getPasswordEnconding(usuario.getPassword()));
		usuario.setEmail(usuario.getEmail().toUpperCase());
		usuarioRepository.save(usuario);

	}

	@Override
	public Usuario findByEmail(String email) throws InvestimentoBusinessException {
		if (email == null || "".equals(email)) {
			throw new InvestimentoBusinessException("O emial não pode ser nulo, nem vazio.");
		}
		return usuarioRepository.findByEmail(email.toUpperCase());
	}

	// PRIVATE

	public String getPasswordEnconding(String password) throws InvestimentoBusinessException {

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

	private void validarEmail(Usuario usuario) throws InvestimentoBusinessException {
		String email = usuario.getEmail();

		if ((email == null) || (email.trim().length() == 0)) {
			throw new InvestimentoBusinessException("O email é obrigatório para registrar um usuário.");
		}

		String emailPattern = "\\b(^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";
		Pattern pattern = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(email);
		if (!matcher.matches()) {
			throw new InvestimentoBusinessException(
					"O email informado não está no formato correto. Utilize um email correto.");
		}

	}

	private void verificarDuplicidade(Usuario usuario) throws InvestimentoBusinessException {
		Usuario userDb = findByEmail(usuario.getEmail());
		if (userDb != null) {
			throw new InvestimentoBusinessException(
					"O email informado já existe cadastrado. Por favor informe outro email.");
		}

	}

	private void validarSenha(Usuario usuario) throws InvestimentoBusinessException {
		if (usuario.getPassword() == null || usuario.getPassword().length() <= 4) {
			throw new InvestimentoBusinessException("Informe uma senha com no mínimo 5 caracteres.");
		}

	}

}
