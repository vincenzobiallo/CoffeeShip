package it.vincenzobiallo.coffeeship.forms.utenti;

import java.util.Set;

import javax.swing.JDialog;
import javax.swing.table.DefaultTableModel;

import it.vincenzobiallo.coffeeship.utenti.venditori.CatalogoVenditori;
import it.vincenzobiallo.coffeeship.utenti.venditori.Venditore;
import it.vincenzobiallo.coffeeship.utils.MessageBox;

public class ListVenditoriFrame extends AbstractElenco<Venditore> {

	private static final long serialVersionUID = 2L;

	public ListVenditoriFrame() {
		super(new String[] { "Codice Venditore", "Codice Fiscale", "Nome", "Cognome", "Data Nascita" });
		setTitle("Lista Venditori");
		setBounds(100, 100, 450, 300);
		super.btnAggiungi.setText("Aggiungi Venditore");
		super.btnModifica.setText("Modifica Venditore");
		super.btnRimuovi.setText("Rimuovi Venditore");
		injectData(CatalogoVenditori.getVenditori());
	}

	@Override
	protected void injectData(Set<Venditore> data) {		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		
		for (Venditore venditore : data) {
			String codice_venditore = venditore.getCodiceVenditore();
			String codice_fiscale = venditore.getCodiceFiscale();
			String nome = venditore.getNome();
			String cognome = venditore.getCognome();
			String dataNascita = venditore.getDataNascita();
			
			model.addRow(new String[] { codice_venditore, codice_fiscale, nome, cognome, dataNascita });
		}
	}

	@Override
	protected void actionAggiungi() {
		JDialog dialog = new FormVenditoreFrame();
		dialog.setVisible(true);
		this.actionAggiorna();
	}

	@Override
	protected void actionRimuovi() {
		int index = table.getSelectedRow(); // -1 se nulla è selezionato

		if (index != -1) {
			
			Venditore selected = (Venditore) CatalogoVenditori.getVenditori().toArray()[index];
			
			if (MessageBox.askQuestion("Gestione Venditori", String.format("Vuoi veramente eliminare questo Venditore con Codice Venditore '%s' ?", selected.getCodiceVenditore()))) {		
				boolean result = CatalogoVenditori.rimuoviVenditore(selected.getCodiceVenditore());
				
				if (result) {
					this.actionAggiorna();
					MessageBox.showInformation("Gestione Venditori", "Venditore eliminato con successo!");			
				} else {
					MessageBox.showWarning("Gestione Venditori", "Impossibile eliminare questo Venditore!");			
				}
				
			}
		} else {
			MessageBox.showWarning("Gestione Clienti", "Devi selezionare almeno un Venditore per poterlo rimuovere!");
		}
	}
	
	@Override
	protected void actionModifica() {
		
		int index = table.getSelectedRow(); // -1 se nulla è selezionato

		if (index != -1) {
			
			Venditore selected = (Venditore) CatalogoVenditori.getVenditori().toArray()[index];
			
			JDialog dialog = new FormVenditoreFrame(selected);
			dialog.setVisible(true);
			this.actionAggiorna();
			
		} else {
			MessageBox.showWarning("Gestione Venditori", "Devi selezionare almeno un V per poterlo modificare!");
		}		
	}

	@Override
	protected void actionAggiorna() {
		this.injectData(CatalogoVenditori.getVenditori());
	}

}
