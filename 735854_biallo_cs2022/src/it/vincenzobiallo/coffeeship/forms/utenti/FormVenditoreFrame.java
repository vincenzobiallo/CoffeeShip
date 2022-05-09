package it.vincenzobiallo.coffeeship.forms.utenti;

import it.vincenzobiallo.coffeeship.utenti.venditori.CatalogoVenditori;
import it.vincenzobiallo.coffeeship.utenti.venditori.Venditore;
import it.vincenzobiallo.coffeeship.utils.MessageBox;

import javax.swing.JTextField;

import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;

public class FormVenditoreFrame extends AbstractForm<Venditore> {

	private static final long serialVersionUID = 3L;
	
	private JTextField boxCodiceVenditore;

	public FormVenditoreFrame() {
		super();
		injectInput();
		
		super.TITLE = "Aggiungi Venditore";
		setTitle(TITLE);
		super.btnAction.setText(TITLE);	
	}
	
	public FormVenditoreFrame(Venditore venditore) {	
		super(venditore);
		injectInput();
		
		super.TITLE = "Modifica Venditore";
		setTitle(TITLE);
		super.btnAction.setText(TITLE);
		
		this.boxCodiceVenditore.setText(venditore.getCodiceVenditore());
		super.boxCodiceFiscale.setText(venditore.getCodiceFiscale());
		super.boxNome.setText(venditore.getNome());
		super.boxCognome.setText(venditore.getCognome());
		super.boxDataNascita.setDate(super.boxDataNascita.getDate());
	}
	
	private void injectInput() {
		boxCodiceVenditore = new JTextField();
		boxCodiceVenditore.setBounds(154, 128, 150, 20);
		getContentPane().add(boxCodiceVenditore);
		boxCodiceVenditore.setColumns(10);
		
		JLabel labelCodiceVenditore = new JLabel("Codice Venditore:");
		labelCodiceVenditore.setBounds(10, 131, 134, 14);
		getContentPane().add(labelCodiceVenditore);
	}
	
	@Override
	public void buttonAction() {

		String codice_venditore = this.boxCodiceVenditore.getText();
		String codice_fiscale = super.boxCodiceFiscale.getText();
		String nome = super.boxNome.getText();
		String cognome = super.boxCognome.getText();
		Date rawData = super.boxDataNascita.getDate();

		Calendar dataNascita = Calendar.getInstance();
		dataNascita.setTime(rawData);

		int result = CatalogoVenditori.aggiungiVenditore(codice_venditore, codice_fiscale, nome, cognome, dataNascita);

		if (result == 1) {
			MessageBox.showInformation(TITLE, String.format("Venditore con codice venditore '%s' inserito con successo!", codice_venditore));
			CatalogoVenditori.salvaCatalogo();
			dispose();
		} else if (result == 0) {
			MessageBox.showWarning(TITLE, "Venditore gi� presente in archivio!");
			dispose();
		}
			
	}

}
