package view;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;

import core.InsuranceRequest;
import model.Client;

@SuppressWarnings("serial")
public class GestionClient extends JFrame implements WindowListener, MouseListener{
	
	public static boolean listFocused;
	private BarreHaute bh;
	private JList liste;
	private Client client;
	private GestionClient me;
	private InsuranceRequest ir;
	
	public GestionClient(InsuranceRequest ir) throws IOException, SQLException {
		me = this;
		this.ir = ir;
		
		this.addWindowListener(this);
		this.setTitle("TP1 gestion de personnes");
		this.setSize(900,500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.bh = new BarreHaute(this);
		this.liste = new JList();
				
		DefaultListModel dlm = new DefaultListModel();
		
		ir.ensClients("");
		for (Client ccurr :ir.getMesClients()) {
			dlm.addElement(ccurr.getNom() + " " + ccurr.getPrenom());
		}
		this.liste.setModel(dlm);
		
		this.liste.addMouseListener(this);
		
		this.setLayout(new BorderLayout());
		this.add(this.bh, BorderLayout.NORTH);
		this.add(this.liste, BorderLayout.CENTER);
		setVisible(true);
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public JList getListe() {
		return liste;
	}

	public void setListe(JList liste) {
		this.liste = liste;
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);	
		ir.close();
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
	public void mouseClicked(MouseEvent e) {
		getBh().getBmodifier().setEnabled(true);
		
		for(Client c : ir.getMesClients()){
			if(liste.getSelectedValue().equals(c.getNom() + " " + c.getPrenom())){
				client = c;
				bh.getZoneAffichage().setText(client.toString());
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	public BarreHaute getBh() {
		return bh;
	}

	public void setBh(BarreHaute bh) {
		this.bh = bh;
	}

	public InsuranceRequest getIr() {
		return ir;
	}

	public void setIr(InsuranceRequest ir) {
		this.ir = ir;
	}


	
}
