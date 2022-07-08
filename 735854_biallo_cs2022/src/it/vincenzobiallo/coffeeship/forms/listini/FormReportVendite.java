package it.vincenzobiallo.coffeeship.forms.listini;

import javax.swing.JDialog;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.toedter.calendar.JDateChooser;

import it.vincenzobiallo.coffeeship.barche.Barca;
import it.vincenzobiallo.coffeeship.barche.ModelloBarca;
import it.vincenzobiallo.coffeeship.utils.MessageBox;
import it.vincenzobiallo.coffeeship.utils.PDFApi;
import it.vincenzobiallo.coffeeship.vendite.CatalogoListini;
import it.vincenzobiallo.coffeeship.vendite.Listino;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import java.awt.Desktop;
import java.awt.event.ActionEvent;

public class FormReportVendite extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private ButtonGroup group;
	private JRadioButton choiceModello;
	private JRadioButton choicePrice;

	public FormReportVendite() {
		setTitle("Report Vendite");
		setBounds(100, 100, 350, 200);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Data Inizio Intervallo");
		lblNewLabel.setBounds(10, 11, 116, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblDataFineIntervallo = new JLabel("Data Fine Intervallo");
		lblDataFineIntervallo.setBounds(171, 11, 116, 14);
		getContentPane().add(lblDataFineIntervallo);
		
		JDateChooser start = new JDateChooser();
		start.setDateFormatString("dd/MM/yyyy");
		start.setBounds(10, 30, 150, 23);
		getContentPane().add(start);
		
		JDateChooser end = new JDateChooser();
		end.setDateFormatString("dd/MM/yyyy");
		end.setBounds(171, 30, 150, 23);
		getContentPane().add(end);
		
		this.group = new ButtonGroup();
		
		JLabel labelOrdinamento = new JLabel("Criterio di Ordinamento");
		labelOrdinamento.setBounds(10, 72, 311, 14);
		getContentPane().add(labelOrdinamento);
		
		choiceModello = new JRadioButton("Tipo di Barca");
		choiceModello.setSelected(true);
		choiceModello.setBounds(10, 93, 109, 23);
		getContentPane().add(choiceModello);
		group.add(choiceModello);
		
		choicePrice = new JRadioButton("Prezzo pi\u00F9 Alto");
		choicePrice.setBounds(121, 93, 109, 23);
		getContentPane().add(choicePrice);
		group.add(choicePrice);
		
		JButton btnReport = new JButton("Genera Report");
		btnReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Date dataInizio = start.getDate();
				Date dataFine = end.getDate();
				
				if ((dataInizio == null) || (dataFine == null)) {
					MessageBox.showWarning("Attenzione!", "Inserisci delle Date Valide!");
				} else {
					if (dataInizio.after(dataFine))
						MessageBox.showWarning("Attenzione!", "La data di fine intervallo non può essere precedente alla data di Inizio!");
					else
						generateReport(dataInizio, dataFine);		
				}
						
			}
		});
		btnReport.setBounds(10, 127, 311, 23);
		getContentPane().add(btnReport);
		
	}
	
	private void generateReport(Date start, Date end) {
		
		try {
			
			File file = new File("Report Vendite " + Calendar.getInstance().getTimeInMillis() + ".pdf");
			Document report = PDFApi.createPdf(new FileOutputStream(file));
			
			Set<Barca> barcheAcquistate = null;
			
			if (this.choiceModello.isSelected())
				barcheAcquistate = new TreeSet<Barca>(new SortByModello());
			else
				barcheAcquistate = new TreeSet<Barca>(new SortByPrice());
			
			for (Listino listino : CatalogoListini.getListini()) {
				if (listino.isVenduto()) {
					
					Date dataVendita = new SimpleDateFormat("dd/MM/yyyy").parse(listino.getContrattoVendita().getData());
					
					if (dataVendita.after(start) && dataVendita.before(end))
						barcheAcquistate.add(listino.getBarca());
					
				}				
			}
			
			report.open();
			
			PdfPTable table = PDFApi.createTable(7);
			PDFApi.addTableHeader(table, new String[] { "Numero Serie", "Scafo", "Chiglia", "Deriva", "Alberatura", "Timone", "Modello" });
			for (Barca barca : barcheAcquistate) {
				String numero_serie = barca.getNumeroSerie();
				String scafo = barca.getScafo().name();
				String chiglia = barca.getChiglia().name();
				String deriva = barca.getDeriva().name();
				String alberatura = barca.getAlberatura().name();
				String timone = barca.getTimone().name();
				String modello = barca.getModello().name();
				
				PDFApi.addRow(table, new String[] { numero_serie, scafo, chiglia, deriva, alberatura, timone, modello });
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
	
	private class SortByModello implements Comparator<Barca> {

		@Override
		public int compare(Barca b1, Barca b2) {
			
			ModelloBarca m1 = b1.getModello();
			ModelloBarca m2 = b2.getModello();
			
			if (m1 == ModelloBarca.MEDIA && m2 == ModelloBarca.AVANZATA)
				return -1;;
			
			return 1;
		}
		
	}
	
	private class SortByPrice implements Comparator<Barca> {

		@Override
		public int compare(Barca b1, Barca b2) {
			
			Listino l1 = CatalogoListini.getListino(b1);
			Listino l2 = CatalogoListini.getListino(b2);
			
			double p1 = l1.getContrattoVendita().getPrezzo();
			double p2 = l2.getContrattoVendita().getPrezzo();
			
			if (p1 >= p2)
				return -1;
			
			return 1;
		}

		
		
	}
}
