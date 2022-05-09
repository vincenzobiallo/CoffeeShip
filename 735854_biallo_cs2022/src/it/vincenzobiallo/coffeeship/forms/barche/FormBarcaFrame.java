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
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.awt.Dialog.ModalExclusionType;

public class FormBarcaFrame extends JFrame {

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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FormBarcaFrame(Barca... istance) {

		Barca barca = null;
		if (istance.length > 1)
			throw new IllegalArgumentException("Troppe istanze passate come parametro!");
		else if (istance.length == 1)
			barca = istance[0];

		setModalExclusionType(ModalExclusionType.NO_EXCLUDE);
		setType(Type.POPUP);
		setTitle(title);
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

		this.boxScafo = new JComboBox(Scafo.values());
		labelScafo.setLabelFor(boxScafo);
		boxScafo.setBounds(174, 42, 100, 22);
		panel.add(boxScafo);

		// Chiglia

		JLabel labelChiglia = new JLabel("Seleziona Chiglia:");
		labelChiglia.setLabelFor(boxChiglia);
		labelChiglia.setBounds(10, 79, 154, 14);
		panel.add(labelChiglia);

		boxChiglia = new JComboBox(Chiglia.values());
		boxChiglia.setBounds(174, 75, 100, 22);
		panel.add(boxChiglia);

		// Deriva

		JLabel labelDeriva = new JLabel("Seleziona Deriva:");
		labelDeriva.setBounds(10, 112, 154, 14);
		panel.add(labelDeriva);

		this.boxDeriva = new JComboBox(Deriva.values());
		labelDeriva.setLabelFor(boxDeriva);
		boxDeriva.setBounds(174, 108, 100, 22);
		panel.add(boxDeriva);

		// Alberatura

		JLabel lblSelezionaAlberatura = new JLabel("Seleziona Alberatura:");
		lblSelezionaAlberatura.setBounds(10, 145, 154, 14);
		panel.add(lblSelezionaAlberatura);

		this.boxAlberatura = new JComboBox(Alberatura.values());
		lblSelezionaAlberatura.setLabelFor(boxAlberatura);
		boxAlberatura.setBounds(174, 141, 100, 22);
		panel.add(boxAlberatura);

		// Timone

		JLabel labelTimone = new JLabel("Seleziona Timone:");
		labelTimone.setBounds(10, 178, 154, 14);
		panel.add(labelTimone);

		this.boxTimone = new JComboBox(Timone.values());
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

		if (barca == null) {

			// Bottone Aggiungi

			JButton btnAggiungi = new JButton("Aggiungi Barca");
			btnAggiungi.addActionListener(new ActionListener() {
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

					boolean result = CatalogoBarche.aggiungiBarca(numero_serie, scafo, chiglia, deriva, alberatura, timone, modello);

					if (result) {
						MessageBox.showInformation(title, String.format("Barca con numero serie '%s' inserita con successo!", numero_serie));
						CatalogoBarche.salvaCatalogo();
						dispose();
					} else {
						MessageBox.showWarning(title, String.format("Barca con numero serie '%s' già presente in Catalogo!", numero_serie));
						dispose();
					}

				}
			});
			btnAggiungi.setBounds(10, 258, 264, 23);
			panel.add(btnAggiungi);

		} else {
			this.boxNumeroSerie.setText(barca.getNumeroSerie());
			this.boxScafo.setSelectedItem(barca.getScafo());
			this.boxChiglia.setSelectedItem(barca.getChiglia());
			this.boxDeriva.setSelectedItem(barca.getDeriva());
			this.boxAlberatura.setSelectedItem(barca.getAlberatura());
			this.boxTimone.setSelectedItem(barca.getTimone());
			
			// TODO Modello
		}

	}
}
