package com.invest.service.impl;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.invest.entidade.rendaVariavel.Cotacao;
import com.invest.execao.InvestimentoBusinessException;
import com.invest.service.ReadXMLFileDomService;

@Service
public class ReadXMLFileDomServiceImpl implements ReadXMLFileDomService {
	private static final Logger logger = LoggerFactory.getLogger(ReadXMLFileDomServiceImpl.class);

	/* (non-Javadoc)
	 * @see com.invest.service.ReadXMLFileDomService#read(java.lang.String)
	 */
	@Override
	public List<Cotacao> read(String content) throws InvestimentoBusinessException {
		logger.info("Lendo url para obter cota��o");
		List<Cotacao> list = new ArrayList<Cotacao>();
		try {
			Cotacao cotacao = null;

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			InputSource inStream = new InputSource();
			inStream.setCharacterStream(new StringReader(content));

			Document doc = dBuilder.parse(inStream);
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("Papel");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == 1) {
					cotacao = new Cotacao();

					Element eElement = (Element) nNode;
					cotacao.setData(obterData(eElement.getAttribute("Data")));
					cotacao.setAbertura(Double.valueOf(Double.parseDouble(eElement.getAttribute("Abertura").replace(',', '.'))));
					cotacao.setFechamento(Double.valueOf(Double.parseDouble(eElement.getAttribute("Ultimo").replace(',', '.'))));
					cotacao.setMinima(Double.valueOf(Double.parseDouble(eElement.getAttribute("Minimo").replace(',', '.'))));
					cotacao.setMaxima(Double.valueOf(Double.parseDouble(eElement.getAttribute("Maximo").replace(',', '.'))));

					list.add(cotacao);
				}
			}
		} catch (Exception e) {
			throw new InvestimentoBusinessException(e.getMessage());
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see com.invest.service.ReadXMLFileDomService#obterData(java.lang.String)
	 */
	@Override
	public Date obterData(String dataString) {
		DateTime data = null;
		try {
			data = DateTime.parse(dataString, DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss"));
		} catch (IllegalArgumentException e) {
			data = DateTime.parse(dataString.substring(0, 10), DateTimeFormat.forPattern("dd/MM/yyyy"));
		}
		return data.toDate();
	}
}
