package fr.excilys.db.exception;
public class NoComputerFound extends Exception{
	public NoComputerFound(String message) {
		super(message);
	}
	public NoComputerFound(Throwable cause) {
		super(cause);
	}
}
