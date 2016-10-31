package com.invest.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.invest.entidade.rendaVariavel.Cotacao;
import com.invest.execao.InvestimentoBusinessException;
import com.invest.service.HTMLParserService;
import com.invest.util.DataUtil;

@Service
public class HTMLParserServiceImpl implements HTMLParserService {
	private static final Logger logger = LoggerFactory.getLogger(HTMLParserServiceImpl.class);

	public List<Cotacao> readADVFN(String papel) throws InvestimentoBusinessException {
		if ((papel == null) || ("".equals(papel))) {
			throw new InvestimentoBusinessException("O papel informado � nulo ou vazio.");
		}
		logger.info("Lendo url para obter cotação do papel: " + papel);
		List<Cotacao> list = new ArrayList<Cotacao>();

		Cotacao cotacao = null;
		try {
			// String url =
			// "http://pregao-online.bmfbovespa.com.br/Noticias.aspx?idioma=pt-BR&Papel="
			// + papel.toUpperCase();
			String url = "http://br.advfn.com/bolsa-de-valores/bovespa/" + papel.toUpperCase() + "/cotacao";
			// String url =
			// "http://exame.abril.com.br/mercados/cotacoes-bovespa/acoes/"+papel.toUpperCase();

			Document doc = Jsoup.connect(url).get();

			Elements divCotacoes = doc.select("div.TableElement");
			Elements tableElements = null;
			for (int i = 0; i < divCotacoes.size(); i++) {
				if (i == 2) {
					tableElements = divCotacoes.get(i).select("table");
				}
			}

			Elements rowItems = null;
			Elements tableRowElements = tableElements.select(":not(thead) tr");
			for (int i = 0; i < tableRowElements.size(); i++) {
				if (i == 1) {
					rowItems = tableRowElements.get(i).select("td");
				}
			}

			for (int i = 0; i < rowItems.size(); i++) {
				if (i == 4) {
					Element row = rowItems.get(i);
					// máximo
					System.out.println(row.text());
				} else if (i == 5) {
					Element row = rowItems.get(i);
					// mínimo
					System.out.println(row.text());
				} else if (i == 6) {
					Element row = rowItems.get(i);
					// abertura
					System.out.println(row.text());

				} else if (i == 7) {
					Element row = rowItems.get(i);
					// fechamento
					System.out.println(row.text());
				}
			}

			// Elements tableElements = doc.select("table");

			// Elements tableRowElements = tableElements.select(":not(thead)
			// tr");
			// for (int i = 0; i < tableRowElements.size(); i++) {
			// cotacao = new Cotacao();
			// Element row = tableRowElements.get(i);
			// Elements rowItems = row.select("td");
			// System.out.println(rowItems);
			// System.out.println(rowItems.select("td[id$=tdDescription] >
			// div > a").get(1).text());
			// if (rowItems.size() > 0) {
			// cotacao.setData(DataUtil.parseToDate(rowItems.get(7).text(),
			// "dd/MM/yy"));
			// cotacao.setFechamento(Double.valueOf(Double.parseDouble(rowItems.get(2).text().replace(',',
			// '.'))));
			// cotacao.setAbertura(Double.valueOf(Double.parseDouble(rowItems.get(3).text().replace(',',
			// '.'))));
			// cotacao.setMaxima(Double.valueOf(Double.parseDouble(rowItems.get(4).text().replace(',',
			// '.'))));
			// cotacao.setMinima(Double.valueOf(Double.parseDouble(rowItems.get(5).text().replace(',',
			// '.'))));
			//
			// System.out.println(cotacao);
			//
			// list.add(cotacao);
			// }
			// }
		} catch (IOException e) {
			throw new InvestimentoBusinessException(e.getMessage());
		}
		Document doc;
		return list;
	}

