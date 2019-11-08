package fr.excilys.db.exception;
public class NoComputerFound extends Exception{
	private static final long serialVersionUID = 1L;
	public NoComputerFound(String message) {
		super(message);
	}
	public NoComputerFound(Throwable cause) {
		super(cause);
	}
}
