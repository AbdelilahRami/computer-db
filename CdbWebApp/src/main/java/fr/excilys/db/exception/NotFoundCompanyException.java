package fr.excilys.db.exception;
public class NotFoundCompanyException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public NotFoundCompanyException(String message, Throwable cause) {
		super(message, cause);	
	}
	public NotFoundCompanyException(String message) {
		super(message);
	}
}
