package it.vincenzobiallo.coffeeship.utenti.venditori;

import java.util.Calendar;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.vincenzobiallo.coffeeship.exceptions.PersonaException;
import it.vincenzobiallo.coffeeship.utenti.Persona;

public class Venditore extends Persona {
	
	private String codice_venditore;

	Venditore(String codice_venditore, String codice_fiscale, String nome, String cognome, Calendar dataNascita) throws PersonaException {
		super(codice_fiscale, nome, cognome, dataNascita);
		setCodiceVenditore(codice_venditore);
	}
	
	public String getCodiceVenditore() {
		return codice_venditore;
	}
	
	public void setCodiceVenditore(String codice_venditore) throws PersonaException {
		
		if (codice_venditore.length() != 8)
			throw new PersonaException("La lunghezza del Codice Venditore è invalida! (Lunghezza: 8)");
		
		String regex = "[A-Za-z0-9]+";
		Pattern pattern = Pattern.compile(regex);
		
		codice_venditore = codice_venditore.toUpperCase();
		
		Matcher matcher = pattern.matcher(codice_venditore);
		
		if (!matcher.matches())
			throw new PersonaException("Formato Codice Venditore non valido!");
			
		this.codice_venditore = codice_venditore.trim();
	}

	@Override
	public int hashCode() {
		final int prime = 8;
		int result = super.hashCode();
		result = prime * result + Objects.hash(codice_venditore);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Venditore other = (Venditore) obj;
		return Objects.equals(codice_venditore, other.codice_venditore);
	}


}
