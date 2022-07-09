package it.vincenzobiallo.coffeeship.exceptions;

/**
 * Gestione degli errori di Barche
 */
public class BarcaException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Eccezione Barca";
	
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
