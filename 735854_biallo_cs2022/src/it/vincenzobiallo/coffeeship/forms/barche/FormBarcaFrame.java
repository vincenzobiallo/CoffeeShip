package it.vincenzobiallo.coffeeship.forms.barche;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import it.vincenzobiallo.coffeeship.barche.Barca;
import it.vincenzobiallo.coffeeship.barche.CatalogoBarche;
import it.vincenzobiallo.coffeeship.barche.ModelloBarca;
import it.vincenzobiallo.coffeeship.barche.elementi.Alberatura;
import it.vincenzobiallo.coffeeship.barche.elementi.Chiglia;
import it.vincenzobiallo.coffeeship.barche.elementi.Deriva;
import it.vincenzobiallo.coffeeship.barche.elementi.Scafo;
import it.vincenzobiallo.coffeeship.barche.elementi.Timone;
import it.vincenzobiallo.coffeeship.utils.MessageBox;

import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class FormBarcaFrame extends JDialog {

	private static final long serialVersionUID = 1L;
	private static final String title = "Gestione Barche";

	private JPanel panel;
	private JTextField boxNumeroSerie;
	private JComboBox<Scafo> boxScafo;
	private JComboBox<Chiglia> boxChiglia;
	private JComboBox<Deriva> boxDeriva;
	private JComboBox<Alberatura> boxAlberatura;
	private JComboBox<Timone> boxTimone;

	private ButtonGroup modello_group;
	private JRadioButton modelloBase;
	private JRadioButton modelloMedio;
	private JRadioButton modelloAvanzato;
	
	private JButton btnAction;

	public FormBarcaFrame() {
		initialize();
		this.btnAction.setText("Aggiungi Barca");
		btnAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String numero_serie = boxNumeroSerie.getText();
				Scafo scafo = (Scafo) boxScafo.getSelectedItem();
				Chiglia chiglia = (Chiglia) boxChiglia.getSelectedItem();
				Deriva deriva = (Deriva) boxDeriva.getSelectedItem();
				Alberatura alberatura = (Alberatura) boxAlberatura.getSelectedItem();
				Timone timone = (Timone) boxTimone.getSelectedItem();

				ModelloBarca modello = null;

				if (modelloBase.isSelected())
					modello = ModelloBarca.BASE;
				else if (modelloMedio.isSelected())
					modello = ModelloBarca.MEDIA;
				else if (modelloAvanzato.isSelected())
					modello = ModelloBarca.AVANZATA;

				int result = CatalogoBarche.aggiungiBarca(numero_serie, scafo, chiglia, deriva, alberatura, timone, modello);

				if (result == 1) {
					MessageBox.showInformation(title, String.format("Barca con numero serie '%s' inserita con successo!", numero_serie));
					CatalogoBarche.salvaCatalogo();
					dispose();
				} else if (result == 0) {
					MessageBox.showWarning(title, String.format("Barca con numero serie '%s' già presente in Catalogo!", numero_serie));
					dispose();
				}

			}
		});
	}
	
	public FormBarcaFrame(Barca barca) {
		
		if (barca == null)
			throw new IllegalArgumentException("L'istanza da modificare non può essere vuota!");
		
		initialize();
		this.btnAction.setText("Modifica Barca");
		this.boxNumeroSerie.setEnabled(false);
		this.modelloBase.setEnabled(false);
		this.modelloMedio.setEnabled(false);
		this.modelloAvanzato.setEnabled(false);
		btnAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String numero_serie = barca.getNumeroSerie();
				Scafo scafo = (Scafo) barca.getScafo();
				Chiglia chiglia = barca.getChiglia();
				Deriva deriva = barca.getDeriva();
				Alberatura alberatura = barca.getAlberatura();
				Timone timone = barca.getTimone();

				ModelloBarca modello = barca.getModello();

				int result = CatalogoBarche.modificaBarca(numero_serie, scafo, chiglia, deriva, alberatura, timone, modello);

				if (result == 1) {
					MessageBox.showInformation(title, String.format("Barca con numero serie '%s' modificata con successo!", numero_serie));
					CatalogoBarche.salvaCatalogo();
					dispose();
				} else if (result == 0) {
					MessageBox.showWarning(title, String.format("Barca con numero serie '%s' non presente in Catalogo!", numero_serie));
					dispose();
				}

			}
		});
	}
	
	private void initialize() {
		setTitle(title);
		setModal(true);
		setModalExclusionType(ModalExclusionType.NO_EXCLUDE);
		setType(Type.POPUP);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 325);
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panel);
		panel.setLayout(null);

		// Numero Serie

		JLabel labelSerie = new JLabel("Numero Serie:");
		labelSerie.setLabelFor(boxNumeroSerie);
		labelSerie.setBounds(10, 14, 154, 14);
		panel.add(labelSerie);

		this.boxNumeroSerie = new JTextField();
		boxNumeroSerie.setBounds(174, 11, 100, 20);
		panel.add(boxNumeroSerie);
		boxNumeroSerie.setColumns(10);

		// Scafo

		JLabel labelScafo = new JLabel("Seleziona Scafo:");
		labelScafo.setBounds(10, 46, 154, 14);
		panel.add(labelScafo);

		this.boxScafo = new JComboBox<Scafo>(Scafo.values());
		labelScafo.setLabelFor(boxScafo);
		boxScafo.setBounds(174, 42, 100, 22);
		panel.add(boxScafo);

		// Chiglia

		JLabel labelChiglia = new JLabel("Seleziona Chiglia:");
		labelChiglia.setLabelFor(boxChiglia);
		labelChiglia.setBounds(10, 79, 154, 14);
		panel.add(labelChiglia);

		boxChiglia = new JComboBox<Chiglia>(Chiglia.values());
		boxChiglia.setBounds(174, 75, 100, 22);
		panel.add(boxChiglia);

		// Deriva

		JLabel labelDeriva = new JLabel("Seleziona Deriva:");
		labelDeriva.setBounds(10, 112, 154, 14);
		panel.add(labelDeriva);

		this.boxDeriva = new JComboBox<Deriva>(Deriva.values());
		labelDeriva.setLabelFor(boxDeriva);
		boxDeriva.setBounds(174, 108, 100, 22);
		panel.add(boxDeriva);

		// Alberatura

		JLabel lblSelezionaAlberatura = new JLabel("Seleziona Alberatura:");
		lblSelezionaAlberatura.setBounds(10, 145, 154, 14);
		panel.add(lblSelezionaAlberatura);

		this.boxAlberatura = new JComboBox<Alberatura>(Alberatura.values());
		lblSelezionaAlberatura.setLabelFor(boxAlberatura);
		boxAlberatura.setBounds(174, 141, 100, 22);
		panel.add(boxAlberatura);

		// Timone

		JLabel labelTimone = new JLabel("Seleziona Timone:");
		labelTimone.setBounds(10, 178, 154, 14);
		panel.add(labelTimone);

		this.boxTimone = new JComboBox<Timone>(Timone.values());
		boxTimone.setBounds(174, 174, 100, 22);
		panel.add(boxTimone);

		// Modello

		this.modello_group = new ButtonGroup();

		this.modelloBase = new JRadioButton("Base");
		modelloBase.setSelected(true);
		modelloBase.setBounds(10, 228, 89, 23);
		panel.add(modelloBase);
		modello_group.add(modelloBase);

		this.modelloMedio = new JRadioButton("Medio");
		modelloMedio.setBounds(101, 228, 85, 23);
		panel.add(modelloMedio);
		modello_group.add(modelloMedio);

		this.modelloAvanzato = new JRadioButton("Avanzato");
		modelloAvanzato.setBounds(184, 228, 90, 23);
		panel.add(modelloAvanzato);
		modello_group.add(modelloAvanzato);

		JLabel labelModello = new JLabel("Seleziona Modello:");
		labelModello.setBounds(10, 207, 264, 14);
		this.panel.add(labelModello);
		
		// Bottone Aggiungi
		btnAction = new JButton();
		btnAction.setBounds(10, 258, 264, 23);
		panel.add(btnAction);
	}
}
