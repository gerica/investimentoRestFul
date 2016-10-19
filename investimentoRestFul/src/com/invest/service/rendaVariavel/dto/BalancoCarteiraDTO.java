package com.invest.service.rendaVariavel.dto;

import java.util.Date;

public class BalancoCarteiraDTO
{
  private String papel;
  private Date dataInvestimento;
  private Double valorInvestimento;
  private Double totalInvestimento;
  private Date dataUltimaCotacao;
  private Double valorUltimaCotacao;
  private Double porcentagemLucroPrejuizo;
  private Double lucroPrejuizo;
  private Double saldoLucroPrejuizo;
  private String classCssLucroPrejuizo;
  private static final String CSS_LUCRO = "success";
  private static final String CSS_PREJUIZO = "danger";
  
  public String getPapel()
  {
    return this.papel;
  }
  
  public void setPapel(String papel)
  {
    this.papel = papel;
  }
  
  public Date getDataInvestimento()
  {
    return this.dataInvestimento;
  }
  
  public void setDataInvestimento(Date dataInvestimento)
  {
    this.dataInvestimento = dataInvestimento;
  }
  
  public Double getTotalInvestimento()
  {
    return this.totalInvestimento;
  }
  
  public void setTotalInvestimento(Double totalInvestimento)
  {
    this.totalInvestimento = totalInvestimento;
  }
  
  public Date getDataUltimaCotacao()
  {
    return this.dataUltimaCotacao;
  }
  
  public void setDataUltimaCotacao(Date dataUltimaCotacao)
  {
    this.dataUltimaCotacao = dataUltimaCotacao;
  }
  
  public Double getValorUltimaCotacao()
  {
    return this.valorUltimaCotacao;
  }
  
  public void setValorUltimaCotacao(Double valorUltimaCotacao)
  {
    this.valorUltimaCotacao = valorUltimaCotacao;
  }
  
  public Double getPorcentagemLucroPrejuizo()
  {
    return this.porcentagemLucroPrejuizo;
  }
  
  public void setPorcentagemLucroPrejuizo(Double porcentagemLucroPrejuizo)
  {
    this.porcentagemLucroPrejuizo = porcentagemLucroPrejuizo;
  }
  
  public Double getLucroPrejuizo()
  {
    return this.lucroPrejuizo;
  }
  
  public void setLucroPrejuizo(Double lucroPrejuizo)
  {
    this.lucroPrejuizo = lucroPrejuizo;
  }
  
  public Double getSaldoLucroPrejuizo()
  {
    return this.saldoLucroPrejuizo;
  }
  
  public void setSaldoLucroPrejuizo(Double saldoLucroPrejuizo)
  {
    this.saldoLucroPrejuizo = saldoLucroPrejuizo;
  }
  
  public String getClassCssLucroPrejuizo()
  {
    if (this.lucroPrejuizo.doubleValue() > 0.0D) {
      this.classCssLucroPrejuizo = "success";
    } else if (this.lucroPrejuizo.doubleValue() < 0.0D) {
      this.classCssLucroPrejuizo = "danger";
    }
    return this.classCssLucroPrejuizo;
  }
  
  public void setClassCssLucroPrejuizo(String classCssLucroPrejuizo)
  {
    this.classCssLucroPrejuizo = classCssLucroPrejuizo;
  }
  
  public Double getValorInvestimento()
  {
    return this.valorInvestimento;
  }
  
  public void setValorInvestimento(Double valorInvestimento)
  {
    this.valorInvestimento = valorInvestimento;
  }
}
