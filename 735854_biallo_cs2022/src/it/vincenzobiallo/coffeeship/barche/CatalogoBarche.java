package it.vincenzobiallo.coffeeship.barche;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import it.vincenzobiallo.coffeeship.barche.elementi.*;
import it.vincenzobiallo.coffeeship.exceptions.BarcaException;
import it.vincenzobiallo.coffeeship.utils.MessageBox;

public class CatalogoBarche {
	
	private static Set<Barca> barche = new HashSet<Barca>();
	
	public static Set<Barca> getBarche() {
		
		Set<Barca> clone = new HashSet<Barca>();
		
		for (Barca barca : barche)
			clone.add((Barca) barca.clone());
		
		return clone;
	}
	
	public static Barca getBarca(String numero_serie) {
		
		for (Barca barca : barche) {
			if (barca.getNumeroSerie().equalsIgnoreCase(numero_serie))
				return (Barca) barca.clone();
		}
		
		return null;
	}
	
	public static void caricaCatalogo() throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader("barche.json"));
		StringBuilder jsonString = new StringBuilder();

		String buffer;
		while ((buffer = reader.readLine()) != null)
			jsonString.append(buffer);
		reader.close();

		barche.clear();
		JSONArray json = new JSONArray(jsonString.toString());

		for (Object instance : json) {

			JSONObject obj = (JSONObject) instance;

			String numero_serie = obj.getString("numero_serie");
			Scafo scafo = Scafo.valueOf((String) obj.get("scafo"));
			Chiglia chiglia = Chiglia.valueOf((String) obj.get("chiglia"));
			Deriva deriva = Deriva.valueOf((String) obj.get("deriva"));
			Alberatura alberatura = Alberatura.valueOf((String) obj.get("alberatura"));
			Timone timone = Timone.valueOf((String) obj.get("timone"));
			ModelloBarca modello = ModelloBarca.valueOf((String) obj.get("modello"));

			aggiungiBarca(numero_serie, scafo, chiglia, deriva, alberatura, timone, modello);
		}
	}
	
	public static void salvaCatalogo() {
		
		JSONArray json = new JSONArray();
		
		for (Barca barca : barche) {
			
			JSONObject obj = new JSONObject();

			ModelloBarca modello = null;
			
			if (barca instanceof BarcaBase)
				modello = ModelloBarca.BASE;
			else if (barca instanceof BarcaMedia)
				modello = ModelloBarca.MEDIA;
			else if (barca instanceof BarcaAvanzata)
				modello = ModelloBarca.AVANZATA;
			
			obj.put("numero_serie", barca.getNumeroSerie());
			obj.put("scafo", barca.getScafo());
			obj.put("chiglia", barca.getChiglia());
			obj.put("deriva", barca.getDeriva());
			obj.put("alberatura", barca.getAlberatura());
			obj.put("timone", barca.getTimone());
			obj.put("modello", modello);
			
			json.put(obj);
		}
		
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter("barche.json"));
			writer.write(json.toString(4));
			writer.close();
		} catch (IOException ex) {
			MessageBox.showWarning("Creazione File", "Ho creato un nuovo file barche.json");
		}
					
	}
	
	public static int aggiungiBarca(String numero_serie, Scafo scafo, Chiglia chiglia, Deriva deriva, Alberatura alberatura, Timone timone, ModelloBarca modello) {
		
		Barca barca = null;
		
		try {
			
			switch(modello) {
			case BASE:
				barca = new BarcaBase(numero_serie, scafo, chiglia, deriva, alberatura, timone);
				break;
			case MEDIA:
				barca = new BarcaMedia(numero_serie, scafo, chiglia, deriva, alberatura, timone);
				break;
			case AVANZATA:
				barca = new BarcaAvanzata(numero_serie, scafo, chiglia, deriva, alberatura, timone);
				break;
			default:
				throw new BarcaException("Il modello della Barca non è valido!");
			}
			
			if (!barche.contains(barca)) {	
				barche.add(barca);
				salvaCatalogo();
				return 1;		
			}
					
		} catch(BarcaException ex) {
			MessageBox.showError("Aggiungi Barca", ex.getMessage());
			return -1;
		}
			
		return 0;
	}
	
	public static int modificaBarca(String numero_serie, Scafo scafo, Chiglia chiglia, Deriva deriva, Alberatura alberatura, Timone timone, ModelloBarca modello) {
		
		Barca barca = null;
		
		try {
			
			switch(modello) {
			case BASE:
				barca = new BarcaBase(numero_serie, scafo, chiglia, deriva, alberatura, timone);
				break;
			case MEDIA:
				barca = new BarcaMedia(numero_serie, scafo, chiglia, deriva, alberatura, timone);
				break;
			case AVANZATA:
				barca = new BarcaAvanzata(numero_serie, scafo, chiglia, deriva, alberatura, timone);
				break;
			default:
				throw new BarcaException("Il modello della Barca non è valido!");
			}
			
			if (barche.contains(barca)) {
				barche.remove(barca);
				barche.add(barca);
				salvaCatalogo();
				return 1;		
			}
					
		} catch(BarcaException ex) {
			MessageBox.showError("Modifica Barca", ex.getMessage());
			return -1;
		}
			
		return 0;
	}
	
	public static boolean rimuoviBarca(String numero_serie) {
		
		Barca barca = getBarca(numero_serie);
		
		if (barca != null) {	
			barche.remove(barca);
			salvaCatalogo();
			return true;
		}
		
		return false;
	}

}
