package fr.excilys.db.exception;
public class NoCompanyFound extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public NoCompanyFound(String message) {
		super(message);
	}
	public NoCompanyFound(Throwable cause) {
		super(cause);
	}
}
