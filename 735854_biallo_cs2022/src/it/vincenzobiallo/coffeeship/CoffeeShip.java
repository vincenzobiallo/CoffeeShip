package it.vincenzobiallo.coffeeship;

import java.awt.EventQueue;

import it.vincenzobiallo.coffeeship.barche.CatalogoBarche;
import it.vincenzobiallo.coffeeship.forms.HomeFrame;
import it.vincenzobiallo.coffeeship.utenti.clienti.CatalogoClienti;
import it.vincenzobiallo.coffeeship.utenti.venditori.CatalogoVenditori;
import it.vincenzobiallo.coffeeship.utils.MessageBox;
import it.vincenzobiallo.coffeeship.vendite.CatalogoListini;

public class CoffeeShip {
	
	public static final String TITLE = "CoffeeShip";

	public static void main(String[] args) {
		
		if (!caricaFiles()) {
			MessageBox.showError("CoffeeShip", "La memoria del programma non è stata caricata!\nControlla i file e riprova..");
			System.exit(-1);
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeFrame home = new HomeFrame();
					home.getMainFrame().setVisible(true);
				} catch (Exception ex) {
					MessageBox.showError(null, ex.getMessage());
				}
			}
		});
		
	}
	
	private static boolean caricaFiles() {
		
		String title = "Caricamento File";
		
		try {
			CatalogoBarche.caricaCatalogo();
		} catch (Exception ex) {
			MessageBox.showWarning(title, "Catalogo Barche" + " inesistente o danneggiato!");
			
			if(!MessageBox.askQuestion(title, "Vuoi crearne uno nuovo?\nQuesta azione è irreversibile, clicca no se vuoi controllarlo manualmente!"))
				return false;
			
			CatalogoBarche.salvaCatalogo();
		}
		
		try {
			CatalogoClienti.caricaCatalogo();
		} catch (Exception ex) {
			MessageBox.showWarning(title, "Catalogo Clienti" + " inesistente o danneggiato!");
			
			if(!MessageBox.askQuestion(title, "Vuoi crearne uno nuovo?\nQuesta azione è irreversibile, clicca no se vuoi controllarlo manualmente!"))
				return false;
			
			CatalogoClienti.salvaCatalogo();
		}
		
		try {
			CatalogoVenditori.caricaCatalogo();
		} catch (Exception ex) {
			MessageBox.showWarning(title, "Catalogo Venditori" + " inesistente o danneggiato!");
			
			if(!MessageBox.askQuestion(title, "Vuoi crearne uno nuovo?\nQuesta azione è irreversibile, clicca no se vuoi controllarlo manualmente!"))
				return false;
			
			CatalogoVenditori.salvaCatalogo();
		}
		
		try {
			CatalogoListini.caricaCatalogo();
		} catch (Exception ex) {
			MessageBox.showWarning(title, "Catalogo Listini" + " inesistente o danneggiato!");
			
			if(!MessageBox.askQuestion(title, "Vuoi crearne uno nuovo?\nQuesta azione è irreversibile, clicca no se vuoi controllarlo manualmente!"))
				return false;
			
			CatalogoListini.salvaCatalogo();
		}
		
		return true;
	}

}
