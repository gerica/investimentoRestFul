package com.invest.controller.dto;

public class SuccessResponse extends AbstractResponse {

	public SuccessResponse(String message) {
		super(message);
	}

	public SuccessResponse(String message, Object obj) {
		super(message, obj);
	}

}
