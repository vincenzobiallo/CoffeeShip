package it.vincenzobiallo.coffeeship.utenti.clienti;

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

public class CatalogoClienti {
	
	private static Set<Cliente> clienti = new HashSet<Cliente>();
	
	/**
	 * Ottiene la lista di tutti i clienti nell'archivio
	 * @return Set di Clienti
	 */
	public static Set<Cliente> getClienti() {
		
		Set<Cliente> clone = new HashSet<Cliente>();
			
		for (Cliente cliente : clienti)
			clone.add((Cliente) cliente.clone());
		
		return clone;
	}
	
	/**
	 * Ottiene la singola istanza di un Cliente, grazie al suo codice identificativo: il codice fiscale
	 * @param codice_fiscale
	 * @return Istanza di Cliente se viene trovato, null altrimenti
	 */
	public static Cliente getCliente(String codice_fiscale) {
		
		for (Cliente cliente : clienti) {
			if (cliente.getCodiceFiscale().equalsIgnoreCase(codice_fiscale))
				return cliente;
		}
		
		return null;
	}
	
	/**
	 * Funzione che carica in memoria i dati contenuti nel file di archiviazione
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void caricaCatalogo() throws IOException, ParseException {

		BufferedReader reader = new BufferedReader(new FileReader("clienti.json"));
		StringBuilder jsonString = new StringBuilder();

		String buffer;
		while ((buffer = reader.readLine()) != null)
			jsonString.append(buffer);
		reader.close();

		JSONArray json = new JSONArray(jsonString.toString());

		for (Object instance : json) {

			JSONObject obj = (JSONObject) instance;

			String codice_fiscale = obj.getString("codice_fiscale");
			String nome = obj.getString("nome");
			String cognome = obj.getString("cognome");
			Date rawData = new SimpleDateFormat("dd/MM/yyyy").parse((String) obj.getString("data_nascita"));
			
			Calendar dataNascita = Calendar.getInstance();
			dataNascita.setTime(rawData);

			aggiungiCliente(codice_fiscale, nome, cognome, dataNascita);
		}

	}
	
	/**
	 * Funzione che salva su disco in formato JSON (per poter in futuro estenderlo sul web) i dati presenti in memoria
	 */
	public static void salvaCatalogo() {
		
		JSONArray json = new JSONArray();
		
		for (Cliente cliente : clienti) {
			
			JSONObject obj = new JSONObject();
			
			obj.put("codice_fiscale",cliente.getCodiceFiscale());
			obj.put("nome", cliente.getNome());
			obj.put("cognome", cliente.getCognome());
			obj.put("data_nascita", cliente.getDataNascita());
			
			json.put(obj);
		}

		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter("clienti.json"));
			writer.write(json.toString(4));
			writer.close();
		} catch (IOException ex) {
			MessageBox.showWarning("Creazione File", "Ho creato un nuovo file clienti.json!");
		}
		
	}
	
	/**
	 * Funzione che consente di aggiungere un cliente, validandone i dati
	 * 
	 * @param codice_fiscale
	 * @param nome
	 * @param cognome
	 * @param dataNascita
	 * @return 1 se il cliente è stato inserito, 0 se era già presente, -1 se c'è stato un errore.
	 */
	public static int aggiungiCliente(String codice_fiscale, String nome, String cognome, Calendar dataNascita) {
		
		try {

			Cliente cliente = new Cliente(codice_fiscale, nome, cognome, dataNascita);
			
			if (!clienti.contains(cliente)) {
				clienti.add(cliente);
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
	 * Funzione che consente di modificare un cliente
	 * 
	 * @param codice_fiscale
	 * @param nome
	 * @param cognome
	 * @param dataNascita
	 * @return 1 se il cliente è stato modificato, 0 altrimenti, -1 se c'è stato un errore
	 */
	public static int modificaCliente(String codice_fiscale, String nome, String cognome, Calendar dataNascita) {
		
		try {

			Cliente cliente = new Cliente(codice_fiscale, nome, cognome, dataNascita);
			
			if (clienti.contains(cliente)) {
				clienti.remove(cliente);
				clienti.add(cliente);
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
	 * Elimina un cliente dalla memoria
	 * 
	 * @param codice_fiscale
	 * @return true se è stato eliminato, false altrimenti
	 */
	public static boolean rimuoviCliente(String codice_fiscale) {
		
		Cliente cliente = getCliente(codice_fiscale);
		
		if (cliente != null) {	
			clienti.remove(cliente);
			salvaCatalogo();
			return true;	
		}
		
		return false;
	}

}
