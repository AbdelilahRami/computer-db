package com.db.exception;

public class PageNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PageNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PageNotFoundException(String message) {
		super(message);

	}

	
	
}
