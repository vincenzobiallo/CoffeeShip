package it.vincenzobiallo.coffeeship.forms;

import javax.swing.JFrame;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JSeparator;

import it.vincenzobiallo.coffeeship.forms.utenti.FormClienteFrame;
import it.vincenzobiallo.coffeeship.forms.utenti.FormVenditoreFrame;
import java.awt.Dialog.ModalExclusionType;

public class HomeFrame {

	public JFrame main;
	public static final String TITLE = "CoffeeShip";

	public HomeFrame() {
		main = new JFrame();
		main.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		main.setResizable(false);
		main.setTitle(TITLE);
		main.setBounds(100, 100, 450, 300);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		main.setJMenuBar(menuBar);
			
		JMenu menuUtenti = new JMenu("Menu Utenti");
		menuUtenti.setMnemonic('U');
		menuBar.add(menuUtenti);
		
		JMenuItem btnAggiungiCliente = new JMenuItem("Aggiungi Cliente");
		btnAggiungiCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new FormClienteFrame();
				frame.setVisible(true);				
			}
		});
		menuUtenti.add(btnAggiungiCliente);
		
		JSeparator separator = new JSeparator();
		menuUtenti.add(separator);
		
		JMenuItem btnAggiungiVenditore = new JMenuItem("Aggiungi Venditore");
		btnAggiungiVenditore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new FormVenditoreFrame();
				frame.setVisible(true);	
			}
		});
		menuUtenti.add(btnAggiungiVenditore);
	}

	
	public JFrame getMainFrame() {
		return main;
	}
}
