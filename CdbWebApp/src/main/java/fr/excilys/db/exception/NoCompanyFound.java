package fr.excilys.db.exception;
public class NoCompanyFound extends Exception{
	public NoCompanyFound(String message) {
		super(message);
	}
	public NoCompanyFound(Throwable cause) {
		super(cause);
	}
}
