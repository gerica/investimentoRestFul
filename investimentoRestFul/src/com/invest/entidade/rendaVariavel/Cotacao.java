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

@Entity
@Table(name="cotacao")
public class Cotacao
{
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  @Column(name="id")
  private Integer id;
  
  @Column(name="data")
//  @NotNull(message="A data n�o pode ser em branco!")
//  @JsonSerialize(using=CustomDateSerializer.class)
//  @JsonDeserialize(using=CustomDateDeserializer.class)
  private Date data;
  
  @Column(name="abertura")
//  @NotNull(message="O valor de abertura n�o pode ser em branco!")
  private Double abertura;
  
  @Column(name="maxima")
//  @NotNull(message="O valor m�ximo n�o pode ser em branco!")
  private Double maxima;
  
  @Column(name="minima")
//  @NotNull(message="O valor m�nimo n�o pode ser em branco!")
  private Double minima;
  
  @Column(name="fechamento")
//  @NotNull(message="O valor de fechamento n�o pode ser em branco!")
  private Double fechamento;
  
  @ManyToOne(fetch=FetchType.EAGER)
  @JoinColumn(name="id_papel", nullable=false)
  private Papel papel;
  
  public Integer getId()
  {
    return this.id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
  }
  
  public Date getData()
  {
    return this.data;
  }
  
  public void setData(Date data)
  {
    this.data = data;
  }
  
  public Double getAbertura()
  {
    return this.abertura;
  }
  
  public void setAbertura(Double abertura)
  {
    this.abertura = abertura;
  }
  
  public Double getMaxima()
  {
    return this.maxima;
  }
  
  public void setMaxima(Double maxima)
  {
    this.maxima = maxima;
  }
  
  public Double getMinima()
  {
    return this.minima;
  }
  
  public void setMinima(Double minima)
  {
    this.minima = minima;
  }
  
  public Double getFechamento()
  {
    return this.fechamento;
  }
  
  public void setFechamento(Double fechamento)
  {
    this.fechamento = fechamento;
  }
  
  public Papel getPapel()
  {
    return this.papel;
  }
  
  public void setPapel(Papel papel)
  {
    this.papel = papel;
  }
  
  public int hashCode()
  {
    int prime = 31;
    int result = 1;
    result = 31 * result + (this.id == null ? 0 : this.id.hashCode());
    return result;
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Cotacao other = (Cotacao)obj;
    if (this.id == null)
    {
      if (other.id != null) {
        return false;
      }
    }
    else if (!this.id.equals(other.id)) {
      return false;
    }
    return true;
  }
  
  public String toString()
  {
    return 
      "Cotacao [id=" + this.id + ", data=" + this.data + ", abertura=" + this.abertura + ", maxima=" + this.maxima + ", minima=" + this.minima + ", fechamento=" + this.fechamento + ", papel=" + this.papel + "]";
  }
}
