package fr.excilys.db.exception;

public class NotFoundCompanyException extends Exception {

	public NotFoundCompanyException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public NotFoundCompanyException(String message) {
		super(message);
		
	}
	

}
