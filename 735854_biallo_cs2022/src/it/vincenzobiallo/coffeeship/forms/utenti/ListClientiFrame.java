package it.vincenzobiallo.coffeeship.forms.utenti;

import java.util.Set;

import javax.swing.JDialog;
import javax.swing.table.DefaultTableModel;

import it.vincenzobiallo.coffeeship.utenti.clienti.CatalogoClienti;
import it.vincenzobiallo.coffeeship.utenti.clienti.Cliente;
import it.vincenzobiallo.coffeeship.utils.MessageBox;

public class ListClientiFrame extends AbstractElenco<Cliente> {

	private static final long serialVersionUID = 2L;

	public ListClientiFrame() {
		super(new String[] { "Codice Fiscale", "Nome", "Cognome", "Data Nascita" });
		setTitle("Lista Clienti");
		setBounds(100, 100, 450, 300);
		super.btnAggiungi.setText("Aggiungi Cliente");
		super.btnModifica.setText("Modifica Cliente");
		super.btnRimuovi.setText("Rimuovi Cliente");
		injectData(CatalogoClienti.getClienti());
	}

	@Override
	protected void injectData(Set<Cliente> data) {		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		
		for (Cliente cliente : data) {
			String codice_fiscale = cliente.getCodiceFiscale();
			String nome = cliente.getNome();
			String cognome = cliente.getCognome();
			String dataNascita = cliente.getDataNascita();
			
			model.addRow(new String[] { codice_fiscale, nome, cognome, dataNascita });
		}
	}

	@Override
	protected void actionAggiungi() {
		JDialog dialog = new FormClienteFrame();
		dialog.setVisible(true);
		this.actionAggiorna();
	}
	
	@Override
	protected void actionModifica() {
		
		int index = table.getSelectedRow(); // -1 se nulla è selezionato

		if (index != -1) {
			
			Cliente selected = (Cliente) CatalogoClienti.getClienti().toArray()[index];
			
			JDialog dialog = new FormClienteFrame(selected);
			dialog.setVisible(true);
			this.actionAggiorna();
			
		} else {
			MessageBox.showWarning("Gestione Clienti", "Devi selezionare almeno un Cliente per poterlo modificare!");
		}
		
	}

	@Override
	protected void actionRimuovi() {
		int index = table.getSelectedRow(); // -1 se nulla è selezionato

		if (index != -1) {
			
			Cliente selected = (Cliente) CatalogoClienti.getClienti().toArray()[index];
			
			if (MessageBox.askQuestion("Gestione Clienti", String.format("Vuoi veramente eliminare questo Cliente con Codice Fiscale '%s' ?", selected.getCodiceFiscale()))) {
				
				boolean result = CatalogoClienti.rimuoviCliente(selected.getCodiceFiscale());
				
				if (result) {
					this.actionAggiorna();
					MessageBox.showInformation("Gestione Clienti", "Cliente eliminato con successo!");
				} else {
					MessageBox.showWarning("Gestione Clienti", "Impossibile eliminare questo Cliente!");			
				}
							
			}
		} else {
			MessageBox.showWarning("Gestione Clienti", "Devi selezionare almeno un Cliente per poterlo rimuovere!");
		}
	}

	@Override
	protected void actionAggiorna() {
		this.injectData(CatalogoClienti.getClienti());	
	}

}
