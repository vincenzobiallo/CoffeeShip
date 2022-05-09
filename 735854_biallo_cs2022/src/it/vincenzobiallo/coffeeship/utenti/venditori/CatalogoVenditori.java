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

public class CatalogoVenditori {
	
	private static Set<Venditore> venditori = new HashSet<Venditore>();
	
	public static Set<Venditore> getVenditori() {
		
		Set<Venditore> clone = new HashSet<Venditore>();
			
		for (Venditore venditore : venditori)
			clone.add((Venditore) venditore.clone());
		
		return clone;
	}
	
	public static Venditore getVenditore(String value) {
		
		for (Venditore venditore : venditori) {
			
			if (value.length() == 16) {
				if (venditore.getCodiceFiscale().equalsIgnoreCase(value))
					return venditore;
			} else {
				if (venditore.getCodiceFiscale().equalsIgnoreCase(value))
					return venditore;
			}
					
		}
		
		return null;
	}
	
	public static void caricaCatalogo() throws IOException, ParseException {

		BufferedReader reader = new BufferedReader(new FileReader("venditori.json"));
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
			writer = new BufferedWriter(new FileWriter("venditori.json"));
			writer.write(json.toString(4));
			writer.close();
		} catch (IOException ex) {
			MessageBox.showWarning("Creazione File", "Ho creato un nuovo file venditori.json!");
		}
		
	}
	
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
