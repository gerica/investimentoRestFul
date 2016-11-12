package com.invest.service.rendaVariavel.dto;

import java.io.Serializable;
import java.util.Date;

public class HistoricoCarteiraDTO implements Serializable {
	private static final long serialVersionUID = 5257726610957284776L;
	private Date data;
	private Double lucroPrejuizo = 0.0;

	public Double getLucroPrejuizo() {
		return lucroPrejuizo;
	}

	public void setLucroPrejuizo(Double lucroPrejuizo) {
		this.lucroPrejuizo = lucroPrejuizo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}
