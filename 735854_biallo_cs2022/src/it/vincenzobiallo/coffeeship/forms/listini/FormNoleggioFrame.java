package it.vincenzobiallo.coffeeship.forms.listini;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import java.awt.Choice;
import javax.swing.JLabel;
import javax.swing.JSpinner;

import com.toedter.calendar.JDateChooser;

import it.vincenzobiallo.coffeeship.barche.Barca;
import it.vincenzobiallo.coffeeship.barche.CatalogoBarche;
import it.vincenzobiallo.coffeeship.exceptions.ContrattoException;
import it.vincenzobiallo.coffeeship.exceptions.OperazioneException;
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
import javax.swing.SpinnerNumberModel;

public class FormNoleggioFrame extends JDialog {

	private static final long serialVersionUID = 1L;
	private static final String title = "Noleggia Barca";
	
	private Choice choiceBarca;
	private JSpinner canoneStandard;
	private JSpinner canoneApplicato;

	public FormNoleggioFrame() throws OperazioneException {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle(title);
		getContentPane().setLayout(null);
		
		choiceBarca = new Choice();
		for (Listino listino : CatalogoListini.getArticoli(false)) {
			if (!listino.isVenduto())
				choiceBarca.add(listino.getBarca().getNumeroSerie());
		}
		
		if (choiceBarca.getItemCount() == 0)
			throw new OperazioneException("Non sono disponibili Barche per il noleggio!");
		
		choiceBarca.setBounds(10, 31, 264, 20);
		getContentPane().add(choiceBarca);
		
		JLabel lblNewLabel = new JLabel("Barca");
		lblNewLabel.setBounds(10, 11, 46, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel labelApplicato = new JLabel("Canone da Applicare:");
		labelApplicato.setBounds(10, 85, 112, 14);
		getContentPane().add(labelApplicato);
		
		JLabel labelStandard = new JLabel("Canone da Listino:");
		labelStandard.setBounds(10, 60, 112, 14);
		getContentPane().add(labelStandard);
		
		JDateChooser boxDataInizio = new JDateChooser();
		boxDataInizio.setDateFormatString("dd/MM/yyyy");
		boxDataInizio.setBounds(10, 157, 120, 20);
		getContentPane().add(boxDataInizio);
		
		JDateChooser boxDataFine = new JDateChooser();
		boxDataFine.setDateFormatString("dd/MM/yyyy");
		boxDataFine.setBounds(154, 157, 120, 20);
		getContentPane().add(boxDataFine);
		
		canoneStandard = new JSpinner();
		canoneStandard.setEnabled(false);
		canoneStandard.setModel(new SpinnerNumberModel(0.0, 0.0, null, 0.1));
		canoneStandard.setBounds(132, 57, 142, 20);
		getContentPane().add(canoneStandard);
		
		canoneApplicato = new JSpinner();
		canoneApplicato.setModel(new SpinnerNumberModel(0.0, 0.0, null, 0.1));
		canoneApplicato.setBounds(132, 82, 142, 20);
		getContentPane().add(canoneApplicato);
		
		JLabel labelCliente = new JLabel("Cliente");
		labelCliente.setBounds(10, 183, 46, 14);
		getContentPane().add(labelCliente);
		
		Choice choiceCliente = new Choice();
		choiceCliente.setBounds(10, 203, 264, 20);
		for (Cliente cliente : CatalogoClienti.getClienti())
			choiceCliente.add(cliente.getCodiceFiscale() + " - " + "(" + cliente.getCognome() + " " + cliente.getNome() + ")");
		getContentPane().add(choiceCliente);
		
		JLabel labelVenditore = new JLabel("Venditore");
		labelVenditore.setBounds(10, 229, 46, 14);
		getContentPane().add(labelVenditore);
		
		Choice choiceVenditore = new Choice();
		choiceVenditore.setBounds(10, 249, 264, 20);
		for (Venditore venditore : CatalogoVenditori.getVenditori())
			choiceVenditore.add(venditore.getCodiceVenditore() + " - " + "(" + venditore.getCognome() + " " + venditore.getNome() + ")");
		getContentPane().add(choiceVenditore);
		
		JSpinner penale = new JSpinner();
		penale.setModel(new SpinnerNumberModel(1.0, 1.0, null, 0.1));
		penale.setBounds(132, 110, 142, 20);
		getContentPane().add(penale);
		
		JButton btnNewButton = new JButton("Noleggia Barca");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String codice_barca = choiceBarca.getSelectedItem();
				Barca barca = CatalogoBarche.getBarca(codice_barca);
				
				String codice_cliente = choiceCliente.getSelectedItem().split(" - ")[0];
				Cliente cliente = CatalogoClienti.getCliente(codice_cliente);
				
				String codice_venditore = choiceVenditore.getSelectedItem().split(" - ")[0];
				Venditore venditore = CatalogoVenditori.getVenditore(codice_venditore);		
				
				if ((barca == null) || (cliente == null) || (venditore == null) || (boxDataInizio.getDate() == null) || (boxDataFine.getDate() == null)) {
					MessageBox.showWarning(title, "I dati inseriti non sono corretti!");
				} else {
					
					Calendar dataInizio = Calendar.getInstance();
					dataInizio.setTime(boxDataInizio.getDate());
					Calendar dataFine = Calendar.getInstance();
					dataFine.setTime(boxDataFine.getDate());
					
					if (!dataInizio.after(dataFine)) {
						double canone_standard = Double.parseDouble(String.valueOf(canoneStandard.getValue()));
						double canone_applicato = Double.parseDouble(String.valueOf(canoneApplicato.getValue()));
						double penale_applicata = Double.parseDouble(String.valueOf(penale.getValue()));
						
						if (MessageBox.askQuestion(title, String.format("Sei sicuro di voler noleggiare questa Barca?\nSconto effettuato sul canone : %f", (canone_standard - canone_applicato)))) {
							
							Calendar today = Calendar.getInstance();
							today.setTime(Calendar.getInstance().getTime());
							try {
								CatalogoListini.noleggiaBarca(barca.getNumeroSerie(), cliente.getCodiceFiscale(), venditore.getCodiceVenditore(), canone_applicato, penale_applicata, dataInizio, dataFine);
								CatalogoListini.salvaCatalogo();
								MessageBox.showInformation(title, "Barca Noleggiata con successo!");				
								dispose();
							} catch (OperazioneException | ContrattoException ex) {
								MessageBox.showWarning(codice_cliente, ex.getMessage());
							}
					
						}	
					} else {
						MessageBox.showWarning(title, "La data di inizio non può essere superiore alla fine!");
					}
				}			
			}
		});
		btnNewButton.setBounds(10, 277, 264, 23);
		getContentPane().add(btnNewButton);
		
		JLabel labelPenale = new JLabel("Penale:");
		labelPenale.setBounds(10, 113, 112, 14);
		getContentPane().add(labelPenale);
		
		JLabel labelDataInizio = new JLabel("Inizio Noleggio");
		labelDataInizio.setBounds(10, 138, 120, 14);
		getContentPane().add(labelDataInizio);
		
		JLabel lblFineNoleggio = new JLabel("Fine Noleggio");
		lblFineNoleggio.setBounds(154, 138, 120, 14);
		getContentPane().add(lblFineNoleggio);
		setResizable(false);
		setModal(false);
		setModalExclusionType(ModalExclusionType.NO_EXCLUDE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setType(Type.POPUP);
		setMinimumSize(new Dimension(300, 350));
		
		updateFields();
		
		choiceBarca.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				updateFields();	        	
			}
		});
	}
	
	/**
	 * Aggiorna i Campi della tabella dopo una selezione
	 */
	private void updateFields() {
		String numero_serie = choiceBarca.getSelectedItem();
    	
    	Barca barca = CatalogoBarche.getBarca(numero_serie);
    	Listino listino = CatalogoListini.getListino(barca);
    	
    	canoneStandard.setValue(listino.getCanoneStandard());
    	canoneApplicato.setValue(listino.getCanoneStandard());
	}
}
