package com.invest.entidade.rendaVariavel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fundamento")
public class Fundamento implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "p_l")
	private Double p_l; // entre 1 e 30

	@Column(name = "p_vp")
	private Double p_vp; // entre 0 e 20

	@Column(name = "dividentoYIELD")
	private Double dividentoYIELD; // maior que 0

	@Column(name = "margemEBIT")
	private Double margemEBIT; // TER� QUE SER >0

	@Column(name = "liquidezCorrete")
	private Double liquidezCorrete; // maior que 1

	@Column(name = "roe")
	private Double roe; // maior que 0%

	@Column(name = "liquidez2Meses")
	private Double liquidez2Meses; // maior que 100.000

	@Column(name = "crescimento")
	private Double crescimento; // maior que 5%

	@Override
	public String toString() {
		return "Cotacao [p_l=" + p_l + ", p_vp=" + p_vp + ", roe=" + roe + ", margemEBIT=" + margemEBIT + ", liquidezCorrete=" + liquidezCorrete
				+ ", crescimento=" + crescimento + ", dividentoYIELD=" + dividentoYIELD + ", liquidez2Meses=" + liquidez2Meses + "]";
	}

	public Double getP_l() {
		return p_l;
	}

	public void setP_l(Double p_l) {
		this.p_l = p_l;
	}

	public Double getP_vp() {
		return p_vp;
	}

	public void setP_vp(Double p_vp) {
		this.p_vp = p_vp;
	}

	public Double getDividentoYIELD() {
		return dividentoYIELD;
	}

	public void setDividentoYIELD(Double dividentoYIELD) {
		this.dividentoYIELD = dividentoYIELD;
	}

	public Double getMargemEBIT() {
		return margemEBIT;
	}

	public void setMargemEBIT(Double margemEBIT) {
		this.margemEBIT = margemEBIT;
	}

	public Double getLiquidezCorrete() {
		return liquidezCorrete;
	}

	public void setLiquidezCorrete(Double liquidezCorrete) {
		this.liquidezCorrete = liquidezCorrete;
	}

	public Double getRoe() {
		return roe;
	}

	public void setRoe(Double roe) {
		this.roe = roe;
	}

	public Double getLiquidez2Meses() {
		return liquidez2Meses;
	}

	public void setLiquidez2Meses(Double liquidez2Meses) {
		this.liquidez2Meses = liquidez2Meses;
	}

	public Double getCrescimento() {
		return crescimento;
	}

	public void setCrescimento(Double crescimento) {
		this.crescimento = crescimento;
	}

}
