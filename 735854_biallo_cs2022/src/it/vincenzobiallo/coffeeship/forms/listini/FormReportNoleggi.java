package it.vincenzobiallo.coffeeship.forms.listini;

import javax.swing.JDialog;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;

import it.vincenzobiallo.coffeeship.utenti.clienti.CatalogoClienti;
import it.vincenzobiallo.coffeeship.utenti.clienti.Cliente;
import it.vincenzobiallo.coffeeship.utils.MessageBox;
import it.vincenzobiallo.coffeeship.utils.PDFApi;
import it.vincenzobiallo.coffeeship.vendite.CatalogoListini;
import it.vincenzobiallo.coffeeship.vendite.ContrattoNoleggio;
import it.vincenzobiallo.coffeeship.vendite.Listino;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.Choice;

public class FormReportNoleggi extends JDialog {

	private static final long serialVersionUID = 2L;
	
	private Choice choiceCliente;

	public FormReportNoleggi() {
		setTitle("Report Noleggi");
		setBounds(100, 100, 350, 130);
		getContentPane().setLayout(null);
		
		JLabel labelCliente = new JLabel("Seleziona Cliente:");
		labelCliente.setBounds(10, 11, 116, 14);
		getContentPane().add(labelCliente);
		
		JButton btnReport = new JButton("Genera Report");
		btnReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateReport();				
			}
		});
		btnReport.setBounds(10, 57, 311, 23);
		getContentPane().add(btnReport);
		
		choiceCliente = new Choice();
		choiceCliente.setBounds(10, 29, 311, 20);
		for (Cliente cliente : CatalogoClienti.getClienti())
			choiceCliente.add(cliente.getCodiceFiscale() + " - " + "(" + cliente.getCognome() + " " + cliente.getNome() + ")");
		getContentPane().add(choiceCliente);
		
	}
	
	private void generateReport() {
		
		try {
			
			File file = new File("./reports/Report Noleggi " + Calendar.getInstance().getTimeInMillis() + ".pdf");
			Document report = PDFApi.createPdf(new FileOutputStream(file));
			
			Set<Listino> barche = CatalogoListini.getListini();		
			Set<ContrattoNoleggio> contratti = new TreeSet<ContrattoNoleggio>(new SortByDipendente());
			
			String codice_fiscale = choiceCliente.getSelectedItem().split(" - ")[0];
			Cliente cliente = CatalogoClienti.getCliente(codice_fiscale);
			
			for (Listino listino : barche) {
				for (ContrattoNoleggio contratto : listino.getContrattiNoleggio())
					if (contratto.getCliente().equals(cliente))			
						contratti.add(contratto);				
			}
			
			if (contratti.size() == 0) {
				MessageBox.showWarning("Attenzione", "Nessun contratto di noleggio trovato per questo cliente!");
				return;
			}
			
			report.open();
			
			PdfPTable table = PDFApi.createTable(10);
			PDFApi.addTableHeader(table, new String[] { "Dipendente", "Numero Serie", "Data Inizio", "Data Fine", "Scafo", "Chiglia", "Deriva", "Alberatura", "Timone", "Modello" });

			for (ContrattoNoleggio contratto : contratti) {			
				String numero_serie = contratto.getBarca().getNumeroSerie();
				String scafo = contratto.getBarca().getScafo().name();
				String chiglia = contratto.getBarca().getChiglia().name();
				String deriva = contratto.getBarca().getDeriva().name();
				String alberatura = contratto.getBarca().getAlberatura().name();
				String timone = contratto.getBarca().getTimone().name();
				String modello = contratto.getBarca().getModello().name();
						
				String dataInizio = contratto.getDataInizio();
				String dataFine = contratto.getDataFine();
				String dipendente = contratto.getVenditore().getCognome();
								
				PDFApi.addRow(table, new String[] { dipendente, numero_serie, dataInizio, dataFine, scafo, chiglia, deriva, alberatura, timone, modello });
					
			}
			
			report.add(table);
			report.close();
			
			Desktop desktop = Desktop.getDesktop();
			if (file.exists())
				desktop.open(file);
			
		} catch (Exception ex) {
			MessageBox.showError("Errore nella Generazione del Report!", ex.getMessage());
		}
		
	}
	
	/**
	 * Comparatore per ordinare la lista in base al cognome del dipendente che ha effettuato il noleggio
	 */
	private class SortByDipendente implements Comparator<ContrattoNoleggio> {

		@Override
		public int compare(ContrattoNoleggio c1, ContrattoNoleggio c2) {
			
			int result = c1.getVenditore().getCognome().compareTo(c2.getVenditore().getCognome());
			
			if (result < 0)
				return -1;
			
			return 1;
		}


		
	}

}
