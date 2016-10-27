package com.invest.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invest.entidade.Usuario;
import com.invest.entidade.rendaVariavel.OperacaoEntrada;
import com.invest.entidade.rendaVariavel.UsuarioOperacao;
import com.invest.repository.rendaVariavel.UsuarioOperacaoRepository;
import com.invest.service.UsuarioOperacaoService;

@Service
public class UsuarioOperacaoServiceImpl implements UsuarioOperacaoService {

	private static final Logger logger = LoggerFactory.getLogger(UsuarioOperacaoServiceImpl.class);

	@Autowired
	private UsuarioOperacaoRepository usuarioOperacaoRepository;

	@Override
	public void salvar(OperacaoEntrada operacao, Usuario usuario) {
		logger.info("UsuarioOperacaoServiceImpl.salvar()");
		UsuarioOperacao usuarioOperacao = new UsuarioOperacao();
		usuarioOperacao.setData(new Date());
		usuarioOperacao.setUsuario(usuario);
		usuarioOperacao.setOperacaoEntrada(operacao);

		usuarioOperacaoRepository.save(usuarioOperacao);

	}

}
