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
	
	private static Listino getListino(Barca barca) {
		
		for (Listino listino : listini) {
			if (listino.getBarca().equals(barca))
				return listino;
		}
		
		return null;
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
			//JSONObject noleggio = obj.getJSONObject("contratto_noleggio");
			
			if (vendita != null) {
				
				String codice_venditore = vendita.getString("venditore");
				String codice_cliente = vendita.getString("cliente");
				double prezzo_effettivo = vendita.getDouble("prezzo");
				Date rawData = new SimpleDateFormat("dd/MM/yyyy").parse((String) obj.getString("data"));
				
				Calendar data = Calendar.getInstance();
				data.setTime(rawData);				
				
				listino.setContrattoVendita(codice_venditore, codice_cliente, prezzo_effettivo, data);
				
			}
			
		}

	}
	
	public static void salvaCatalogo() {
		
		JSONArray json = new JSONArray();
		
		for (Listino listino : listini) {
			
			JSONObject obj = new JSONObject();
			
			obj.put("numero_barca", listino.getBarca().getNumeroSerie());
			obj.put("prezzo_standard", listino.getPrezzoStandard());
			
			if (listino.isVenduto()) {
				
				JSONObject vendita = new JSONObject();
				
				vendita.put("venditore", listino.getContrattoVendita().getVenditore().getCodiceVenditore());
				vendita.put("cliente", listino.getContrattoVendita().getCliente().getCodiceFiscale());
				vendita.put("prezzo", listino.getContrattoVendita().getPrezzo());
				vendita.put("data", listino.getContrattoVendita().getData());
				
				obj.put("contratto_vendita", vendita);
				
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
	
	public void vendiBarca(String numero_serie, String codice_cliente, String codice_venditore, double prezzo, Calendar data) throws VenditaException, ContrattoException {
		
		Barca barca = CatalogoBarche.getBarca(numero_serie);
		Listino listino = getListino(barca);
		if (listino == null)
			throw new VenditaException("La Barca selezionata non ha un listino!");
		
		listino.setContrattoVendita(codice_venditore, codice_cliente, prezzo, data);
	}

}
