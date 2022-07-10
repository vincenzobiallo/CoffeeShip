package it.vincenzobiallo.coffeeship.forms.listini;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import it.vincenzobiallo.coffeeship.barche.Barca;
import it.vincenzobiallo.coffeeship.utils.MessageBox;
import it.vincenzobiallo.coffeeship.vendite.CatalogoListini;
import it.vincenzobiallo.coffeeship.vendite.Listino;

import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FormListinoFrame extends JDialog {

	private static final long serialVersionUID = 1L;
	private static final String title = "Gestione Listino";
	
	private JButton btnOperation;
	private JTextField boxBarca;
	private JSpinner spinnerVendita;
	private JSpinner spinnerNoleggio;

	public FormListinoFrame(Barca barca) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		initialize();
		btnOperation.setText("Aggiungi Listino");
		boxBarca.setText(barca.getNumeroSerie());
	}

	public FormListinoFrame(Listino listino) {
		initialize();
		btnOperation.setText("Modifica Listino");
		boxBarca.setText(listino.getBarca().getNumeroSerie());
		spinnerVendita.setValue(listino.getPrezzoStandard());
		spinnerNoleggio.setValue(listino.getCanoneStandard());
	}
	
	private void initialize() {
		setTitle(title);
		getContentPane().setLayout(null);
		setResizable(false);
		setModal(false);
		setModalExclusionType(ModalExclusionType.NO_EXCLUDE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setType(Type.POPUP);
		setMinimumSize(new Dimension(300, 200));
		
		boxBarca = new JTextField();
		boxBarca.setBounds(130, 11, 144, 20);
		getContentPane().add(boxBarca);
		boxBarca.setColumns(10);
		boxBarca.setEnabled(false);
		
		JLabel labelBarca = new JLabel("Barca:");
		labelBarca.setLabelFor(boxBarca);
		labelBarca.setBounds(10, 14, 110, 14);
		getContentPane().add(labelBarca);
		
		JLabel labelPrezzo = new JLabel("Prezzo di Vendita:");
		labelPrezzo.setBounds(10, 45, 110, 14);
		getContentPane().add(labelPrezzo);
		
		JLabel lblCanoneDiNoleggio = new JLabel("Canone di Noleggio:");
		lblCanoneDiNoleggio.setBounds(10, 73, 110, 14);
		getContentPane().add(lblCanoneDiNoleggio);
		
		spinnerVendita = new JSpinner();
		spinnerVendita.setModel(new SpinnerNumberModel(0.0, 0.0, null, 0.1));
		spinnerVendita.setBounds(130, 42, 144, 20);
		getContentPane().add(spinnerVendita);
		
		spinnerNoleggio = new JSpinner();
		spinnerNoleggio.setModel(new SpinnerNumberModel(0.0, 0.0, null, 0.1));
		spinnerNoleggio.setBounds(130, 70, 144, 20);
		getContentPane().add(spinnerNoleggio);
		
		btnOperation = new JButton("Aggiungi Listino");
		btnOperation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (((double) spinnerVendita.getValue() <= 0) || (((double) spinnerNoleggio.getValue()) <= 0)) {
					MessageBox.showWarning("Listini", "Valori non validi!");
				} else {
				
					boolean result = CatalogoListini.aggiungiListino(boxBarca.getText(), (double) spinnerVendita.getValue(), (double) spinnerNoleggio.getValue());
					
					if (result)
						MessageBox.showInformation("Listini", "Listino aggiornato con successo!");
					else
						MessageBox.showWarning("Listini", "Impossibile aggiornare il Listino!");
					
					CatalogoListini.salvaCatalogo();
					
					dispose();
				}
			}
		});
		btnOperation.setBounds(10, 127, 264, 23);
		getContentPane().add(btnOperation);
		
	}
	
}
