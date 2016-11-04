package com.invest.service.rendaVariavel.dto;

import java.io.Serializable;
import java.util.Date;

public class CotacaoTendenciaDTO implements Serializable {
	private Integer id;
	private Date data;
	private Double ruptura;
	private Double soma;
	private Boolean rompeu;
	private Integer numeroRupturas;
	private Double mediaRupturas;
	private Double stop;
	private Double win;
	private Double valorProtegido;

	public Double getRuptura() {
		return this.ruptura;
	}

	public void setRuptura(Double ruptura) {
		this.ruptura = ruptura;
	}

	public Double getSoma() {
		return this.soma;
	}

	public void setSoma(Double soma) {
		this.soma = soma;
	}

	public Boolean getRompeu() {
		return this.rompeu;
	}

	public void setRompeu(Boolean rompeu) {
		this.rompeu = rompeu;
	}

	public Integer getNumeroRupturas() {
		return this.numeroRupturas;
	}

	public void setNumeroRupturas(Integer numeroRupturas) {
		this.numeroRupturas = numeroRupturas;
	}

	public Double getMediaRupturas() {
		return this.mediaRupturas;
	}

	public void setMediaRupturas(Double mediaRupturas) {
		this.mediaRupturas = mediaRupturas;
	}

	public Double getStop() {
		return this.stop;
	}

	public void setStop(Double stop) {
		this.stop = stop;
	}

	public Double getValorProtegido() {
		return this.valorProtegido;
	}

	public void setValorProtegido(Double valorProtegido) {
		this.valorProtegido = valorProtegido;
	}

	public Date getData() {
		return this.data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getWin() {
		return this.win;
	}

	public void setWin(Double win) {
		this.win = win;
	}
}
