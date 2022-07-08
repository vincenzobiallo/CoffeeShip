package it.vincenzobiallo.coffeeship.forms.listini;

import java.awt.BorderLayout;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import it.vincenzobiallo.coffeeship.barche.Barca;
import it.vincenzobiallo.coffeeship.barche.CatalogoBarche;
import it.vincenzobiallo.coffeeship.utils.MessageBox;
import it.vincenzobiallo.coffeeship.vendite.CatalogoListini;
import it.vincenzobiallo.coffeeship.vendite.ContrattoNoleggio;
import it.vincenzobiallo.coffeeship.vendite.Listino;
import java.awt.Choice;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FormTerminaNoleggio extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private static final String title = "Termina Noleggio";

	private JTextField boxVenditore;
	private JTextField boxCliente;
	private JTextField boxPenale;
	private JTextField boxDataFine;

	private Choice choiceBarca;
	private Choice choiceInizio;

	public FormTerminaNoleggio() {
		setBounds(100, 100, 300, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		choiceBarca = new Choice();
		choiceBarca.add("-");
		choiceBarca.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				choiceBarca.setEnabled(false);

				String codice_barca = choiceBarca.getSelectedItem();
				Barca barca = CatalogoBarche.getBarca(codice_barca);
				Listino listino = CatalogoListini.getListino(barca);

				ContrattoNoleggio[] contratti = getNoleggi(listino);
				for (ContrattoNoleggio contratto : contratti)
					choiceInizio.add(contratto.getDataInizio());
				choiceInizio.setEnabled(true);
			}
		});
		for (Listino listino : CatalogoListini.getArticoli(false))
			if (listino.isNoleggiato())
				choiceBarca.add(listino.getBarca().getNumeroSerie());
		if (choiceBarca.getItemCount() == 0) {
			MessageBox.showWarning(title, "Non sono disponibili Barche per la vendita!");
			dispose();
		}
		choiceBarca.setBounds(124, 11, 150, 20);
		contentPanel.add(choiceBarca);

		choiceInizio = new Choice();
		choiceInizio.add("-");
		choiceInizio.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (choiceInizio.getItem(0).equalsIgnoreCase("-"))
					choiceInizio.remove(0);
				updateFields();
			}
		});
		choiceInizio.setEnabled(false);
		choiceInizio.setBounds(124, 34, 150, 20);
		contentPanel.add(choiceInizio);
		updateFields();

		JLabel labelBarca = new JLabel("Barca:");
		labelBarca.setBounds(10, 11, 108, 14);
		contentPanel.add(labelBarca);

		JLabel labelVenditore = new JLabel("Venditore:");
		labelVenditore.setBounds(10, 65, 104, 14);
		contentPanel.add(labelVenditore);

		boxVenditore = new JTextField();
		boxVenditore.setEditable(false);
		boxVenditore.setBounds(124, 62, 150, 20);
		contentPanel.add(boxVenditore);
		boxVenditore.setColumns(10);

		JLabel labelCliente = new JLabel("Cliente:");
		labelCliente.setBounds(10, 93, 104, 14);
		contentPanel.add(labelCliente);

		boxCliente = new JTextField();
		boxCliente.setEditable(false);
		boxCliente.setColumns(10);
		boxCliente.setBounds(124, 90, 150, 20);
		contentPanel.add(boxCliente);

		JLabel labelPenale = new JLabel("Penale da Versare:");
		labelPenale.setBounds(10, 149, 104, 14);
		contentPanel.add(labelPenale);

		boxPenale = new JTextField();
		boxPenale.setEditable(false);
		boxPenale.setColumns(10);
		boxPenale.setBounds(124, 146, 150, 20);
		contentPanel.add(boxPenale);

		JLabel labelDataFine = new JLabel("Data Fine:");
		labelDataFine.setBounds(10, 121, 104, 14);
		contentPanel.add(labelDataFine);

		boxDataFine = new JTextField();
		boxDataFine.setEditable(false);
		boxDataFine.setColumns(10);
		boxDataFine.setBounds(124, 118, 150, 20);
		contentPanel.add(boxDataFine);

		JButton btnTermina = new JButton("Termina Noleggio");
		btnTermina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (MessageBox.askQuestion("Terminare Noleggio?", "Sei sicuro di voler terminare questo noleggio?")) {
					String codice_barca = choiceBarca.getSelectedItem();
					Barca barca = CatalogoBarche.getBarca(codice_barca);
					Listino listino = CatalogoListini.getListino(barca);
					
					String dataInizio = choiceInizio.getSelectedItem();

					ContrattoNoleggio[] contratti = getNoleggi(listino);
					for (ContrattoNoleggio contratto : contratti)
						if (contratto.getDataInizio().equalsIgnoreCase(dataInizio))
							contratto.setTerminato(true);
					
					MessageBox.showInformation("Noleggio Terminato", "Noleggio Terminato con successo!");
					CatalogoListini.salvaCatalogo();
					dispose();
				}
			}
		});
		btnTermina.setBounds(10, 177, 264, 23);
		contentPanel.add(btnTermina);

		JLabel labelDataInizio = new JLabel("Inizio Noleggio:");
		labelDataInizio.setBounds(10, 36, 108, 14);
		contentPanel.add(labelDataInizio);
	}

	private void updateFields() {

		String dataInizio = choiceInizio.getSelectedItem();

		String codice_barca = choiceBarca.getSelectedItem();
		Barca barca = CatalogoBarche.getBarca(codice_barca);
		Listino listino = CatalogoListini.getListino(barca);

		if (listino == null)
			return;

		ContrattoNoleggio[] contratti = getNoleggi(listino);

		for (ContrattoNoleggio contratto : contratti)
			if (contratto.getDataInizio().equals(dataInizio)) {
				this.boxCliente.setText(contratto.getCliente().getCodiceFiscale());
				this.boxVenditore.setText(contratto.getVenditore().getCodiceVenditore());
				this.boxDataFine.setText(contratto.getDataFine());

				LocalDate fine = LocalDate.parse(boxDataFine.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				LocalDate today = LocalDate.now();

				Period days = Period.between(fine, today);

				double penale = 0;
				if (days.getDays() > 0)
					penale = contratto.getPenale() * days.getDays();

				this.boxPenale.setText(penale + "");

				break;
			}

	}

	private ContrattoNoleggio[] getNoleggi(Listino listino) {

		Set<ContrattoNoleggio> contratti = new HashSet<ContrattoNoleggio>();

		for (ContrattoNoleggio contratto : listino.getContrattiNoleggio())
			if (!contratto.isTerminato())
				contratti.add(contratto);

		ContrattoNoleggio[] array = new ContrattoNoleggio[contratti.size()];

		return contratti.toArray(array);
	}
}
