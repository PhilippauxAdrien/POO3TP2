package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import model.Client;


@SuppressWarnings("serial")
public class Modification extends JDialog implements ActionListener, WindowListener, KeyListener{

	private JLabel lNom, lPrenom, lRevenu, lTel;
	private JTextField tfNom, tfPrenom, tfRevenu, tfTel;
	private JButton bsave, bannuler;
	private GestionClient myGC;
	
	public Modification(GestionClient gc) {

		myGC = gc;
		this.setTitle("Modification d'une personne");
		this.setSize(500, 300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new GridLayout(0, 2));
		
		bsave = new JButton("Enregistrer");
		bannuler = new JButton("Annuler");
		bannuler.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Annulation de la modif");
				Modification.this.dispose();
			}
		});

		if (myGC.getClient() != null) {
			lNom = new JLabel("Nom");
			tfNom = new JTextField(myGC.getClient().getNom());

			lPrenom = new JLabel("Prenom");
			tfPrenom = new JTextField(myGC.getClient().getPrenom());

			lRevenu = new JLabel("Revenu en € ");
			tfRevenu = new JTextField("" + myGC.getClient().getRevenu());
			
			lTel = new JLabel("Telephone");
			tfTel = new JTextField(myGC.getClient().getTelephone());
			
			tfNom.addKeyListener(this);
			tfPrenom.addKeyListener(this);
			tfTel.addKeyListener(this);
			tfRevenu.addKeyListener(this);
		}
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
		
		this.add(bsave);
		this.add(bannuler);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Enregistrement des modifs");
		myGC.getClient().setNom(tfNom.getText());
		myGC.getClient().setPrenom(tfPrenom.getText());
		myGC.getClient().setTelephone(tfTel.getText());
		myGC.getClient().setRevenu(Double.parseDouble(tfRevenu.getText().replaceAll(",", ".")));
		
		/**
		 * On fait appel à la requete d'update créée dans la classe InsuranceRequest
		 */
		try {
			myGC.getIr().updateClient(myGC.getClient());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		/**
		 * On refresh les clients de la liste
		 */
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
		myGC.getBh().getBmodifier().setEnabled(false);

	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			bsave.doClick();
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			bannuler.doClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
