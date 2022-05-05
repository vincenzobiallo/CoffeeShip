package it.vincenzobiallo.coffeeship.exceptions;

public class BarcaException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final String TITLE = "";
	
	public BarcaException() {
		super();
	}
	
	public BarcaException(String message) {
		super(message);
	}
	
	public String getTitle() {
		return TITLE;
	}

}
