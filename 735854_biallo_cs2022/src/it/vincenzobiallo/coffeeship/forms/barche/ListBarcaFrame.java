package it.vincenzobiallo.coffeeship.forms.barche;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import it.vincenzobiallo.coffeeship.barche.Barca;
import it.vincenzobiallo.coffeeship.barche.CatalogoBarche;
import it.vincenzobiallo.coffeeship.forms.listini.FormListinoFrame;
import it.vincenzobiallo.coffeeship.utils.MessageBox;
import it.vincenzobiallo.coffeeship.vendite.CatalogoListini;
import it.vincenzobiallo.coffeeship.vendite.Listino;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.util.Set;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class ListBarcaFrame extends JDialog {

	private static final long serialVersionUID = 1L;
	private String TITLE = "Lista Barche";
	private JPanel contentPane;
	
	protected JTable table;
	
	protected JButton btnAggiungi;
	protected JButton btnModifica;
	protected JButton btnRimuovi;
	private JSeparator separator;
	private JButton btnAggiorna;
	private JPanel bottom_panel;
	private JCheckBox optionMostraVendute;
	private JLabel labelBarcheVendute;
	private JButton btnListino;

	/**
	 * Genera form di Lista Barche
	 */
	public ListBarcaFrame() {
		setTitle(TITLE);
		setModal(false);
		setModalExclusionType(ModalExclusionType.NO_EXCLUDE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setType(Type.POPUP);	
		setBounds(100, 100, 600, 400);
		setMinimumSize(new Dimension(600,400));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel top_panel = new JPanel();
		contentPane.add(top_panel, BorderLayout.NORTH);
		
		btnAggiungi = new JButton("Aggiungi Barca");
		btnAggiungi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionAggiungi();
			}
		});
		top_panel.add(btnAggiungi);
		
		btnModifica = new JButton("Modifica Barca");
		btnModifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionModifica();
			}
		});
		top_panel.add(btnModifica);
		
		
		btnRimuovi = new JButton("Rimuovi Barca");
		btnRimuovi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionRimuovi();
			}
		});
		top_panel.add(btnRimuovi);
		
		separator = new JSeparator();
		top_panel.add(separator);
		
		btnAggiorna = new JButton("Aggiorna");
		btnAggiorna.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionAggiorna();
			}
		});
		top_panel.add(btnAggiorna);
		
		JScrollPane page_panel = new JScrollPane();
		contentPane.add(page_panel, BorderLayout.CENTER);
		
		table = new JTable();
		page_panel.setViewportView(table);
		table.setDefaultEditor(String.class, null);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        	
	        	if (table.getSelectedRow() != -1) {
	        		btnListino.setEnabled(true);
		        	
		        	String numero_serie = table.getValueAt(table.getSelectedRow(), 0).toString();
		        	Barca barca = CatalogoBarche.getBarca(numero_serie);
		        	
		        	if (CatalogoListini.getListino(barca) != null) {
		        		btnListino.setText("Modifica Listino");
		        	} else {
		        		btnListino.setText("Aggiungi Listino");
		        	}
	        	} else {
	        		btnListino.setEnabled(false);
	        	}
	        	
	        }
	    });
		
		DefaultTableModel model = new DefaultTableModel(0, 0);
		model.setColumnIdentifiers(new String[] { "Numero Serie", "Scafo", "Chiglia", "Deriva", "Alberatura", "Timone", "Modello" });
		table.setModel(model);
		table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {

			private static final long serialVersionUID = 1L;

			@Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                String numero_serie = table.getValueAt(row, 0).toString();
                
                boolean hasListino = CatalogoListini.getListino(CatalogoBarche.getBarca(numero_serie)) != null;
                
                if (hasListino)
                    c.setBackground(Color.ORANGE);
                else
                	c.setBackground(Color.WHITE);
                
                return c;
            }
		});
		
		bottom_panel = new JPanel();
		contentPane.add(bottom_panel, BorderLayout.SOUTH);
		
		JLabel labelSfondo = new JLabel("(Sfondo Arancione = hanno un Listino)");
		bottom_panel.add(labelSfondo);
		
		btnListino = new JButton("Aggiungi Listino");
		btnListino.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String numero_serie = table.getValueAt(table.getSelectedRow(), 0).toString();
				Barca barca = CatalogoBarche.getBarca(numero_serie);
				
				Listino listino = CatalogoListini.getListino(barca);
				
				JDialog dialog = null;
				
				if (listino == null) 
					dialog = new FormListinoFrame(barca);
				else
					dialog = new FormListinoFrame(listino);
				
				dialog.setVisible(true);
			}
		});
		btnListino.setEnabled(false);
		bottom_panel.add(btnListino);
		
		labelBarcheVendute = new JLabel("Mostrare Barche gi\u00E0 Vendute?");
		bottom_panel.add(labelBarcheVendute);
		
		optionMostraVendute = new JCheckBox("Mostra");
		optionMostraVendute.setSelected(true);
		optionMostraVendute.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				actionAggiorna();
			}
		});
		
		labelBarcheVendute.setLabelFor(optionMostraVendute);
		bottom_panel.add(optionMostraVendute);
		
		this.actionAggiorna();
		
	}
	
	/**
	 * Funzionalità di Aggiunta
	 */
	private void actionAggiungi() {
		JDialog dialog = new FormBarcaFrame();
		dialog.setVisible(true);
	}
	
	/**
	 * Funzionalità di Modifica
	 */
	private void actionModifica() {
		
		String numero_Serie = table.getValueAt(table.getSelectedRow(), 0).toString();
		Barca barca = CatalogoBarche.getBarca(numero_Serie);
		
		JDialog dialog = new FormBarcaFrame(barca);
		dialog.setVisible(true);
	}
	
	/**
	 * Funzionalità di Rimozione
	 */
	private void actionRimuovi() {
		
		int index = table.getSelectedRow(); // -1 se nulla è selezionato

		if (index != -1) {
			
			Barca selected = (Barca) CatalogoBarche.getBarche().toArray()[index];
			
			if (CatalogoListini.getListino(selected) != null) {
				MessageBox.showInformation("Gestione Barche", "Non puoi eliminare questa Barca in quanto ha un Listino attivo!");
				return;
			}
			
			if (MessageBox.askQuestion("Gestione Barche", String.format("Vuoi veramente eliminare questa Barca con Numero Serie '%s' ?", selected.getNumeroSerie()))) {
				
				boolean result = CatalogoBarche.rimuoviBarca(selected.getNumeroSerie());
				
				if (result) {
					this.actionAggiorna();
					MessageBox.showInformation("Gestione Barche", "Barca eliminata con successo!");
				} else {
					MessageBox.showWarning("Gestione Barche", "Impossibile eliminare questa Barca!");			
				}
							
			}
		} else {
			MessageBox.showWarning("Gestione Barcge", "Devi selezionare almeno una Barca per poterla rimuovere!");
		}
	}
	
	/**
	 * Funzionalità di Aggiornamento
	 */
	private void actionAggiorna() {
		
		Set<Barca> buffer = CatalogoBarche.getBarche();
		
		if (!this.optionMostraVendute.isSelected()) {		
			for (Listino listino : CatalogoListini.getListini())
				if (listino.isVenduto())
					buffer.remove(listino.getBarca());	
		}
		
		this.injectData(buffer);
	}
	
	/**
	 * Inserisce i dati nella tabella
	 * @param data
	 */
	private void injectData(Set<Barca> data) {
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		
		for (Barca barca : data) {
			String numero_serie = barca.getNumeroSerie();
			String scafo = barca.getScafo().name();
			String chiglia = barca.getChiglia().name();
			String deriva = barca.getDeriva().name();
			String alberatura = barca.getAlberatura().name();
			String timone = barca.getTimone().name();
			String modello = barca.getModello().name();
			
			model.addRow(new String[] { numero_serie, scafo, chiglia, deriva, alberatura, timone, modello });
		}
	}

}
