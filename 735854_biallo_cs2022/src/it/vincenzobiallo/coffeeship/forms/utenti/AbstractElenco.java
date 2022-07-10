package it.vincenzobiallo.coffeeship.forms.utenti;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.util.Set;
import java.awt.event.ActionEvent;

abstract class AbstractElenco<T> extends JDialog {

	private static final long serialVersionUID = 1L;
	private String TITLE = "";
	private JPanel contentPane;
	
	protected JTable table;
	
	protected JButton btnAggiungi;
	protected JButton btnModifica;
	protected JButton btnRimuovi;
	private JSeparator separator;
	private JButton btnAggiorna;

	/**
	 * Genera il Form dell'elenco
	 * @param columns
	 */
	protected AbstractElenco(String[] columns) {
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
		
		btnAggiungi = new JButton("Aggiungi {0}");
		btnAggiungi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionAggiungi();
			}
		});
		top_panel.add(btnAggiungi);
		
		btnModifica = new JButton("Modifica {0}");
		btnModifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionModifica();
			}
		});
		top_panel.add(btnModifica);
		
		
		btnRimuovi = new JButton("Rimuovi {0}");
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
		
		DefaultTableModel model = new DefaultTableModel(0, 0);
		model.setColumnIdentifiers(columns);
		table.setModel(model);
		
	}
	
	/**
	 * Evento che si Attiva Quando si clicca sul Pulsante Aggiungi
	 */
	protected abstract void actionAggiungi();
	
	/**
	 * Evento che si Attiva Quando si clicca sul Pulsante Modifica
	 */
	protected abstract void actionModifica();
	
	/**
	 * Evento che si Attiva Quando si clicca sul Pulsante Rimuovi
	 */
	protected abstract void actionRimuovi();
	
	/**
	 * Evento che si Attiva Quando si clicca sul Pulsante Aggiorna
	 */
	protected abstract void actionAggiorna();
	
	/**
	 * Inserisce i dati nella Tabella
	 */
	protected abstract void injectData(Set<T> data);

}
