package it.vincenzobiallo.coffeeship.vendite;

import java.util.Calendar;
import java.util.Objects;

import it.vincenzobiallo.coffeeship.barche.Barca;
import it.vincenzobiallo.coffeeship.barche.CatalogoBarche;
import it.vincenzobiallo.coffeeship.exceptions.BarcaException;
import it.vincenzobiallo.coffeeship.exceptions.ContrattoException;
import it.vincenzobiallo.coffeeship.utenti.clienti.CatalogoClienti;
import it.vincenzobiallo.coffeeship.utenti.clienti.Cliente;
import it.vincenzobiallo.coffeeship.utenti.venditori.CatalogoVenditori;
import it.vincenzobiallo.coffeeship.utenti.venditori.Venditore;

public class Listino implements Cloneable {
	
	private Barca barca;
	
	private ContrattoVendita vendita = null;
	private double prezzo;
	
	Listino(String numero_serie, double prezzo, double canone) throws BarcaException {
		setBarca(numero_serie);
		setPrice(prezzo);
	}
	
	public Barca getBarca() {
		return barca;
	}
	
	public ContrattoVendita getContrattoVendita() {
		return vendita;
	}
	
	public boolean isVenduto() {
		return this.getContrattoVendita() != null;
	}
	
	public double getPrezzoStandard() {
		return prezzo;
	}
	
	private void setBarca(String numero_serie) throws BarcaException {
		
		Barca istance = CatalogoBarche.getBarca(numero_serie);
		
		if (istance == null)
			throw new BarcaException("Questa Barca non è presente nel Catalogo!");;
		
		this.barca = istance;
	}
	
	private void setPrice(double prezzo) throws BarcaException {
		
		if (prezzo <= 0)
			throw new BarcaException("Il prezzo di vendita di una Barca non può essere uguale o minore di 0!");
		
		this.prezzo = prezzo;		
	}
	
	public void setContrattoVendita(String codice_venditore, String codice_cliente, double prezzo_effettivo, Calendar data_vendita) throws ContrattoException {
		
		if (vendita != null)
			throw new ContrattoException("Questa Barca è già stata venduta!");
		
		Venditore venditore = CatalogoVenditori.getVenditore(codice_venditore);
		Cliente cliente = CatalogoClienti.getCliente(codice_cliente);		
		
		this.vendita = new ContrattoVendita(this.barca, venditore, cliente, prezzo_effettivo, data_vendita);
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

	@Override
	public int hashCode() {
		return Objects.hash(barca);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Listino other = (Listino) obj;
		return Objects.equals(barca, other.barca);
	}

}