	public List<Cotacao> lerCotacoesHistorica(String papel) throws InvestimentoBusinessException {
		if ((papel == null) || ("".equals(papel))) {
			throw new InvestimentoBusinessException("O papel informado é nulo ou vazio.");
		}
		logger.info("Lendo url para obter cotação do papel: " + papel);
		List<Cotacao> list = new ArrayList<Cotacao>();

		String urlPagina1 = "https://br.financas.yahoo.com/q/hp?s=" + papel.toUpperCase() + ".SA";
		String urlPagina2 = "https://br.financas.yahoo.com/q/hp?s=" + papel.toUpperCase()
				+ ".SA&d=9&e=31&f=2016&g=d&a=0&b=3&c=2000&z=66&y=66";

		historicoCotacoes(urlPagina1, list);
		historicoCotacoes(urlPagina2, list);

		return list;
	}

	private void historicoCotacoes(String url, List<Cotacao> list) throws InvestimentoBusinessException {

		Cotacao cotacao = null;
		try {

			Document doc = Jsoup.connect(url).get();

			Elements tabelaCotacaoesHistorica = doc.select("table.yfnc_datamodoutline1");

			Elements tableRowElements = tabelaCotacaoesHistorica.select(":not(thead) tr");
			for (int i = 0; i < tableRowElements.size(); i++) {
				cotacao = new Cotacao();
				Element row = tableRowElements.get(i);
				Elements rowItems = row.select("td");
				if (rowItems.size() > 0 && rowItems.size() == 7) {
					if (i > 0) {
						cotacao.setData(DataUtil.parseToDateTransformeString(rowItems.get(0).text()));
						cotacao.setAbertura(
								Double.valueOf(Double.parseDouble(rowItems.get(1).text().replace(',', '.'))));
						cotacao.setMaxima(Double.valueOf(Double.parseDouble(rowItems.get(2).text().replace(',', '.'))));
						cotacao.setMinima(Double.valueOf(Double.parseDouble(rowItems.get(3).text().replace(',', '.'))));
						cotacao.setFechamento(
								Double.valueOf(Double.parseDouble(rowItems.get(4).text().replace(',', '.'))));

						System.out.println(cotacao);

						list.add(cotacao);

					}
				}
			}
		} catch (IOException e) {
			throw new InvestimentoBusinessException(e.getMessage());
		}
	}

	public Cotacao lerCotacaoAtual(String papel) throws InvestimentoBusinessException {
		if ((papel == null) || ("".equals(papel))) {
			throw new InvestimentoBusinessException("O papel informado � nulo ou vazio.");
		}
		logger.info("Lendo url para obter cotação do papel: " + papel);

		Cotacao cotacao = new Cotacao();
		try {

			String url = "https://br.financas.yahoo.com/q?s=" + papel.toUpperCase() + ".SA";

			Document doc = Jsoup.connect(url).get();

			// Data
			Elements dataCotacao = doc.select("span.time_rtq");
			cotacao.setData(DataUtil.parseToDateTransformeString(dataCotacao.text()));

			// fechamento
			Elements spanFechamento = doc.select("span.time_rtq_ticker");
			cotacao.setFechamento(Double.valueOf(Double.parseDouble(spanFechamento.text().replace(',', '.'))));

			Elements tdCotacoes = doc.select("td.yfnc_tabledata1");
			for (int i = 0; i < tdCotacoes.size(); i++) {
				if (i == 1) {
					// abertura
					cotacao.setAbertura(Double.valueOf(Double.parseDouble(tdCotacoes.get(i).text().replace(',', '.'))));
				} else if (i == 7) {
					cotacao.setMaxima(Double
							.valueOf(Double.parseDouble(tdCotacoes.get(i).text().substring(8, 13).replace(',', '.'))));
					cotacao.setMinima(Double
							.valueOf(Double.parseDouble(tdCotacoes.get(i).text().substring(0, 5).replace(',', '.'))));
				}

			}

		} catch (IOException e) {
			throw new InvestimentoBusinessException(e.getMessage());
		}
		System.out.println(cotacao);
		return cotacao;
	}

	public static void main(String[] args) {
		HTMLParserService p = new HTMLParserServiceImpl();
		try {
//			p.lerCotacoesHistorica("ABEV3");
			 p.lerCotacaoAtual("ALPA4");
			// p.lerCotacaoAtual("HGTX3");
		} catch (InvestimentoBusinessException e) {
			e.printStackTrace();
		}
	}
}
