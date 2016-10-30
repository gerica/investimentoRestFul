package com.invest.service.rendaVariavel.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.invest.entidade.Usuario;
import com.invest.entidade.rendaVariavel.ConfiguracaoAnaliseCotacoes;
import com.invest.execao.InvestimentoBusinessException;
import com.invest.repository.rendaVariavel.ConfiguracaoAnaliseCotacoesRepository;
import com.invest.security.service.AuthenticationService;
import com.invest.service.rendaVariavel.ConfiguracaoAnaliseCotacoesService;

@Service
public class ConfiguracaoAnaliseCotacoesServiceImpl implements ConfiguracaoAnaliseCotacoesService {
	private static final Logger logger = LoggerFactory.getLogger(ConfiguracaoAnaliseCotacoesServiceImpl.class);

	@Autowired
	private ConfiguracaoAnaliseCotacoesRepository repository;
	
	@Autowired
	private AuthenticationService authenticationService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.invest.service.rendaVariavel.impl.ConfiguracaoAnaliseCotacoesService#
	 * findByUsuario()
	 */
	@Override
	public ConfiguracaoAnaliseCotacoes findByUsuario() {
		Usuario usuario = authenticationService.get();
		logger.info("findByUsuario " + usuario);
		ConfiguracaoAnaliseCotacoes config = this.repository.findByUsuario(usuario);
		if (config == null) {
			config = new ConfiguracaoAnaliseCotacoes();
		}
		config.configDefault();
		return config;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.invest.service.rendaVariavel.impl.ConfiguracaoAnaliseCotacoesService#
	 * gravar(com.invest.entidade.rendaVariavel.ConfiguracaoAnaliseCotacoes)
	 */
	@Override
	public void gravar(ConfiguracaoAnaliseCotacoes config) throws InvestimentoBusinessException {
		logger.info("Salvar " + config);
		config.setUsuario(authenticationService.get());
		this.repository.save(config);
	}
}
