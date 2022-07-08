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
import it.vincenzobiallo.coffeeship.exceptions.VenditaException;
import it.vincenzobiallo.coffeeship.utils.MessageBox;

public class CatalogoListini {
	
	private static Set<Listino> listini = new HashSet<Listino>();
	
	public static Set<Listino> getListini() {
		
		Set<Listino> clone = new HashSet<Listino>();
		
		for (Listino articolo : listini)
			clone.add((Listino) articolo.clone());
		
		return clone;
	}
	
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
	
	public static Listino getListino(Barca barca) {
		
		for (Listino listino : listini) {
			if (listino.getBarca().equals(barca))
				return listino;
		}
		
		return null;
	}
	
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
	
	public static void caricaCatalogo() throws BarcaException, ContrattoException, IOException, ParseException {

		BufferedReader reader = new BufferedReader(new FileReader("listini.json"));
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
			writer = new BufferedWriter(new FileWriter("listini.json"));
			writer.write(json.toString(4));
			writer.close();
		} catch (IOException ex) {
			MessageBox.showWarning("Creazione File", "Ho creato un nuovo file listini.json!");
		}
		
	}
	
	public static boolean vendiBarca(String numero_serie, String codice_cliente, String codice_venditore, double prezzo, Calendar data) throws VenditaException, ContrattoException {
		
		Barca barca = CatalogoBarche.getBarca(numero_serie);
		Listino listino = getListino(barca);
		
		if (listino == null)
			throw new VenditaException("La Barca selezionata non ha un listino!");
		
		if (listino.isVenduto())
			return false;
		
		listino.setContrattoVendita(codice_venditore, codice_cliente, prezzo, data);
		return true;
	}
	
	public static boolean noleggiaBarca(String numero_serie, String codice_cliente, String codice_venditore, double canone, double penale, Calendar dataInizio, Calendar dataFine) throws VenditaException, ContrattoException {
		
		Barca barca = CatalogoBarche.getBarca(numero_serie);
		Listino listino = getListino(barca);
		
		if (listino == null)
			throw new VenditaException("La Barca selezionata non ha un listino!");
		
		try {
			if (listino.isNoleggiato(dataInizio.getTime(), dataFine.getTime()))
				throw new ContrattoException("La Barca è già sotto noleggio in todo o in parte nel periodo selezionato!");
		} catch (ParseException ex) {
			ex.printStackTrace();
			return false;
		}
		
		listino.addContrattoNoleggio(codice_venditore, codice_cliente, canone, penale, dataInizio, dataFine, false);
		return true;
	}

}
