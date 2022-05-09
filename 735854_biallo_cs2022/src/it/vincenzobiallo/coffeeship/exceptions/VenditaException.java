package it.vincenzobiallo.coffeeship.exceptions;

public class VenditaException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Eccezione Vendita";
	
	public VenditaException() {
		super();
	}
	
	public VenditaException(String message) {
		super(message);
	}
	
	public String getTitle() {
		return TITLE;
	}

}
