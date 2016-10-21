package com.invest.controller.dto;

public abstract class AbstractResponse {
	private String message;

	public AbstractResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
