package it.vincenzobiallo.coffeeship.vendite;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import it.vincenzobiallo.coffeeship.barche.Barca;
import it.vincenzobiallo.coffeeship.barche.CatalogoBarche;
import it.vincenzobiallo.coffeeship.barche.ModelloBarca;
import it.vincenzobiallo.coffeeship.exceptions.BarcaException;
import it.vincenzobiallo.coffeeship.exceptions.ContrattoException;
import it.vincenzobiallo.coffeeship.exceptions.OperazioneException;
import it.vincenzobiallo.coffeeship.utenti.clienti.CatalogoClienti;
import it.vincenzobiallo.coffeeship.utenti.clienti.Cliente;
import it.vincenzobiallo.coffeeship.utenti.venditori.CatalogoVenditori;
import it.vincenzobiallo.coffeeship.utenti.venditori.Venditore;
import it.vincenzobiallo.coffeeship.utils.MessageBox;

/**
 * Catalogo che contiene tutti i Listini Associati alle Barche
 */
public class CatalogoListini {
	
	private static Set<Listino> listini = new HashSet<Listino>();
	
	/**
	 * Numero massimo giornaliero di noleggi possibili per un cliente
	 */
	private final static int MAX_CLIENTE = 5;
	/**
	 * Numero massimo di noleggi eseguibili da un venditore
	 */
	private final static int MAX_VENDITORE = 25;
	
	/**
	 * Ottieni la lista di tutti i listini presenti nel sistema
	 * @return Set di Listini
	 */
	public static Set<Listino> getListini() {
		
		Set<Listino> clone = new HashSet<Listino>();
		
		for (Listino articolo : listini)
			clone.add((Listino) articolo.clone());
		
		return clone;
	}
	
	/**
	 * Ottiene una lista di tutti i listini, filtrata solo per i listini che sono disponibili per eseguire operazioni (non sono stati venduti)
	 * @param vendita se true consente di operare su barche medie e avanzate (vendite), se false su quelle base (noleggi)
	 * @return Set di Listini
	 */
	public static Set<Listino> getArticoli(boolean vendita) {
		
		Set<Listino> clone = new HashSet<Listino>();
		
		for (Listino articolo : listini) {
			if (!articolo.isVenduto()) {
				if (vendita) {
					if ((articolo.getBarca().getModello() == ModelloBarca.MEDIA) || (articolo.getBarca().getModello() == ModelloBarca.AVANZATA))
						clone.add((Listino) articolo.clone());
				} else {
					if ((articolo.getBarca().getModello() == ModelloBarca.BASE))
						clone.add((Listino) articolo.clone());
				}
			}		
		}
			
		
		return clone;
	}
	
	/**
	 * Consente di Ottenere una singola Istanza di Listino partendo da un'istanza di Barca
	 * @param barca
	 * @return Instanza di Barca se disponibile, null altrimenti
	 */
	public static Listino getListino(Barca barca) {
		
		for (Listino listino : listini) {
			if (listino.getBarca().equals(barca))
				return listino;
		}
		
		return null;
	}
	
	/**
	 * Consente di Associare un Listino ad una Barca in Anagrafica
	 * @param numero_serie
	 * @param prezzo
	 * @param canone
	 * @return true se è stato aggiunto, false altrimenti
	 */
	public static boolean aggiungiListino(String numero_serie, double prezzo, double canone) {
		
		try {
			Listino listino = new Listino(numero_serie, prezzo, canone);
			listini.remove(listino);
			listini.add(listino);
		} catch (BarcaException ex) {
			MessageBox.showError(null, ex.getMessage());
			return false;
		}
		
		return true;
	}
	
