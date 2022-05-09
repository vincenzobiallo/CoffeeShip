package it.vincenzobiallo.coffeeship.exceptions;

public class ContrattoException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Eccezione Contratto";
	
	public ContrattoException() {
		super();
	}
	
	public ContrattoException(String message) {
		super(message);
	}
	
	public String getTitle() {
		return TITLE;
	}

}
