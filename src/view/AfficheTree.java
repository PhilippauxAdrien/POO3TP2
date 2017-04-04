package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import model.Client;
import model.Produit;
import model.TableModelResultSet;
 
public class AfficheTree extends JFrame implements ActionListener{
    private JTree tree;
    private ButtonGroup btngrp;
	private JRadioButton byClient, byProduit;
	private DefaultMutableTreeNode root;
	private GestionClient myGC;
	
    public AfficheTree(GestionClient gc) throws SQLException {
    	myGC = gc;
    	
        JPanel boutonPane = new JPanel();
        JPanel content = new JPanel();
    	btngrp = new ButtonGroup();
    	byClient = new JRadioButton("Par client", true);
    	byProduit = new JRadioButton("Par produit");
		
    	byClient.addActionListener(this);
    	byProduit.addActionListener(this);

    	btngrp.add(byClient);
    	btngrp.add(byProduit);
    	
        //create the root node
        root = new DefaultMutableTreeNode("Clients");
        
		DefaultMutableTreeNode myNode;
		TableModelResultSet t;
        myGC.getIr().ensClients("");
		for(Client c : myGC.getIr().getMesClients()){
			myNode = new DefaultMutableTreeNode(c.getNom());
			t = myGC.getIr().ensContrat(c);
			if(t.getRowCount() == 0)
				myNode.add(new DefaultMutableTreeNode("Aucun contrat"));
			
			for(int i=0; i < t.getRowCount(); i++){
				myNode.add(new DefaultMutableTreeNode(t.getValueAt(i, 0) + " n°" + t.getValueAt(i, 1)));
			}
			root.add(myNode);
		}       
         
        //create the tree by passing in the root node
        tree = new JTree(root);
                
        boutonPane.add(byClient);
        boutonPane.add(byProduit);
        
        content.setLayout(new CardLayout());
        content.add(tree);
        
        this.getContentPane().add(boutonPane, BorderLayout.NORTH);
        this.getContentPane().add(content, BorderLayout.CENTER);

        this.setSize(400, 700);
        this.setLocationRelativeTo(null);
        this.setTitle("Arbre de données");       
        this.setVisible(true);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		DefaultMutableTreeNode myNode;
		DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
	    root = (DefaultMutableTreeNode)model.getRoot();
	    root.removeAllChildren();
		
		if(e.getSource().equals(byClient)){
			try {
				DefaultMutableTreeNode mNode;
				TableModelResultSet t;
		        myGC.getIr().ensClients("");
				for(Client c : myGC.getIr().getMesClients()){
					mNode = new DefaultMutableTreeNode(c.getNom());
					t = myGC.getIr().ensContrat(c);
					
					if(t.getRowCount() == 0)
						mNode.add(new DefaultMutableTreeNode("Aucun contrat"));

					for(int i=0; i < t.getRowCount(); i++){
						mNode.add(new DefaultMutableTreeNode(t.getValueAt(i, 0) + " n°" + t.getValueAt(i, 1)));
					}
					root.add(mNode);
				}
				model.reload(root);

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if(e.getSource().equals(byProduit)){
			try {
				DefaultMutableTreeNode mNode;
				TableModelResultSet t;

			    model = (DefaultTreeModel)tree.getModel();
			    root = (DefaultMutableTreeNode)model.getRoot();
			    root.removeAllChildren();
				
		       List<Produit> prods =  myGC.getIr().ensProduit();
		       List<Client> clients;
				for(Produit p : prods){
					mNode = new DefaultMutableTreeNode(p.getNom());
					clients = myGC.getIr().getClientsByProduit(p);
					for(Client c : clients){
						mNode.add(new DefaultMutableTreeNode(c.getPrenom() + " " + c.getNom()));
					}
					root.add(mNode);
				}
				model.reload(root);

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
	}
     
}
