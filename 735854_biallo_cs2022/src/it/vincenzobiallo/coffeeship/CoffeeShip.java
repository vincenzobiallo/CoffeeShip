package it.vincenzobiallo.coffeeship;

import java.awt.EventQueue;

import it.vincenzobiallo.coffeeship.barche.CatalogoBarche;
import it.vincenzobiallo.coffeeship.forms.HomeFrame;
import it.vincenzobiallo.coffeeship.utenti.clienti.CatalogoClienti;
import it.vincenzobiallo.coffeeship.utenti.venditori.CatalogoVenditori;
import it.vincenzobiallo.coffeeship.utils.MessageBox;

public class CoffeeShip {
	
	public static final String TITLE = "CoffeeShip";

	public static void main(String[] args) {
		
		if (!caricaFiles())
			System.exit(-1);
		
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
			MessageBox.showWarning(title, ex.getMessage());
			
			if(!MessageBox.askQuestion(title, "Vuoi creare un nuovo file?"))
				return false;
			
			CatalogoBarche.salvaCatalogo();
		}
		
		try {
			CatalogoClienti.caricaCatalogo();
		} catch (Exception ex) {
			MessageBox.showWarning(title, ex.getMessage());
			
			if(!MessageBox.askQuestion(title, "Vuoi creare un nuovo file?"))
				return false;
			
			CatalogoClienti.salvaCatalogo();
		}
		
		try {
			CatalogoVenditori.caricaCatalogo();
		} catch (Exception ex) {
			MessageBox.showWarning(title, ex.getMessage());
			
			if(!MessageBox.askQuestion(title, "Vuoi creare un nuovo file?"))
				return false;
			
			CatalogoVenditori.salvaCatalogo();
		}
		
		return true;
	}

}
