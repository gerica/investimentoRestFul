package com.invest.service.rendaVariavel.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.invest.execao.InvestimentoBusinessException;
import com.invest.service.rendaVariavel.CotacaoBovespaService;

@Service
public class CotacaoBovespaServiceImpl implements CotacaoBovespaService {
	private static final Logger logger = LoggerFactory.getLogger(CotacaoBovespaServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.invest.repository.rendaVariavel.CotacaoBovespaService#recuperarAcao(
	 * java.lang.String)
	 */
	@Override
	public String recuperarAcao(String papel) throws InvestimentoBusinessException {
		logger.info("Acessar BMF Bovespa para obter a cota\u00e7\u00e3o do dia");
		BufferedReader in = null;
		try {
			String inputLine;
			URL url = new URL("http://pregao-online.bmfbovespa.com.br/Noticias.aspx?idioma=pt-BR&Papel=" + papel);
			URLConnection yc = url.openConnection();
			in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			StringBuilder sb = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine);
			}
			in.close();
			String string = sb.toString();
			return string;
		} catch (Exception e) {
			throw new InvestimentoBusinessException("Erro ao acessar o site da bmf bovesta: " + e.getLocalizedMessage());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new InvestimentoBusinessException(e.getMessage());
				}
			}
		}
	}
}
