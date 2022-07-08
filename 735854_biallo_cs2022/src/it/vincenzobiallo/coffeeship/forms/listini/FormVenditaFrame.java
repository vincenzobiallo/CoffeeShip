package it.vincenzobiallo.coffeeship.forms.listini;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import java.awt.Choice;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import it.vincenzobiallo.coffeeship.barche.Barca;
import it.vincenzobiallo.coffeeship.barche.CatalogoBarche;
import it.vincenzobiallo.coffeeship.exceptions.ContrattoException;
import it.vincenzobiallo.coffeeship.exceptions.VenditaException;
import it.vincenzobiallo.coffeeship.utenti.clienti.CatalogoClienti;
import it.vincenzobiallo.coffeeship.utenti.clienti.Cliente;
import it.vincenzobiallo.coffeeship.utenti.venditori.CatalogoVenditori;
import it.vincenzobiallo.coffeeship.utenti.venditori.Venditore;
import it.vincenzobiallo.coffeeship.utils.MessageBox;
import it.vincenzobiallo.coffeeship.vendite.CatalogoListini;
import it.vincenzobiallo.coffeeship.vendite.Listino;

import javax.swing.JButton;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FormVenditaFrame extends JDialog {

	private static final long serialVersionUID = 1L;
	private static final String title = "Vendi Barca";
	
	private Choice choiceBarca;
	private JSpinner prezzoStandard;
	private JSpinner prezzoApplicato;

	public FormVenditaFrame() throws VenditaException {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle(title);
		getContentPane().setLayout(null);
		
		choiceBarca = new Choice();
		for (Listino listino : CatalogoListini.getArticoli(true)) {
			if (!listino.isVenduto())
				choiceBarca.add(listino.getBarca().getNumeroSerie());
		}
		
		if (choiceBarca.getItemCount() == 0)
			throw new VenditaException("Non sono disponibili Barche per la vendita!");
		
		choiceBarca.setBounds(10, 31, 264, 20);
		getContentPane().add(choiceBarca);
		
		JLabel lblNewLabel = new JLabel("Barca");
		lblNewLabel.setBounds(10, 11, 46, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel labelApplicato = new JLabel("Prezzo da Applicare:");
		labelApplicato.setBounds(10, 102, 112, 14);
		getContentPane().add(labelApplicato);
		
		JLabel labelStandard = new JLabel("Prezzo da Listino:");
		labelStandard.setBounds(10, 77, 112, 14);
		getContentPane().add(labelStandard);
		
		prezzoStandard = new JSpinner();
		prezzoStandard.setEnabled(false);
		prezzoStandard.setModel(new SpinnerNumberModel(0.0, null, null, 0.1));
		prezzoStandard.setBounds(132, 74, 142, 20);
		getContentPane().add(prezzoStandard);
		
		prezzoApplicato = new JSpinner();
		prezzoApplicato.setModel(new SpinnerNumberModel(0.0, null, null, 0.1));
		prezzoApplicato.setBounds(132, 99, 142, 20);
		getContentPane().add(prezzoApplicato);
		
		JLabel labelCliente = new JLabel("Cliente");
		labelCliente.setBounds(10, 130, 46, 14);
		getContentPane().add(labelCliente);
		
		Choice choiceCliente = new Choice();
		choiceCliente.setBounds(10, 150, 264, 20);
		for (Cliente cliente : CatalogoClienti.getClienti())
			choiceCliente.add(cliente.getCodiceFiscale());
		getContentPane().add(choiceCliente);
		
		JLabel labelVenditore = new JLabel("Venditore");
		labelVenditore.setBounds(10, 176, 46, 14);
		getContentPane().add(labelVenditore);
		
		Choice choiceVenditore = new Choice();
		choiceVenditore.setBounds(10, 196, 264, 20);
		for (Venditore venditore : CatalogoVenditori.getVenditori())
			choiceVenditore.add(venditore.getCodiceVenditore());
		getContentPane().add(choiceVenditore);
		
		JButton btnNewButton = new JButton("Vendi Barca");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String codice_barca = choiceBarca.getSelectedItem();
				Barca barca = CatalogoBarche.getBarca(codice_barca);
				
				String codice_cliente = choiceCliente.getSelectedItem();
				Cliente cliente = CatalogoClienti.getCliente(codice_cliente);
				
				String codice_venditore = choiceVenditore.getSelectedItem();
				Venditore venditore = CatalogoVenditori.getVenditore(codice_venditore);
				
				if ((barca == null) || (cliente == null) || (venditore == null)) {
					MessageBox.showWarning(title, "I dati inseriti non sono corretti!");
				} else {
					
					double prezzo_applicato = Double.parseDouble(String.valueOf(prezzoApplicato.getValue()));
					double prezzo_standard = Double.parseDouble(String.valueOf(prezzoStandard.getValue()));
					
					if (MessageBox.askQuestion(title, String.format("Sei sicuro di voler vendere questa Barca?\nSconto effettuato : %f",(prezzo_standard - prezzo_applicato)))) {
						
						Calendar today = Calendar.getInstance();
						today.setTime(Calendar.getInstance().getTime());
						try {
							CatalogoListini.vendiBarca(barca.getNumeroSerie(), cliente.getCodiceFiscale(), venditore.getCodiceVenditore(), prezzo_applicato, today);
							CatalogoListini.salvaCatalogo();
							MessageBox.showInformation(title, "Barca Venduta con successo!");				
							dispose();
						} catch (VenditaException | ContrattoException ex) {
							MessageBox.showWarning(codice_cliente, ex.getMessage());
						}
				
					}				
				}			
			}
		});
		btnNewButton.setBounds(10, 227, 264, 23);
		getContentPane().add(btnNewButton);
		setResizable(false);
		setModal(false);
		setModalExclusionType(ModalExclusionType.NO_EXCLUDE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setType(Type.POPUP);
		setMinimumSize(new Dimension(300, 300));
		
		updateFields();
		
		choiceBarca.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				updateFields();	        	
			}
		});
	}
	
	private void updateFields() {
		String numero_serie = choiceBarca.getSelectedItem();
    	
    	Barca barca = CatalogoBarche.getBarca(numero_serie);
    	Listino listino = CatalogoListini.getListino(barca);
    	
    	prezzoStandard.setValue(listino.getPrezzoStandard());
    	prezzoApplicato.setValue(listino.getPrezzoStandard());
	}
}
