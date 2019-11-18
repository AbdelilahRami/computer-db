package fr.excilys.db.exception;
public class ComputerToDeleteNotFound extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ComputerToDeleteNotFound(String message, Throwable cause) {
		super(message, cause);
	}
	public ComputerToDeleteNotFound(String message) {
		super(message);
	}
}
