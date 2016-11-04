package com.invest.execao;

public class InvestimentoBusinessException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvestimentoBusinessException() {
	}

	public InvestimentoBusinessException(String msg) {
		super(msg);
	}

	public InvestimentoBusinessException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvestimentoBusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvestimentoBusinessException(Throwable cause) {
		super(cause);
	}
}
