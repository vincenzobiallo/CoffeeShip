package it.vincenzobiallo.coffeeship.forms;

import javax.swing.JDialog;
import javax.swing.JFrame;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JSeparator;
import javax.swing.UIManager;

import it.vincenzobiallo.coffeeship.forms.barche.FormBarcaFrame;
import it.vincenzobiallo.coffeeship.forms.barche.ListBarcaFrame;
import it.vincenzobiallo.coffeeship.forms.listini.FormNoleggioFrame;
import it.vincenzobiallo.coffeeship.forms.listini.FormTerminaNoleggio;
import it.vincenzobiallo.coffeeship.forms.listini.FormVenditaFrame;
import it.vincenzobiallo.coffeeship.forms.utenti.FormClienteFrame;
import it.vincenzobiallo.coffeeship.forms.utenti.FormVenditoreFrame;
import it.vincenzobiallo.coffeeship.forms.utenti.ListClientiFrame;
import it.vincenzobiallo.coffeeship.forms.utenti.ListVenditoriFrame;

import java.awt.Dialog.ModalExclusionType;

public class HomeFrame {

	public JFrame main;
	public static final String TITLE = "CoffeeShip";

	public HomeFrame() {
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
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
				JDialog dialog = new FormClienteFrame();
				dialog.setVisible(true);				
			}
		});
		menuUtenti.add(btnAggiungiCliente);
		
		JMenuItem btnAggiungiVenditore = new JMenuItem("Aggiungi Venditore");
		btnAggiungiVenditore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new FormVenditoreFrame();
				dialog.setVisible(true);	
			}
		});
		menuUtenti.add(btnAggiungiVenditore);

		menuUtenti.add(new JSeparator());
		
		JMenuItem btnListaClienti = new JMenuItem("Lista Clienti");
		btnListaClienti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new ListClientiFrame();
				dialog.setVisible(true);	
			}
		});
		menuUtenti.add(btnListaClienti);
		
		JMenuItem btnListaVenditori = new JMenuItem("Lista Venditori");
		btnListaVenditori.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new ListVenditoriFrame();
				dialog.setVisible(true);	
			}
		});
		menuUtenti.add(btnListaVenditori);
		
		JMenu menuBarche = new JMenu("Menu Barche");
		menuBar.add(menuBarche);
		
		JMenuItem btnAggiungiBarca = new JMenuItem("Aggiungi Barca");
		btnAggiungiBarca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new FormBarcaFrame();
				dialog.setVisible(true);
			}
		});
		menuBarche.add(btnAggiungiBarca);

		menuBarche.add(new JSeparator());
		
		JMenuItem btnListaBarche = new JMenuItem("Lista Barche");
		btnListaBarche.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new ListBarcaFrame();
				dialog.setVisible(true);
			}
		});
		menuBarche.add(btnListaBarche);
		
		JMenu menuOperazioni = new JMenu("Menu Operazioni");
		menuBar.add(menuOperazioni);
		
		JMenu menuVendita = new JMenu("Operazioni di Vendita");
		menuOperazioni.add(menuVendita);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Vendi Barca..");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new FormVenditaFrame();
				dialog.setVisible(true);
			}
		});
		menuVendita.add(mntmNewMenuItem);
		
		JMenu menuNoleggio = new JMenu("Operazioni di Noleggio");
		menuOperazioni.add(menuNoleggio);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Noleggia Barca..");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new FormNoleggioFrame();
				dialog.setVisible(true);
			}
		});
		menuNoleggio.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Termina Noleggio..");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new FormTerminaNoleggio();
				dialog.setVisible(true);
			}
		});
		menuNoleggio.add(mntmNewMenuItem_2);
	}

	
	public JFrame getMainFrame() {
		return main;
	}
}
