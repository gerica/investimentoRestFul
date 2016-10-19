package com.invest.service.rendaVariavel.dto;

import com.invest.entidade.rendaVariavel.OperacaoSaida;
import com.invest.util.FormatadorUtil;

class HistoricoOperacaoSaidaDTO {
	private String dataSaida;
	private String precoUnitarioSaida;
	private String quantidadeSaida;
	private String totalSaida;

	public HistoricoOperacaoSaidaDTO(OperacaoSaida o) {
		this.dataSaida = FormatadorUtil.formatarData(o.getData());
		this.precoUnitarioSaida = FormatadorUtil.formatarMoeda(o.getPrecoUnitario());
		this.quantidadeSaida = o.getQuantidade().toString();
		this.totalSaida = FormatadorUtil.formatarMoeda(o.getTotalOperacao());
	}

	public String getDataSaida() {
		return this.dataSaida;
	}

	public void setDataSaida(String dataSaida) {
		this.dataSaida = dataSaida;
	}

	public String getPrecoUnitarioSaida() {
		return this.precoUnitarioSaida;
	}

	public void setPrecoUnitarioSaida(String precoUnitarioSaida) {
		this.precoUnitarioSaida = precoUnitarioSaida;
	}

	public String getQuantidadeSaida() {
		return this.quantidadeSaida;
	}

	public void setQuantidadeSaida(String quantidadeSaida) {
		this.quantidadeSaida = quantidadeSaida;
	}

	public String getTotalSaida() {
		return this.totalSaida;
	}

	public void setTotalSaida(String totalSaida) {
		this.totalSaida = totalSaida;
	}
}
