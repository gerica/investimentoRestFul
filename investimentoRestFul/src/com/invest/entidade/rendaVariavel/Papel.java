package com.invest.entidade.rendaVariavel;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tb_papel")
public class Papel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_papel")
	private Integer id;

	@Column(name = "ds_nome")
	private String nome;

	@Column(name = "ds_sigla_papel")
	private String papel;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "papel", cascade = { javax.persistence.CascadeType.ALL })
	@OrderBy("data ASC")
	private Set<Cotacao> cotacoes;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_fundamento", nullable = false)
	private Fundamento fundamento;

	@Column(name = "nr_setor")
	private Integer setor;

	@Column(name = "in_ativo")
	private Boolean ativo = Boolean.valueOf(true);

	@Transient
	private Integer rank = new Integer(1);

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPapel() {
		return papel;
	}

	public void setPapel(String papel) {
		this.papel = papel;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Fundamento getFundamento() {
		return fundamento;
	}

	public void setFundamento(Fundamento fundamento) {
		this.fundamento = fundamento;
	}

	@Override
	public String toString() {
		return "Papel [id=" + id + ", nome=" + nome + ", papel=" + papel + ", fundamento=" + fundamento + ", rank=" + rank + "]";
	}

	public Integer getSetor() {
		return setor;
	}

	public void setSetor(Integer setor) {
		this.setor = setor;
	}

	public Set<Cotacao> getCotacoes() {
		return cotacoes;
	}

	public void setCotacoes(Set<Cotacao> cotacoes) {
		this.cotacoes = cotacoes;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((papel == null) ? 0 : papel.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Papel other = (Papel) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (papel == null) {
			if (other.papel != null)
				return false;
		} else if (!papel.equals(other.papel))
			return false;
		return true;
	}

}
