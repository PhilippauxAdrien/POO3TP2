package core;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Client;

public class InsuranceRequest {

	  private Connection connect = null;
      private PreparedStatement preparedStatement = null;
      private ResultSet resultSet = null;

      private List<Client> mesClients = new ArrayList<>();
      public static final String REQUEST_SELECT_CLIENTS = "SELECT * from CLIENT WHERE UPPER(nom) LIKE ? OR UPPER(prenom) LIKE ?";
      public static final String REQUEST_UPDATE_CLIENT = "UPDATE CLIENT c SET c.nom=?, c.prenom=?, c.telephone=?, c.revenu=? WHERE c.nClient=?";

	public void connect(){
		
      try {
         // This will load the MySQL driver, each DB has its own driver
		Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
	      connect = DriverManager.getConnection("jdbc:mysql://localhost/tp_assurance?"
	                                             + "user=root&password=root");
	      System.out.println("Connexion à la base réussie.");
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Problème de connexion à la base.");
			e.printStackTrace();
		}
      
	}
	
	public List<Client> ensClients(String pattern) throws SQLException{
		Client curr;
		preparedStatement = connect.prepareStatement(REQUEST_SELECT_CLIENTS);
		preparedStatement.setString(1, "%" + pattern + "%");
		preparedStatement.setString(2, "%" + pattern + "%");

		resultSet = preparedStatement.executeQuery();
        
		while (resultSet.next()) {
			curr = new Client(resultSet.getInt("nClient"), resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getInt("nNumSecu"), resultSet.getString("telephone"), resultSet.getDouble("revenu"), resultSet.getInt("nRisque"));
		}
		return mesClients;
	}

	public void updateClient(Client c, String nom, String prenom, String telephone, String revenu) throws SQLException{
		preparedStatement = connect.prepareStatement(REQUEST_UPDATE_CLIENT);
		preparedStatement.setString(1, nom);
		preparedStatement.setString(2, prenom);
		preparedStatement.setString(3, telephone);
		preparedStatement.setBigDecimal(4, new BigDecimal(revenu));
		preparedStatement.setInt(5, c.getNumClient());
		
		preparedStatement.executeUpdate();
	}
	
	public List<Client> getMesClients() {
		return mesClients;
	}

	public void setMesClients(List<Client> mesClients) {
		this.mesClients = mesClients;
	}
	
}
