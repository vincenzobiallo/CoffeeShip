package it.vincenzobiallo.coffeeship.forms.utenti;

import it.vincenzobiallo.coffeeship.utenti.venditori.CatalogoVenditori;
import it.vincenzobiallo.coffeeship.utenti.venditori.Venditore;
import it.vincenzobiallo.coffeeship.utils.MessageBox;

import javax.swing.JTextField;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;

public class FormVenditoreFrame extends AbstractForm<Venditore> {

	private static final long serialVersionUID = 3L;

	private JTextField boxCodiceVenditore;

	/**
	 * Costruttore del Form per poter inserire un Venditore
	 */
	public FormVenditoreFrame() {
		super();

		injectInput();

		super.TITLE = "Aggiungi Venditore";
		setTitle(TITLE);
		super.btnAction.setText(TITLE);
	}

	/**
	 * Costruttore del Form per poter modificare un Venditore
	 * 
	 * @param venditore
	 */
	public FormVenditoreFrame(Venditore venditore) {
		super(venditore);

		injectInput();
		this.boxCodiceVenditore.setEnabled(false);

		super.TITLE = "Modifica Venditore";
		setTitle(TITLE);
		super.btnAction.setText(TITLE);

		this.boxCodiceVenditore.setText(venditore.getCodiceVenditore());
		super.boxCodiceFiscale.setText(venditore.getCodiceFiscale());
		super.boxNome.setText(venditore.getNome());
		super.boxCognome.setText(venditore.getCognome());
		try {
			super.boxDataNascita.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(venditore.getDataNascita()));
		} catch (ParseException ex) {
			super.boxDataNascita.setEnabled(false);
			MessageBox.showError(getTitle(), ex.getMessage());
		}
	}

	/**
	 * Rispetto alla classe astratta, questa funzione inserisce del nuovo codice all'interno della grafica della GUI
	 */
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

		if ((super.boxDataNascita.getDate() == null) || (super.boxNome.getText().length() == 0)
				|| (super.boxCognome.getText().length() == 0)) {
			MessageBox.showWarning(TITLE, "Dati inseriti non validi!");
		} else {

			Date rawData = super.boxDataNascita.getDate();

			Calendar dataNascita = Calendar.getInstance();
			dataNascita.setTime(rawData);

			if (super.boxCodiceFiscale.isEnabled()) {

				int result = CatalogoVenditori.aggiungiVenditore(codice_venditore, codice_fiscale, nome, cognome,
						dataNascita);

				if (result == 1) {
					MessageBox.showInformation(TITLE, String
							.format("Venditore con codice venditore '%s' inserito con successo!", codice_venditore));
					CatalogoVenditori.salvaCatalogo();
					dispose();
				} else if (result == 0) {
					MessageBox.showWarning(TITLE, "Venditore già presente in archivio!");
					dispose();
				}

			} else {

				int result = CatalogoVenditori.modificaVenditore(codice_venditore, codice_fiscale, nome, cognome,
						dataNascita);

				if (result == 1) {
					MessageBox.showInformation(TITLE, String
							.format("Venditore con codice venditore '%s' modificato con successo!", codice_venditore));
					CatalogoVenditori.salvaCatalogo();
					dispose();
				} else if (result == 0) {
					MessageBox.showWarning(TITLE, "Venditore non presente in archivio!");
					dispose();
				}
			}
		}

	}

}
