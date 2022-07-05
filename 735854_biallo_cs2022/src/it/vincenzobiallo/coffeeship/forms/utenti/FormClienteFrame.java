package it.vincenzobiallo.coffeeship.forms.utenti;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import it.vincenzobiallo.coffeeship.utenti.clienti.CatalogoClienti;
import it.vincenzobiallo.coffeeship.utenti.clienti.Cliente;
import it.vincenzobiallo.coffeeship.utils.MessageBox;

public class FormClienteFrame extends AbstractForm<Cliente> {

	private static final long serialVersionUID = 2L;
	
	public FormClienteFrame() {	
		super();
		super.TITLE = "Aggiungi Cliente";
		setTitle(TITLE);
		super.btnAction.setText(TITLE);
	}
	
	public FormClienteFrame(Cliente cliente) {	
		super(cliente);
		super.TITLE = "Modifica Cliente";
		setTitle(TITLE);
		super.btnAction.setText(TITLE);
		
		super.boxCodiceFiscale.setText(cliente.getCodiceFiscale());
		super.boxNome.setText(cliente.getNome());
		super.boxCognome.setText(cliente.getCognome());
		try {
			super.boxDataNascita.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(cliente.getDataNascita()));
		} catch (ParseException ex) {
			super.boxDataNascita.setEnabled(false);
			MessageBox.showError(getTitle(), ex.getMessage());
		}
	}

	@Override
	public void buttonAction() {

		String codice_fiscale = super.boxCodiceFiscale.getText();
		String nome = super.boxNome.getText();
		String cognome = super.boxCognome.getText();
		Date rawData = super.boxDataNascita.getDate();

		Calendar dataNascita = Calendar.getInstance();
		dataNascita.setTime(rawData);
		
		if (super.boxCodiceFiscale.isEnabled()) {
			int result = CatalogoClienti.aggiungiCliente(codice_fiscale, nome, cognome, dataNascita);

			if (result == 1) {
				MessageBox.showInformation(TITLE,String.format("Cliente con codice fiscale '%s' inserito con successo!", codice_fiscale));
				CatalogoClienti.salvaCatalogo();
				dispose();
			} else if (result == 0) {
				MessageBox.showWarning(TITLE, "Cliente già presente in archivio!");
				dispose();
			}
		} else {
			int result = CatalogoClienti.modificaCliente(codice_fiscale, nome, cognome, dataNascita);

			if (result == 1) {
				MessageBox.showInformation(TITLE,String.format("Cliente con codice fiscale '%s' modificato con successo!", codice_fiscale));
				CatalogoClienti.salvaCatalogo();
				dispose();
			} else if (result == 0) {
				MessageBox.showWarning(TITLE, "Cliente non presente in archivio!");
				dispose();
			}
		}

		
			
	}
}
