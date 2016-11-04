package com.invest.service.rendaVariavel.dto;

import java.util.ArrayList;
import java.util.List;

import com.invest.entidade.rendaVariavel.OperacaoEntrada;
import com.invest.entidade.rendaVariavel.OperacaoSaida;
import com.invest.util.FormatadorUtil;

public class HistoricoRendaVariavelDTO {
	private String dataEntrada;
	private String nomePapel;
	private String tipoOperacao;
	private String precoUnitarioEntrada;
	private String quantidadeEntrada;
	private String totalEntrada;
	private List<HistoricoOperacaoSaidaDTO> saidas;
	private String classCssLucroPrejuizo;
	private static final String CSS_LUCRO = "lucro";
	private static final String CSS_PREJUIZO = "prejuizo";

	public HistoricoRendaVariavelDTO(OperacaoEntrada entrada, List<OperacaoSaida> listaSaidas) {
		this.dataEntrada = FormatadorUtil.formatarData(entrada.getData());
		this.nomePapel = entrada.getPapel().getNome();
		this.tipoOperacao = entrada.getTipoOperacao();
		this.precoUnitarioEntrada = FormatadorUtil.formatarMoeda(entrada.getPrecoUnitario());
		this.quantidadeEntrada = entrada.getQuantidade().toString();
		this.totalEntrada = FormatadorUtil.formatarMoeda(entrada.getTotalOperacao());
		if (listaSaidas != null) {
			this.saidas = new ArrayList<HistoricoOperacaoSaidaDTO>();
			for (OperacaoSaida o : listaSaidas) {
				this.saidas.add(new HistoricoOperacaoSaidaDTO(o));
			}
		}
	}

	public String getClassCssLucroPrejuizo() {
		if ((this.totalEntrada != null) && (FormatadorUtil.isInteger(this.totalEntrada))) {
			Double totalEntradaInt = Double.valueOf(Double.parseDouble(this.totalEntrada));
			Double totalSaidaInt = getTotalSaida();
			if (totalSaidaInt.doubleValue() > 0.0D) {
				if (totalSaidaInt.doubleValue() > totalEntradaInt.doubleValue()) {
					this.classCssLucroPrejuizo = "lucro";
				} else if (totalSaidaInt.doubleValue() < totalEntradaInt.doubleValue()) {
					this.classCssLucroPrejuizo = "prejuizo";
				}
			}
		}
		return this.classCssLucroPrejuizo;
	}

	public String getDataEntrada() {
		return this.dataEntrada;
	}

	public void setDataEntrada(String dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public String getNomePapel() {
		return this.nomePapel;
	}

	public void setNomePapel(String nomePapel) {
		this.nomePapel = nomePapel;
	}

	public String getTipoOperacao() {
		return this.tipoOperacao;
	}

	public void setTipoOperacao(String tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public String getPrecoUnitarioEntrada() {
		return this.precoUnitarioEntrada;
	}

	public void setPrecoUnitarioEntrada(String precoUnitarioEntrada) {
		this.precoUnitarioEntrada = precoUnitarioEntrada;
	}

	public String getQuantidadeEntrada() {
		return this.quantidadeEntrada;
	}

	public void setQuantidadeEntrada(String quantidadeEntrada) {
		this.quantidadeEntrada = quantidadeEntrada;
	}

	public String getTotalEntrada() {
		return this.totalEntrada;
	}

	public void setTotalEntrada(String totalEntrada) {
		this.totalEntrada = totalEntrada;
	}

	public void setClassCssLucroPrejuizo(String classCssLucroPrejuizo) {
		this.classCssLucroPrejuizo = classCssLucroPrejuizo;
	}

	private Double getTotalSaida() {
		Double totalSaida = Double.valueOf(0.0D);
		if (this.saidas != null) {
			for (HistoricoOperacaoSaidaDTO saidaDTO : this.saidas) {
				if (FormatadorUtil.isInteger(saidaDTO.getTotalSaida())) {
					totalSaida = Double.valueOf(totalSaida.doubleValue() + Double.parseDouble(saidaDTO.getTotalSaida()));
				}
			}
		}
		return totalSaida;
	}

	public List<HistoricoOperacaoSaidaDTO> getSaidas() {
		return this.saidas;
	}

	public void setSaidas(List<HistoricoOperacaoSaidaDTO> saidas) {
		this.saidas = saidas;
	}
}
