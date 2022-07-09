package it.vincenzobiallo.coffeeship.exceptions;

/**
 * Gestione degli errori di Operazioni
 */
public class OperazioneException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Eccezione Operazione";
	
	public OperazioneException() {
		super();
	}
	
	public OperazioneException(String message) {
		super(message);
	}
	
	public String getTitle() {
		return TITLE;
	}

}
