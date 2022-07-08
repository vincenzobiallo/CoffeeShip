package it.vincenzobiallo.coffeeship.vendite;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import it.vincenzobiallo.coffeeship.barche.Barca;
import it.vincenzobiallo.coffeeship.exceptions.ContrattoException;
import it.vincenzobiallo.coffeeship.utenti.clienti.Cliente;
import it.vincenzobiallo.coffeeship.utenti.venditori.Venditore;

public class ContrattoNoleggio {
	
	private Barca barca;
	private Cliente cliente;
	private Venditore venditore;
	
	private Calendar dataInizio;
	private Calendar dataFine;
	
	private double canone;
	private double penale;
	
	private Calendar dataStipula;
	
	private boolean terminato = false;
	
	ContrattoNoleggio(Barca barca, Venditore venditore, Cliente cliente, double canone, double penale, Calendar dataInizio, Calendar dataFine, Calendar dataStipula, boolean terminato) throws ContrattoException {		
		
		if ((venditore == null) || (cliente == null))
			throw new ContrattoException("Dati Venditore/Cliente non validi!");
	
		this.barca = barca;
		this.venditore = venditore;
		this.cliente = cliente;
		
		if (dataFine.before(dataInizio))
			throw new ContrattoException("La data di fine non può essere precedente alla data di inizio!");
		
		if (dataInizio.equals(dataFine))
			throw new ContrattoException("Il noleggio non può terminare nello stesso giorno in cui è stato concesso!");
		
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.dataStipula = dataStipula;
		
		if ((canone < 0) || (penale < 0))
			throw new ContrattoException("Gli importi del Contratto non possono essere numeri negativi!");
		
		this.canone = canone;
		this.penale = penale;
		this.terminato = terminato;
	}

	public Barca getBarca() {
		return barca;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Venditore getVenditore() {
		return venditore;
	}

	public String getDataInizio() {
		SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dataFormat.format(dataInizio.getTime());
	}

	public String getDataFine() {
		SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dataFormat.format(dataFine.getTime());
	}
	
	public Date getDataStipula() {
		return dataStipula.getTime();
	}

	public double getCanone() {
		return canone;
	}

	public double getPenale() {
		return penale;
	}
	
	public boolean isTerminato() {
		return terminato;
	}
	
	public void setTerminato(boolean value) {
		this.terminato = value;
	}

}
