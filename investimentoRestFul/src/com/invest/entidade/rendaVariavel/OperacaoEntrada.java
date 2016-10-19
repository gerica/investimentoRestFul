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
@Table(name = "operacao_entrada")
public class OperacaoEntrada {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_papel", nullable = false)
	private Papel papel;

	@Column(name = "data")
	// @NotNull(message="A data n�o pode ser em branco!")
	// @JsonSerialize(using=CustomDateSerializer.class)
	// @JsonDeserialize(using=CustomDateDeserializer.class)
	private Date data;

	@Column(name = "tipo_operacao")
	// @NotEmpty(message="O tipo de opera��o n�o pode ser em branco!")
	private String tipoOperacao;

	@Column(name = "preco_unitario")
	// @NotNull(message="O valor do pre�o unit�rio n�o pode ser em branco!")
	private Double precoUnitario;

	@Column(name = "despesa")
	// @NotNull(message="O valor da despesa n�o pode ser em branco!")
	private Double despesa;

	@Column(name = "quantidade")
	// @NotNull(message="A quantidade n�o pode ser em branco!")
	private Integer quantidade;

	@Column(name = "stop_loss")
	private Double stopLoss;

	@Column(name = "stop_win")
	private Double stopWin;

	@Transient
	private Boolean podeRemover = Boolean.valueOf(true);

	@Column(name = "avaliacao_entrada")
	private Double avaliacaoEntrada;

	@Column(name = "observacao")
	// @NotEmpty(message="O valor da observa��o n�o pode ser em branco!")
	private String observacao;

	@Column(name = "ativo")
	private Boolean ativo = Boolean.valueOf(true);

	public Double getDespesa() {
		return this.despesa;
	}

	public void setDespesa(Double despesa) {
		this.despesa = despesa;
	}

	public Integer getQuantidade() {
		return this.quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getStopLoss() {
		return this.stopLoss;
	}

	public void setStopLoss(Double stopLoss) {
		this.stopLoss = stopLoss;
	}

	public Double getStopWin() {
		return this.stopWin;
	}

	public void setStopWin(Double stopWin) {
		this.stopWin = stopWin;
	}

	public Double getAvaliacaoEntrada() {
		return this.avaliacaoEntrada;
	}

	public void setAvaliacaoEntrada(Double avaliacaoEntrada) {
		this.avaliacaoEntrada = avaliacaoEntrada;
	}

	public String getObservacao() {
		return this.observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Papel getPapel() {
		return this.papel;
	}

	public void setPapel(Papel papel) {
		this.papel = papel;
	}

	public Date getData() {
		return this.data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getTipoOperacao() {
		return this.tipoOperacao;
	}

	public void setTipoOperacao(String tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	@Transient
	public Double getTotalOperacao() {
		if ((this.precoUnitario != null) && (this.quantidade != null)) {
			return Double.valueOf(this.precoUnitario.doubleValue() * this.quantidade.intValue());
		}
		return Double.valueOf(0.0D);
	}

	@Transient
	public boolean isAvaliacaoEntradaRealizada() {
		return (this.avaliacaoEntrada != null) && (this.avaliacaoEntrada.doubleValue() > 0.0D);
	}

	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = 31 * result + (this.id == null ? 0 : this.id.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		OperacaoEntrada other = (OperacaoEntrada) obj;
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public Boolean getPodeRemover() {
		return this.podeRemover;
	}

	public void setPodeRemover(Boolean podeRemover) {
		this.podeRemover = podeRemover;
	}

	public Boolean getAtivo() {
		return this.ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Double getPrecoUnitario() {
		return this.precoUnitario;
	}

	public void setPrecoUnitario(Double precoUnitario) {
		this.precoUnitario = precoUnitario;
	}
}
