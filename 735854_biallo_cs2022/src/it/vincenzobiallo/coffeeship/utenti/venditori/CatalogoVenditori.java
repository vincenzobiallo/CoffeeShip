package it.vincenzobiallo.coffeeship.utenti.venditori;

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

import it.vincenzobiallo.coffeeship.exceptions.PersonaException;
import it.vincenzobiallo.coffeeship.utils.MessageBox;

/**
 * Catalogo che Gestisce i Venditori
 */
public class CatalogoVenditori {
	
	private static Set<Venditore> venditori = new HashSet<Venditore>();
	
	/**
	 * Ottieni la Lista dei Venditori
	 * @return Set di Venditori
	 */
	public static Set<Venditore> getVenditori() {
		
		Set<Venditore> clone = new HashSet<Venditore>();
			
		for (Venditore venditore : venditori)
			clone.add((Venditore) venditore.clone());
		
		return clone;
	}
	
	/**
	 * Ottiene una singola instanza di Venditore
	 * @param value: Questa istanza pul essere ottenuta dal codice fiscale o dal codice venditore
	 * @return Istanza se viene trovato, null altrimenti
	 */
	public static Venditore getVenditore(String value) {
		
		for (Venditore venditore : venditori) {
			
			if (value.length() == 16) {
				if (venditore.getCodiceFiscale().equalsIgnoreCase(value))
					return venditore;
			} else {
				if (venditore.getCodiceVenditore().equalsIgnoreCase(value))
					return venditore;
			}
					
		}
		
		return null;
	}
	
	/**
	 * Funzione che carica in memoria i dati contenuti nel file di archiviazione
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void caricaCatalogo() throws IOException, ParseException {

		BufferedReader reader = new BufferedReader(new FileReader("./files/venditori.json"));
		StringBuilder jsonString = new StringBuilder();

		String buffer;
		while ((buffer = reader.readLine()) != null)
			jsonString.append(buffer);
		reader.close();

		JSONArray json = new JSONArray(jsonString.toString());

		for (Object instance : json) {

			JSONObject obj = (JSONObject) instance;

			String codice_venditore = obj.getString("codice_venditore");
			String codice_fiscale = obj.getString("codice_fiscale");
			String nome = obj.getString("nome");
			String cognome = obj.getString("cognome");
			Date rawData = new SimpleDateFormat("dd/MM/yyyy").parse((String) obj.getString("data_nascita"));
			
			Calendar dataNascita = Calendar.getInstance();
			dataNascita.setTime(rawData);

			aggiungiVenditore(codice_venditore, codice_fiscale, nome, cognome, dataNascita);
		}

	}
	
	/**
	 * Funzione che salva su disco in formato JSON (per poter in futuro estenderlo sul web) i dati presenti in memoria
	 */
	public static void salvaCatalogo() {
		
		JSONArray json = new JSONArray();
		
		for (Venditore venditore : venditori) {
			
			JSONObject obj = new JSONObject();
			
			obj.put("codice_venditore", venditore.getCodiceVenditore());
			obj.put("codice_fiscale", venditore.getCodiceFiscale());
			obj.put("nome", venditore.getNome());
			obj.put("cognome", venditore.getCognome());
			obj.put("data_nascita", venditore.getDataNascita());
			
			json.put(obj);
		}

		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter("./files/venditori.json"));
			writer.write(json.toString(4));
			writer.close();
		} catch (IOException ex) {
			MessageBox.showWarning("Creazione File", "Ho creato un nuovo file venditori.json!");
		}
		
	}
	
	/**
	 * Funzione che consente di aggiungere un venditore, validandone i dati
	 * 
	 * @param codice_venditore
	 * @param codice_fiscale
	 * @param nome
	 * @param cognome
	 * @param dataNascita
	 * @return 1 se è stato inserito, 0 altrimenti, -1 con errore
	 */
	public static int aggiungiVenditore(String codice_venditore, String codice_fiscale, String nome, String cognome, Calendar dataNascita) {
		
		try {

			Venditore venditore = new Venditore(codice_venditore, codice_fiscale, nome, cognome, dataNascita);
			
			if (!venditori.contains(venditore)) {
				venditori.add(venditore);
				salvaCatalogo();
				return 1;			
			}
			
		} catch (PersonaException ex) {
			MessageBox.showWarning(ex.getTitle(), ex.getMessage());
			return -1;
		}
		
		return 0;
	}
	
	/**
	 * Modifica un venditore
	 * 
	 * @param codice_venditore
	 * @param codice_fiscale
	 * @param nome
	 * @param cognome
	 * @param dataNascita
	 * @return 1 se è stato modificato, 0 altrimenti
	 */
	public static int modificaVenditore(String codice_venditore, String codice_fiscale, String nome, String cognome, Calendar dataNascita) {
		
		try {

			Venditore venditore = new Venditore(codice_venditore, codice_fiscale, nome, cognome, dataNascita);
			
			if (venditori.contains(venditore)) {
				venditori.remove(venditore);
				venditori.add(venditore);
				salvaCatalogo();
				return 1;			
			}
			
		} catch (PersonaException ex) {
			MessageBox.showWarning(ex.getTitle(), ex.getMessage());
			return -1;
		}
		
		return 0;
	}
	
	/**
	 * Rimuove un Venditore dalla Memoria
	 * 
	 * @param value
	 * @return true se è stato rimosso, false altrimenti
	 */
	public static boolean rimuoviVenditore(String value) {
		
		Venditore venditore = getVenditore(value);
		
		if (venditore != null) {	
			venditori.remove(venditore);
			salvaCatalogo();
			return true;	
		}
		
		return false;
	}
	

}
