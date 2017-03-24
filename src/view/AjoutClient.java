package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import model.Client;

public class AjoutClient extends JDialog implements ActionListener, WindowListener, KeyListener{

	private JLabel lNom, lPrenom, lRevenu, lTel, lSecu, lRisque, lDob, lSexe, lCodePostal, lordre, lcle;
	private JTextField tfNom, tfPrenom, tfRevenu, tfTel, tfSecu, tfCodePostal, tfOrdre, tfCle;
	private JButton bsave, bannuler;
	private GestionClient myGC;
	private JComboBox listRisque, listsexe;
	private String [] risque = {"1","2","3","4","5"};
	private String [] genre = {"Homme", "Femme"};
	
	private int nbClients;
	private JDatePickerImpl datePicker;
	
	public AjoutClient(GestionClient myGC) throws SQLException {
		this.myGC = myGC;
		
		this.setTitle("Ajout d'un client");
		this.setSize(500, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new GridLayout(0, 2));
		
		UtilDateModel model = new UtilDateModel();
		//model.setDate(20,04,2014);
		// Need this...
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		// Don't know about the formatter, but there it is...
	    datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		 		
		listRisque = new JComboBox(risque);
		nbClients = myGC.getIr().getNbClients();
		listsexe = new JComboBox<>(genre);
		
		bsave = new JButton("Enregistrer");
		bannuler = new JButton("Annuler");
		bannuler.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Annulation de la création");
				AjoutClient.this.dispose();
			}
		});

		
			lNom = new JLabel("Nom");
			tfNom = new JTextField();

			lPrenom = new JLabel("Prenom");
			tfPrenom = new JTextField();

			lRevenu = new JLabel("Revenu en € ");
			tfRevenu = new JTextField();
			
			lTel = new JLabel("Telephone");
			tfTel = new JTextField();
			
			lSecu = new JLabel("Num secu");
			tfSecu = new JTextField(nbClients+1 +"");
			tfSecu.setEnabled(false);
			
			lRisque = new JLabel("Niveau risque");
			
			lDob = new JLabel("Date de naissance");
			lSexe = new JLabel("Sexe");
			
			lCodePostal = new JLabel("Code postal");
			tfCodePostal = new JTextField();
			
			lordre = new JLabel("Ordre");
			tfOrdre = new JTextField();
			lcle = new JLabel("Cle");
			tfCle = new JTextField();
			
			tfNom.addKeyListener(this);
			tfPrenom.addKeyListener(this);
			tfTel.addKeyListener(this);
			tfRevenu.addKeyListener(this);
			tfSecu.addKeyListener(this);
			
			bsave.addActionListener(this);
			this.addKeyListener(this);
			
			this.add(lNom);
			this.add(tfNom);
			this.add(lPrenom);
			this.add(tfPrenom);
			this.add(lTel);
			this.add(tfTel);
			this.add(lRevenu);
			this.add(tfRevenu);
			this.add(lSecu);
			this.add(tfSecu);
			this.add(lRisque);
			this.add(listRisque);
			this.add(lDob);
			this.add(datePicker);
			this.add(lSexe);
			this.add(listsexe);
			this.add(lCodePostal);
			this.add(tfCodePostal);
			this.add(lordre);
			this.add(tfOrdre);
			this.add(lcle);
			this.add(tfCle);
			
		this.add(bsave);
		this.add(bannuler);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	/**
	Traitement ajout client 
	*/
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Date d = (Date) datePicker.getModel().getValue();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(d);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int sexe = listsexe.getSelectedItem().toString().equals("Homme") ? 1 : 2;
		int dep = Integer.parseInt(tfCodePostal.getText().substring(0,2));
		int comm = Integer.parseInt(tfCodePostal.getText().substring(2,5));
		int ordre = Integer.parseInt(tfOrdre.getText());
		int cle = Integer.parseInt(tfCle.getText());

		
		
		Client c;
		c = new Client(0, tfNom.getText(), tfPrenom.getText(), Integer.parseInt(tfSecu.getText()), tfTel.getText(), Double.parseDouble(tfRevenu.getText()), Integer.parseInt(listRisque.getSelectedItem().toString()));
		try {
			myGC.getIr().addNumsecu(sexe, year, month, dep, comm, ordre, cle);
			myGC.getIr().addClient(c);
			
			DefaultListModel dlm = new DefaultListModel();
			
			try {
				myGC.getIr().ensClients("");
				for (Client ccurr :myGC.getIr().getMesClients()) {
					dlm.addElement(ccurr.getNom() + " " + ccurr.getPrenom());
				}
				myGC.getListe().setModel(dlm);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			this.dispose();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
