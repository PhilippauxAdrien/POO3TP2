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

/**
 * Cette classe contient toutes les requetes nécessaires pour la gestion de ce TP2.
 * Exemples : maj Client, liste des clients, suppression client, ...
 * @author adrie
 *
 */
public class InsuranceRequest {

	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	private List<Client> mesClients = new ArrayList<>();
	public static final String REQUEST_SELECT_CLIENTS = "SELECT * from CLIENT WHERE UPPER(nom) LIKE ? OR UPPER(prenom) LIKE ?";
	public static final String REQUEST_UPDATE_CLIENT = "UPDATE CLIENT c SET c.nom=?, c.prenom=?, c.telephone=?, c.revenu=? WHERE c.nClient=?";

	/**
	 * Méthode de connexion à la base de donnée mysql
	 */
	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/tp_assurance?" + "user=root&password=root");
			System.out.println("Connexion à la base réussie.");
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Problème de connexion à la base.");
			e.printStackTrace();
		}

	}

	/**
	 * Effectue un sélect sur la table Client pour récupérer tous les clients de celle-ci
	 * @param pattern - Chaine de caractère pour filtrer les clients (doit être présente dans le nom ou prénom)
	 * @return Une liste de client correspondant à la requete effectuée.
	 * @throws SQLException
	 */
	public List<Client> ensClients(String pattern) throws SQLException {
		Client curr;
		mesClients.clear();
		preparedStatement = connect.prepareStatement(REQUEST_SELECT_CLIENTS);
		preparedStatement.setString(1, "%" + pattern + "%");
		preparedStatement.setString(2, "%" + pattern + "%");

		resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			curr = new Client(resultSet.getInt("nClient"), resultSet.getString("nom"), resultSet.getString("prenom"),
					resultSet.getInt("nNumSecu"), resultSet.getString("telephone"), resultSet.getDouble("revenu"),
					resultSet.getInt("nRisque"));
			mesClients.add(curr);
		}
		return mesClients;
	}

	/**
	 * Mets à jour un client dans la table Client
	 * @param c
	 * @throws SQLException
	 */
	public void updateClient(Client c) throws SQLException {
		preparedStatement = connect.prepareStatement(REQUEST_UPDATE_CLIENT);
		preparedStatement.setString(1, c.getNom());
		preparedStatement.setString(2, c.getPrenom());
		preparedStatement.setString(3, c.getTelephone());
		preparedStatement.setBigDecimal(4, new BigDecimal(c.getRevenu()));
		preparedStatement.setInt(5, c.getNumClient());

		preparedStatement.executeUpdate();
	}

	public List<Client> getMesClients() {
		return mesClients;
	}

	public void setMesClients(List<Client> mesClients) {
		this.mesClients = mesClients;
	}

	/**
	 * Méthode de fermeture de la connexion à la base de donnée et des objets utilisés
	 */
	public void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}
}
