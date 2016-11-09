package com.invest.service.rendaVariavel.dto;

import java.io.Serializable;
import java.util.Date;

public class CotacaoGraficoDTO implements Serializable {
	private static final long serialVersionUID = 5257726610957284776L;
	private Date data;
	private Double fechamento;

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Double getFechamento() {
		return fechamento;
	}

	public void setFechamento(Double fechamento) {
		this.fechamento = fechamento;
	}

}
