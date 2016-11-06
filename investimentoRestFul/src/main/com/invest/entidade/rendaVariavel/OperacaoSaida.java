package com.invest.entidade.rendaVariavel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tb_operacao_saida")
public class OperacaoSaida {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_operacao_saida")
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_operacao_entrada", nullable = false)
	private OperacaoEntrada operacaoEntrada;

	@Column(name = "dt_operacao_saida")
	// @NotNull(message="A data n�o pode ser em branco!")
	// @JsonSerialize(using=CustomDateSerializer.class)
	// @JsonDeserialize(using=CustomDateDeserializer.class)
	private Date data;

	@Column(name = "nr_preco_unitario")
	// @NotNull(message="O valor do pre�o unit�rio n�o pode ser em branco!")
	private Double precoUnitario;

	@Column(name = "nr_despesa")
	// @NotNull(message="O valor da despesa n�o pode ser em branco!")
	private Double despesa;

	@Column(name = "ds_observacao")
	// @NotEmpty(message="O valor da observa��o n�o pode ser em branco!")
	private String observacao;

	@Column(name = "nr_avaliacao_saida")
	private Double avaliacaoSaida;

	@Column(name = "in_ativo")
	private Boolean ativo = Boolean.valueOf(true);

	@Column(name = "nr_quantidade")
	// @NotNull(message="O valor da quantidade n�o pode ser em branco!")
	private Integer quantidade;

	public Boolean getAtivo() {
		return this.ativo;
	}

	public Double getAvaliacaoSaida() {
		return this.avaliacaoSaida;
	}

	public Date getData() {
		return this.data;
	}

	public Double getDespesa() {
		return this.despesa;
	}

	public Integer getId() {
		return this.id;
	}

	@Transient
	public Double getLucroPrejuizo() {
		return Double.valueOf(0.0D);
	}

	public String getObservacao() {
		return this.observacao;
	}

	public OperacaoEntrada getOperacaoEntrada() {
		return this.operacaoEntrada;
	}

	public Double getPrecoUnitario() {
		return this.precoUnitario;
	}

	public Integer getQuantidade() {
		return this.quantidade;
	}

	@Transient
	public Double getTotalOperacao() {
		if ((this.precoUnitario != null) && (this.operacaoEntrada != null) && (this.operacaoEntrada.getQuantidade() != null)) {
			return Double.valueOf(this.precoUnitario.doubleValue() * this.getQuantidade().intValue()) - this.getDespesa();
		}
		data.getMonth();
		return Double.valueOf(0.0D);
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public void setAvaliacaoSaida(Double avaliacaoSaida) {
		this.avaliacaoSaida = avaliacaoSaida;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public void setDespesa(Double despesa) {
		this.despesa = despesa;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public void setOperacaoEntrada(OperacaoEntrada operacaoEntrada) {
		this.operacaoEntrada = operacaoEntrada;
	}

	public void setPrecoUnitario(Double precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
}
