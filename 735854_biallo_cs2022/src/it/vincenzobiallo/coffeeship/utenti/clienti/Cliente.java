package it.vincenzobiallo.coffeeship.utenti.clienti;

import java.util.Calendar;

import it.vincenzobiallo.coffeeship.exceptions.PersonaException;
import it.vincenzobiallo.coffeeship.utenti.Persona;

public class Cliente extends Persona {

	Cliente(String codice_fiscale, String nome, String cognome, Calendar dataNascita) throws PersonaException {
		super(codice_fiscale, nome, cognome, dataNascita);
	}
	
}
