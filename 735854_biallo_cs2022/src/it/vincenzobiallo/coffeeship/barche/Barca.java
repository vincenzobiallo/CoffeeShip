package it.vincenzobiallo.coffeeship.barche;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.vincenzobiallo.coffeeship.barche.elementi.*;
import it.vincenzobiallo.coffeeship.exceptions.BarcaException;

/**
 * Classe astratta che funge da Interfaccia nel sistema, contiene tutte le informazioni di una barca e un array di Cabine per i differenti modelli
 */
public abstract class Barca implements Cloneable {
	
	private String numero_serie;
	private Scafo scafo;
	private Chiglia chiglia;
	private Deriva deriva;
	private Alberatura alberatura;
	private Timone timone;
	
	protected ModelloBarca modello;
	protected Cabina[] cabine;
	
	protected Barca(String numero_serie, Scafo scafo, Chiglia chiglia, Deriva deriva, Alberatura alberatura, Timone timone) throws BarcaException {
		this.setNumeroSerie(numero_serie);
		this.scafo = scafo;
		this.chiglia = chiglia;
		this.deriva = deriva;
		this.alberatura = alberatura;
		this.timone = timone;
		setupCabina();
	}
	
	/**
	 * Consente, in modo dinamico, di poter impostare una diversa tipologia di Cabina per ogni barca
	 */
	protected abstract void setupCabina();
	
	public String getNumeroSerie() {
		return numero_serie;
	}

	public Scafo getScafo() {
		return scafo;
	}
	
	public Chiglia getChiglia() {
		return chiglia;
	}

	public Deriva getDeriva() {
		return deriva;
	}

	public Alberatura getAlberatura() {
		return alberatura;
	}
	
	public Timone getTimone() {
		return timone;
	}
	
	public ModelloBarca getModello() {
		return modello;
	}
	
	public int getNumeroBagni() {
		
		int count = 0;
		
		for (Cabina cabina : cabine)
			count += cabina.getNumeroBagni();
		
		return count;
	}
	
	public int getNumeroLetti() {
		
		int count = 0;
		
		for (Cabina cabina : cabine)
			count += cabina.getNumeroLetti();
		
		return count;
	}
	
	public int getNumeroCucine() {
		
		int count = 0;
		
		for (Cabina cabina : cabine)
			if (cabina.possiedeCucina())
				count++;
		
		return count;
	}

	/**
	 * Consente di inserire il numero serie validandolo
	 * @param numero_serie
	 * @throws BarcaException
	 */
	protected void setNumeroSerie(String numero_serie) throws BarcaException {
		
		if (numero_serie.length() != 12)
			throw new BarcaException("Le cifre del Numero Di Serie non sono corrette! (Cifre Corrette: 12)");
		
		String regex = "[0-9]+";
		Pattern pattern = Pattern.compile(regex);
		
		Matcher matcher = pattern.matcher(numero_serie);
		
		if (!matcher.matches())
			throw new BarcaException("Il Numero Serie non è valido!");
			
		this.numero_serie = numero_serie.trim();
	}

	@Override
	public int hashCode() {
		return Objects.hash(numero_serie);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Barca other = (Barca) obj;
		return Objects.equals(numero_serie, other.numero_serie);
	}
	
	@Override
	public Object clone() {
		
		Object clone = null;
		
		try {
			clone = super.clone();
		} catch (CloneNotSupportedException ex) {
			ex.printStackTrace();
		}
		
		return (Barca) clone;
	}

}