	/**
	 * Carica in memoria il catalogo su file JSON
	 * @throws BarcaException
	 * @throws ContrattoException
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void caricaCatalogo() throws BarcaException, ContrattoException, IOException, ParseException {

		BufferedReader reader = new BufferedReader(new FileReader("./files/listini.json"));
		StringBuilder jsonString = new StringBuilder();

		String buffer;
		while ((buffer = reader.readLine()) != null)
			jsonString.append(buffer);
		reader.close();

		JSONArray json = new JSONArray(jsonString.toString());

		for (Object instance : json) {

			JSONObject obj = (JSONObject) instance;

			String numero_barca = obj.getString("numero_barca");
			double prezzo = obj.getDouble("prezzo_standard");
			double canone = obj.getDouble("canone_standard");
			
			Listino listino = new Listino(numero_barca, prezzo, canone);
			
			JSONObject vendita = obj.getJSONObject("contratto_vendita");
			JSONArray noleggi = obj.getJSONArray("contratti_noleggio");
			
			if (!vendita.isEmpty()) {
				
				String codice_venditore = vendita.getString("venditore");
				String codice_cliente = vendita.getString("cliente");
				double prezzo_effettivo = vendita.getDouble("prezzo");
				Date rawData = new SimpleDateFormat("dd/MM/yyyy").parse((String) vendita.getString("data"));
				
				Calendar data = Calendar.getInstance();
				data.setTime(rawData);				
				
				listino.setContrattoVendita(codice_venditore, codice_cliente, prezzo_effettivo, data);
			}
			
			if (!noleggi.isEmpty()) {

				for (Object subinstance : noleggi) {
					
					JSONObject subobj = (JSONObject) subinstance;
					
					String codice_venditore = subobj.getString("venditore");
					String codice_cliente = subobj.getString("cliente");
					Date rawDataInizio = new SimpleDateFormat("dd/MM/yyyy").parse((String) subobj.getString("data_inizio"));
					Date rawDataFine = new SimpleDateFormat("dd/MM/yyyy").parse((String) subobj.getString("data_fine"));
					double canone_eff = subobj.getDouble("canone");
					double penale = subobj.getDouble("penale");
					boolean terminato = subobj.getBoolean("terminato");
					
					Calendar dataInizio = Calendar.getInstance();
					dataInizio.setTime(rawDataInizio);
					
					Calendar dataFine = Calendar.getInstance();
					dataFine.setTime(rawDataFine);
					
					listino.addContrattoNoleggio(codice_venditore, codice_cliente, canone_eff, penale, dataInizio, dataFine, terminato);				
				}
				
			}
			
			listini.add(listino);
			
		}

	}
	
	/**
	 * Salva su disco, in formato JSON i dati presenti in memoria
	 */
	public static void salvaCatalogo() {
		
		JSONArray json = new JSONArray();
		
		for (Listino listino : listini) {
			
			JSONObject obj = new JSONObject();
			
			obj.put("numero_barca", listino.getBarca().getNumeroSerie());
			obj.put("prezzo_standard", listino.getPrezzoStandard());
			obj.put("canone_standard", listino.getCanoneStandard());
			
			if (listino.isVenduto()) {
				
				JSONObject vendita = new JSONObject();
				
				vendita.put("venditore", listino.getContrattoVendita().getVenditore().getCodiceVenditore());
				vendita.put("cliente", listino.getContrattoVendita().getCliente().getCodiceFiscale());
				vendita.put("prezzo", listino.getContrattoVendita().getPrezzo());
				vendita.put("data", listino.getContrattoVendita().getData());
				
				obj.put("contratto_vendita", vendita);
				
			} else {
				obj.put("contratto_vendita", new JSONObject());
			}
			
			if (listino.getContrattiNoleggio().length != 0) {
				
				JSONArray noleggi = new JSONArray();
				
				for (ContrattoNoleggio contratto : listino.getContrattiNoleggio()) {
					
					JSONObject instance = new JSONObject();
					
					instance.put("venditore", contratto.getVenditore().getCodiceVenditore());
					instance.put("cliente", contratto.getCliente().getCodiceFiscale());
					instance.put("data_inizio", contratto.getDataInizio());
					instance.put("data_fine", contratto.getDataFine());
					instance.put("canone", contratto.getCanone());
					instance.put("penale", contratto.getPenale());
					instance.put("terminato", contratto.isTerminato());
					
					noleggi.put(instance);
				}
				
				obj.put("contratti_noleggio", noleggi);
			} else {
				obj.put("contratti_noleggio", new JSONArray());
			}

			json.put(obj);
		}

		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter("./files/listini.json"));
			writer.write(json.toString(4));
			writer.close();
		} catch (IOException ex) {
			MessageBox.showWarning("Creazione File", "Ho creato un nuovo file listini.json!");
		}
		
	}
	
	/**
	 * Vende una Barca
	 * 
	 * @param numero_serie
	 * @param codice_cliente
	 * @param codice_venditore
	 * @param prezzo
	 * @param data
	 * @return true se è stata venduta, false altrimenti
	 * @throws OperazioneException
	 * @throws ContrattoException
	 */
	public static boolean vendiBarca(String numero_serie, String codice_cliente, String codice_venditore, double prezzo, Calendar data) throws OperazioneException, ContrattoException {
		
		Barca barca = CatalogoBarche.getBarca(numero_serie);
		Listino listino = getListino(barca);
		
		if (listino == null)
			throw new OperazioneException("La Barca selezionata non ha un listino!");
		
		if (listino.isVenduto())
			return false;
		
		listino.setContrattoVendita(codice_venditore, codice_cliente, prezzo, data);
		return true;
	}
	
	/**
	 * Consente di noleggiare una barca
	 * @param numero_serie
	 * @param codice_cliente
	 * @param codice_venditore
	 * @param canone
	 * @param penale
	 * @param dataInizio
	 * @param dataFine
	 * @return true se è stata noleggiata, false se non è stata possibile noleggiarla
	 * @throws OperazioneException
	 * @throws ContrattoException
	 */
	public static boolean noleggiaBarca(String numero_serie, String codice_cliente, String codice_venditore, double canone, double penale, Calendar dataInizio, Calendar dataFine) throws OperazioneException, ContrattoException {
		
		Barca barca = CatalogoBarche.getBarca(numero_serie);
		Listino listino = getListino(barca);
		
		if (listino == null)
			throw new OperazioneException("La Barca selezionata non ha un listino!");
		
		try {
			if (listino.isNoleggiato(dataInizio.getTime(), dataFine.getTime()))
				throw new ContrattoException("La Barca è già sotto noleggio in todo o in parte nel periodo selezionato!");
		} catch (ParseException ex) {
			ex.printStackTrace();
			return false;
		}
		
		if (isLocked(CatalogoClienti.getCliente(codice_cliente))) 
			throw new OperazioneException("Il cliente selezionato ha raggiunto il massimo di operazioni eseguibili oggi!");
		
		if (isLocked(CatalogoVenditori.getVenditore(codice_venditore))) 
			throw new OperazioneException("Il venditore selezionato ha raggiunto il massimo di operazioni eseguibili oggi!");
		
		listino.addContrattoNoleggio(codice_venditore, codice_cliente, canone, penale, dataInizio, dataFine, false);
		return true;
	}
	
	/**
	 * Consente di individuare se un determinato cliente è bloccato o no dal numero massimo di noleggi giornalieri
	 * @param cliente
	 * @return true se è bloccato, false altrimenti
	 */
	public static boolean isLocked(Cliente cliente) {
		
		int count = 0;
		
		Calendar today = Calendar.getInstance();
		today.setTime(Calendar.getInstance().getTime());
		
		for (Listino listino : getArticoli(false)) {
			for (ContrattoNoleggio contratto : listino.getContrattiNoleggio())
				if (contratto.getCliente().equals(cliente) && contratto.getDataStipula().equals(today.getTime())) {
					count++;
				}
		}
		
		if (count >= MAX_CLIENTE)
			return true;
		
		return false;
	}
	
	/**
	 * Consente di individuare se un determinato venditore è bloccato o no dal numero massimo di noleggi giornalieri
	 * @param venditore
	 * @return true se è bloccato, false altrimenti
	 */
	public static boolean isLocked(Venditore venditore) {
		
		int count = 0;
		
		Calendar today = Calendar.getInstance();
		today.setTime(Calendar.getInstance().getTime());
		
		for (Listino listino : getArticoli(false)) {
			for (ContrattoNoleggio contratto : listino.getContrattiNoleggio())
				if (contratto.getVenditore().equals(venditore) && contratto.getDataStipula().equals(today.getTime())) {
					count++;
				}
		}
		
		if (count >= MAX_VENDITORE)
			return true;
		
		return false;
	}

}
