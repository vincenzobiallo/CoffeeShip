package it.vincenzobiallo.coffeeship.utils;

import javax.swing.JOptionPane;

import it.vincenzobiallo.coffeeship.CoffeeShip;

/**
 * Classe Interfaccia per poter gestire la comunicazione Uomo/Macchina Grafica
 */
public class MessageBox {
	
	/**
	 * Se vero viene utilizzata l'interfaccia
	 */
	private static boolean boxForm = true;
	
	/**
	 * Mostra un messaggio di avviso
	 * 
	 * @param title
	 * @param message
	 */
	public static final void showInformation(String title, String message) {
		
		if (title.isEmpty() || title == null)
			title = CoffeeShip.TITLE;
		
		if (boxForm)
			JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
		else
			System.out.println(String.format("[%s] %s", title, message));
	}
	
	/**
	 * Mostra un messaggio di allerta
	 * 
	 * @param title
	 * @param message
	 */
	public static final void showWarning(String title, String message) {
		
		if (title.isEmpty() || title == null)
			title = CoffeeShip.TITLE;
		
		if (boxForm)
			JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
		else
			System.out.println(String.format("<!> [%s] %s", title, message));
	}
	
	/**
	 * Mostra un messaggio di errore
	 * 
	 * @param title
	 * @param message
	 */
	public static final void showError(String title, String message) {
		
		if (title.isEmpty() || title == null)
			title = CoffeeShip.TITLE;
		
		if (boxForm)
			JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
		else
			System.err.println(String.format("<!> [%s] %s", title, message));
	}
	
	/**
	 * Chiede all'utente una domanda, a cui può rispondere positivamente o negativamente
	 * 
	 * @param title
	 * @param message
	 * @return true se ha risposto di si, false altrimenti
	 */
	public static final boolean askQuestion(String title, String message) {
		
		if (title.isEmpty() || title == null)
			title = CoffeeShip.TITLE;
		
		int answer = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
		
		if (answer == JOptionPane.YES_OPTION)
			return true;
		
		return false;
	}

}
