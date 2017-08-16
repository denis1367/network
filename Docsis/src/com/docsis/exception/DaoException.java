package com.docsis.exception;

@SuppressWarnings("serial")
public class DaoException extends Exception {

	public DaoException() {
	}

	public DaoException(String message) {
		super(message);
	}

	public DaoException(Throwable throwed) {
		super(throwed);
	}

	public DaoException(String message, Throwable throwed) {
		super(message, throwed);
	}
	
	@Override
	public String getMessage() {
		return "Ocorreu um erro ao acessar a base, contate seu fornecedor";

	}
	

}
