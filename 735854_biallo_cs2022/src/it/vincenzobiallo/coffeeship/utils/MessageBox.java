package it.vincenzobiallo.coffeeship.utils;

import javax.swing.JOptionPane;

import it.vincenzobiallo.coffeeship.CoffeeShip;

public class MessageBox {
	
	private static boolean boxForm = true;
	
	public static final void showInformation(String title, String message) {
		
		if (title.isEmpty())
			title = CoffeeShip.TITLE;
		
		if (boxForm)
			JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
		else
			System.out.println(String.format("[%s] %s", title, message));
	}
	
	public static final void showWarning(String title, String message) {
		
		if (title.isEmpty())
			title = CoffeeShip.TITLE;
		
		if (boxForm)
			JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
		else
			System.out.println(String.format("<!> [%s] %s", title, message));
	}
	
	public static final void showError(String title, String message) {
		
		if (title.isEmpty())
			title = CoffeeShip.TITLE;
		
		if (boxForm)
			JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
		else
			System.err.println(String.format("<!> [%s] %s", title, message));
	}
	
	public static final boolean askQuestion(String title, String message) {
		
		if (title.isEmpty())
			title = CoffeeShip.TITLE;
		
		int answer = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
		
		if (answer == JOptionPane.YES_OPTION)
			return true;
		
		return false;
	}

}
