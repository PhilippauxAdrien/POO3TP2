package view;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.TableModelResultSet;

public class AfficheContrat extends JDialog{

	JTable table;
	JScrollPane scrollPane;

	public AfficheContrat() {
		this.setVisible(true);
		this.setTitle("Contrats du client");
		this.setSize(1000,300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				
	}

	public AfficheContrat(TableModelResultSet t) {
		this();
		scrollPane = new JScrollPane();
		table = new JTable(t);
		scrollPane.setViewportView(table);

		this.add(scrollPane);
	}
	
}
