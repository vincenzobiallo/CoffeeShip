package it.vincenzobiallo.coffeeship.vendite;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
	private double prezzo;
	private double canone;
	
	private ContrattoVendita vendita = null;
	private List<ContrattoNoleggio> noleggi = new ArrayList<ContrattoNoleggio>();
	
	Listino(String numero_serie, double prezzo, double canone) throws BarcaException {
		setBarca(numero_serie);
		setPrice(prezzo);
		setCanone(canone);
	}
	
	public Barca getBarca() {
		return barca;
	}
	
	public double getPrezzoStandard() {
		return prezzo;
	}
	
	public double getCanoneStandard() {
		return canone;
	}
	
	public ContrattoVendita getContrattoVendita() {
		return vendita;
	}
	
	public boolean isVenduto() {
		return this.getContrattoVendita() != null;
	}
	
	public ContrattoNoleggio[] getContrattiNoleggio() {
		
		int count = noleggi.size();
		
		if (count == 0)
			return new ContrattoNoleggio[0];
		
		ContrattoNoleggio[] contratti = new ContrattoNoleggio[count];
		
		return noleggi.toArray(contratti);
	}
	
	public boolean isNoleggiato() {
		
		if (noleggi.size() != 0)
			return true;
		
		return false;	
	}
	
	public boolean isNoleggiato(Date dataInizio, Date dataFine) throws ParseException {
		
		for (ContrattoNoleggio noleggio : noleggi) {
			
			Date inizioNoleggio = new SimpleDateFormat("dd/MM/yyyy").parse(noleggio.getDataInizio());
			Date fineNoleggio = new SimpleDateFormat("dd/MM/yyyy").parse(noleggio.getDataFine());
			
			boolean overlaps = false;
			
			if (dataInizio.after(inizioNoleggio) && dataInizio.before(fineNoleggio))
				overlaps = true;
			
			if (dataFine.after(inizioNoleggio) && dataFine.before(fineNoleggio))
				overlaps = true;
			
			if (overlaps)
				return true;
		}
		
		return false;
		
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
	
	private void setCanone(double canone) throws BarcaException {
		
		if (canone <= 0)
			throw new BarcaException("Il canone di noleggio di una Barca non può essere uguale o minore di 0!");
		
		this.canone = canone;
	}
	
	public void setContrattoVendita(String codice_venditore, String codice_cliente, double prezzo_effettivo, Calendar data_vendita) throws ContrattoException {
		
		if (vendita != null)
			throw new ContrattoException("Questa Barca è già stata venduta!");
		
		Venditore venditore = CatalogoVenditori.getVenditore(codice_venditore);
		Cliente cliente = CatalogoClienti.getCliente(codice_cliente);
		
		this.vendita = new ContrattoVendita(this.barca, venditore, cliente, prezzo_effettivo, data_vendita);
	}
	
	public void addContrattoNoleggio(String codice_venditore, String codice_cliente, double canone, double penale, Calendar dataInizio, Calendar dataFine, boolean terminato) throws ContrattoException {
		
		Venditore venditore = CatalogoVenditori.getVenditore(codice_venditore);
		Cliente cliente = CatalogoClienti.getCliente(codice_cliente);
		
		Calendar today = Calendar.getInstance();
		today.setTime(Calendar.getInstance().getTime());
		
		this.noleggi.add(new ContrattoNoleggio(this.barca, venditore, cliente, canone, penale, dataInizio, dataFine, today, terminato));
	}
	
	@Override
	public Object clone() {
		
		Object clone = null;
		
		try {
			clone = super.clone();
		} catch (CloneNotSupportedException ex) {
			ex.printStackTrace();
		}
		
		return (Listino) clone;
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
