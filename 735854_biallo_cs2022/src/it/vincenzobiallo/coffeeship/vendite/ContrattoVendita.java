package it.vincenzobiallo.coffeeship.vendite;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import it.vincenzobiallo.coffeeship.barche.Barca;
import it.vincenzobiallo.coffeeship.exceptions.ContrattoException;
import it.vincenzobiallo.coffeeship.utenti.clienti.Cliente;
import it.vincenzobiallo.coffeeship.utenti.venditori.Venditore;

class ContrattoVendita {
	
	private Barca barca;
	private Cliente cliente;
	private Venditore venditore;
	private Calendar data;
	
	private double prezzo;
	
	public ContrattoVendita(Barca barca, Venditore venditore, Cliente cliente, double prezzo, Calendar data) throws ContrattoException {
		this.barca = barca;
		this.venditore = venditore;
		this.cliente = cliente;
		this.data = data;
		
		if ((venditore == null) || (cliente == null))
			throw new ContrattoException("Dati Venditore/Cliente non validi!");
		
		if (prezzo < 0)
			throw new ContrattoException("Il prezzo di vendita non può essere un numero negativo!");
		
		this.prezzo = prezzo;
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
	
	public String getData() {
		SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dataFormat.format(data.getTime());
	}
	
	public double getPrezzo() {
		return prezzo;
	}

}
