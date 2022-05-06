package it.vincenzobiallo.coffeeship.utenti;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.vincenzobiallo.coffeeship.exceptions.PersonaException;
import it.vincenzobiallo.coffeeship.utenti.clienti.Cliente;

public class Persona implements Comparable<Persona>, Cloneable {
	
	private String codice_fiscale;
	private String nome;
	private String cognome;
	private Calendar dataNascita;
	
	protected Persona(String codice_fiscale, String nome, String cognome, Calendar dataNascita) throws PersonaException {
		setCodiceFiscale(codice_fiscale);
		setNome(nome);
		setCognome(cognome);
		this.dataNascita = dataNascita;
	}
	
	public String getCodiceFiscale() {
		return codice_fiscale;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getCognome() {
		return cognome;
	}
	
	public String getDataNascita() {	
		SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dataFormat.format(dataNascita.getTime());
	}
	
	private void setCodiceFiscale(String codice_fiscale) throws PersonaException {
		
		if (codice_fiscale.length() != 16)
			throw new PersonaException("La lunghezza del Codice Fiscale è invalida! (Lunghezza: 16)");
		
		String regex = "^([A-Z]{6}[0-9LMNPQRSTUV]{2}[ABCDEHLMPRST]{1}[0-9LMNPQRSTUV]{2}[A-Z]{1}[0-9LMNPQRSTUV]{3}[A-Z]{1})$|([0-9]{11})$";
		Pattern pattern = Pattern.compile(regex);
		
		codice_fiscale = codice_fiscale.toUpperCase();
		
		Matcher matcher = pattern.matcher(codice_fiscale);
		
		if (!matcher.matches())
			throw new PersonaException("Formato Codice Fiscale non valido!");
			
		this.codice_fiscale = codice_fiscale.trim();
	}
	
	private void setNome(String nome) {	
		nome = nome.substring(0, 1).toUpperCase() + nome.substring(1);
		this.nome = nome.trim();
	}
	
	private void setCognome(String cognome) {		
		cognome = cognome.substring(0, 1).toUpperCase() + cognome.substring(1);
		this.cognome = cognome.trim();
	}

	@Override
	public int hashCode() {
		return Objects.hash(codice_fiscale);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		return Objects.equals(codice_fiscale, other.codice_fiscale);
	}
	
	@Override
	public int compareTo(Persona p) {
		
		if (this.getCognome().compareTo(p.getCognome()) <= 0) {
			
			if (this.getCognome().compareTo(p.getCognome()) < 0)
				return -1;
			else
				if (this.getNome().compareTo(p.getNome()) < 0)
					return -1;
				else
					return 1;
		}
		
		return 1;
	}
	
	@Override
	public Object clone() {
		
		Object clone = null;
		
		try {
			clone = super.clone();
		} catch (CloneNotSupportedException ex) {
			ex.printStackTrace();
		}
		
		return (Cliente) clone;
	}

}
