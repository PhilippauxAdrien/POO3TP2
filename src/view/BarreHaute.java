package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.Client;


@SuppressWarnings("serial")
public class BarreHaute extends JPanel implements ActionListener {
	private JTextArea zoneAffichage;
	private JButton bmodifier;
	private JButton bajouter;
	private JButton supprimer;

	private GestionClient myGC;
	private JTextField recherche;
	private JLabel label;

	public BarreHaute(GestionClient gc) {
		myGC = gc;
		this.label = new JLabel("Rechercher");
		this.zoneAffichage = new JTextArea();
		this.bmodifier = new JButton("Modifier");
		this.bajouter = new JButton("Ajouter un client");
		this.supprimer = new JButton("Supprimer le client");

		bmodifier.addActionListener(this);
		bmodifier.setEnabled(false);
		supprimer.addActionListener(this);
		supprimer.setEnabled(false);
		bajouter.addActionListener(this);
		bajouter.setEnabled(true);
		
		recherche = new JTextField(10);
		recherche.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent arg0) {
			}

			/**
			 * A l'appui sur une touche on recherche si un nom, prenom coincide avec la saisie
			 */
			public void keyReleased(KeyEvent arg0) {
				try {
					rechercheResults();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			public void keyPressed(KeyEvent arg0) {
			}
		});

		this.add(label);
		this.add(recherche);
		this.add(this.zoneAffichage);
		this.add(this.bmodifier);
		this.add(bajouter);
		this.add(supprimer);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void rechercheResults() throws SQLException {
		String pattern ="";
		if (recherche.getText().length() > 0) {
			pattern = getRecherche().getText().toUpperCase();
		} else {
			pattern = "";
		}
		DefaultListModel dlm = new DefaultListModel();
		
		myGC.getIr().ensClients(pattern);
		for (Client ccurr : myGC.getIr().getMesClients()) {
			dlm.addElement(ccurr.getNom() + " " + ccurr.getPrenom());
		}
		myGC.getListe().setModel(dlm);

		// Reset de l'affichage des infos de la personne
		myGC.getBh().getZoneAffichage().setText(null);
	}

	public JTextArea getZoneAffichage() {
		return zoneAffichage;
	}

	public void setZoneAffichage(JTextArea zoneAffichage) {
		this.zoneAffichage = zoneAffichage;
	}

	public JButton getBmodifier() {
		return bmodifier;
	}

	public void setBmodifier(JButton bmodifier) {
		this.bmodifier = bmodifier;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(bmodifier)){
			Modification m = new Modification(myGC);
			m.setVisible(true);
		}
		else if(e.getSource().equals(bajouter)){
			AjoutClient ac;
			try {
				ac = new AjoutClient(myGC);
				ac.setVisible(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if(e.getSource().equals(supprimer)){
			AjoutClient ac;
			try {
				myGC.getIr().deleteClient(myGC.getClient());
				
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
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public JTextField getRecherche() {
		return recherche;
	}

	public void setRecherche(JTextField recherche) {
		this.recherche = recherche;
	}

	public JButton getSupprimer() {
		return supprimer;
	}

	public void setSupprimer(JButton supprimer) {
		this.supprimer = supprimer;
	}

}
