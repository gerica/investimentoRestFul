package com.invest.entidade.rendaVariavel;

public enum SetorEnum {
	TODOS("TODOS", 0), //
	ENERGETICO("ENERG�TICO", 32), //
	FINANCEIRO("FINANCEIRO", 35), //
	VESTUARIO("VESTU�RIO", 21), //
	;

	private final String desc;
	private final Integer id;

	private SetorEnum(String d, Integer i) {
		this.desc = d;
		this.id = i;

	}

	public String getDesc() {
		return desc;
	}

	public Integer getId() {
		return id;
	}

}
