package it.vincenzobiallo.coffeeship.forms.utenti;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Classe Astratta che Generalizza i Form di Inserimento di Persona
 *
 * @param <T>
 */
abstract class AbstractForm<T> extends JDialog {

	private static final long serialVersionUID = 1L;
	
	protected String TITLE = "";
	
	private JPanel panel;
	
	protected JTextField boxCodiceFiscale;
	protected JTextField boxNome;
	protected JTextField boxCognome;
	protected JDateChooser boxDataNascita;
	protected JLabel labelDataNascita;
	
	protected JButton btnAction;

	/**
	 * Costruttore di Inserimento
	 */
	protected AbstractForm() {
		initialize();
	}
	
	/**
	 * Costruttore di Modifica Persona
	 * @param persona
	 */
	protected AbstractForm(T persona) {
		
		if (persona == null)
			throw new IllegalArgumentException("L'istanza da modificare non può essere vuota!");
		
		initialize();
		boxCodiceFiscale.setEnabled(false);
	}
	
	/**
	 * Inserisce gli elementi all'interno della GUI
	 */
	private void initialize() {
		setTitle(TITLE);
		setResizable(false);
		setModal(false);
		setType(Type.POPUP);
		setModalExclusionType(ModalExclusionType.NO_EXCLUDE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 330, 230);
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panel);
		panel.setLayout(null);
		
		this.boxCodiceFiscale = new JTextField();
		boxCodiceFiscale.setBounds(154, 11, 150, 20);
		panel.add(boxCodiceFiscale);
		boxCodiceFiscale.setColumns(16);	
		
		JLabel labelCodiceFiscale = new JLabel("Codice Fiscale:");
		labelCodiceFiscale.setBounds(10, 14, 134, 14);
		panel.add(labelCodiceFiscale);
		
		JLabel labelNome = new JLabel("Nome:");
		labelNome.setBounds(10, 45, 134, 14);
		panel.add(labelNome);
		
		this.boxNome = new JTextField();
		boxNome.setColumns(16);
		boxNome.setBounds(154, 42, 150, 20);
		panel.add(boxNome);
		
		JLabel labelCognome = new JLabel("Cognome:");
		labelCognome.setBounds(10, 76, 134, 14);
		panel.add(labelCognome);
		
		this.boxCognome = new JTextField();
		boxCognome.setColumns(10);
		boxCognome.setBounds(154, 73, 150, 20);
		panel.add(boxCognome);
		
		this.boxDataNascita = new JDateChooser();
		boxDataNascita.setDateFormatString("dd/MM/yyyy");
		boxDataNascita.setBounds(154, 101, 150, 20);
		panel.add(boxDataNascita);
		
		labelDataNascita = new JLabel("Data Nascita:");
		labelDataNascita.setBounds(10, 104, 134, 14);
		panel.add(labelDataNascita);
		
		this.btnAction = new JButton();
		btnAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonAction();
			}
		});
		btnAction.setBounds(10, 157, 294, 23);
		panel.add(btnAction);
	}
	
	/**
	 * Funzione Astratta da implementare a seconda del tipo di persona
	 */
	public abstract void buttonAction();

}
