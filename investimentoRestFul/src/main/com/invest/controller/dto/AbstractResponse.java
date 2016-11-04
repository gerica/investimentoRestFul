package com.invest.controller.dto;

public abstract class AbstractResponse {
	private String message;
	private Object objeto;

	public AbstractResponse(String message) {
		this.message = message;
	}

	public AbstractResponse(String message, Object obj) {
		this.message = message;
		this.objeto = obj;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObjeto() {
		return objeto;
	}

	public void setObjeto(Object objeto) {
		this.objeto = objeto;
	}

}
