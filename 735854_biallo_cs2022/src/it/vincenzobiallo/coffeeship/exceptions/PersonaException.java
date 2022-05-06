package it.vincenzobiallo.coffeeship.exceptions;

public class PersonaException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Eccezione Persona";
	
	public PersonaException() {
		super();
	}
	
	public PersonaException(String message) {
		super(message);
	}
	
	public String getTitle() {
		return TITLE;
	}

}
