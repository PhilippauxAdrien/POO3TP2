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
import model.Produit;
import model.TableModelResultSet;

/**
 * Cette classe contient toutes les requetes n�cessaires pour la gestion de ce
 * TP2. Exemples : maj Client, liste des clients, suppression client, ...
 * 
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
	public static final String REQUEST_NB_CLIENTS = "select * from client order by nClient desc";
	public static final String REQUEST_ADD_CLIENT = "INSERT into CLIENT(nom, prenom, nNumSecu, telephone, revenu, nrisque) values(?,?,?,?,?,?)";
	public static final String REQUEST_ADD_NUMSECU = "INSERT into NUMSECU(sexe, anneeNaissance, moisNaissance, departement, commune, ordre, cle) values(?, ?, ?, ?, ?, ?, ?)";
	public static final String REQUEST_DELETE_CLIENT = "DELETE from client where nClient= ? ";
	public static final String REQUEST_DELETE_NUMSECU = "DELETE from numsecu where nNumSecu= ? ";
	public static final String REQUEST_SELECT_CONTRAT = "Select p.nom AS NOMP, c.numero, c.dateDebut, c.dateEcheance, p.nProduit as NProd, p.tauxRevenu as txRevenu, p.effortBudgetaire as efbud, c.nContrat, c.nProduit as NPoduitc FROM contrat c INNER JOIN produit p on p.nProduit = c.nProduit WHERE c.nClient = ?";
	public static final String REQUEST_SELECT_PRODUIT = "SELECT * from produit";
	public static final String REQUEST_SELECT_BY_CLIENTS_BY_PRODUIT = "SELECT c.nProduit , c.nClient, cl.nClient, cl.nom, cl.prenom, cl.nNumSecu, cl.telephone, cl.revenu, cl.nRisque FROM contrat c INNER JOIN client cl on c.nClient = cl.nClient WHERE c.nProduit = ?";

	/**
	 * M�thode de connexion � la base de donn�e mysql
	 */
	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/tp_assurance?" + "user=root&password=root");
			System.out.println("Connexion � la base r�ussie.");
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Probl�me de connexion � la base.");
			e.printStackTrace();
		}

	}

	/**
	 * Effectue un s�lect sur la table Client pour r�cup�rer tous les clients de
	 * celle-ci
	 * 
	 * @param pattern
	 *            - Chaine de caract�re pour filtrer les clients (doit �tre
	 *            pr�sente dans le nom ou pr�nom)
	 * @return Une liste de client correspondant � la requete effectu�e.
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
	 * R�cup�re le nb de client en base
	 */
	public int getNbClients() throws SQLException {
		int nb;

		preparedStatement = connect.prepareStatement(REQUEST_NB_CLIENTS);
		resultSet = preparedStatement.executeQuery();
		resultSet.next();

		nb = resultSet.getInt("nClient");

		return nb;

	}

	/**
	 * Mets � jour un client dans la table Client
	 * 
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

	/**
	 * Ajout un client dans la table Client
	 * 
	 * @throws SQLException
	 */
	public void addClient(Client c) throws SQLException {
		preparedStatement = connect.prepareStatement(REQUEST_ADD_CLIENT);
		preparedStatement.setString(1, c.getNom());
		preparedStatement.setString(2, c.getPrenom());
		preparedStatement.setInt(3, c.getNumSecu());
		preparedStatement.setString(4, c.getTelephone());
		preparedStatement.setBigDecimal(5, new BigDecimal(c.getRevenu()));
		preparedStatement.setInt(6, c.getNivRisque());

		preparedStatement.executeUpdate();
	}

	/**
	 * Ajoute un numsecu dans la table numsecu
	 * 
	 * @throws SQLException
	 */
	public void addNumsecu(int sexe, int annee, int mois, int dep, int comm, int ordre, int cle) throws SQLException {
		preparedStatement = connect.prepareStatement(REQUEST_ADD_NUMSECU);
		preparedStatement.setInt(1, sexe);
		preparedStatement.setInt(2, annee);
		preparedStatement.setInt(3, mois);
		preparedStatement.setInt(4, dep);
		preparedStatement.setInt(5, comm);
		preparedStatement.setInt(6, ordre);
		preparedStatement.setInt(7, cle);

		preparedStatement.executeUpdate();
	}

	/**
	 * Supprime un client de la table Client
	 * 
	 * @param c
	 * @throws SQLException
	 */
	public void deleteClient(Client c) throws SQLException {
		preparedStatement = connect.prepareStatement(REQUEST_DELETE_CLIENT);

		preparedStatement.setInt(1, c.getNumClient());

		preparedStatement.executeUpdate();

		preparedStatement = connect.prepareStatement(REQUEST_DELETE_NUMSECU);

		preparedStatement.setInt(1, c.getNumSecu());
		preparedStatement.executeUpdate();

	}

	public List<Produit> ensProduit() throws SQLException {
		
		List<Produit> prod = new ArrayList<Produit>();
		
		preparedStatement = connect.prepareStatement(REQUEST_SELECT_PRODUIT);
		resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()){
			Produit p = new Produit(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3),  resultSet.getDouble(4),  resultSet.getDouble(5));
			prod.add(p);
		}
		
		return prod;
	}
	
	public List<Client> getClientsByProduit(Produit p) throws SQLException {
		
		List<Client> clients = new ArrayList<Client>();
		
		preparedStatement = connect.prepareStatement(REQUEST_SELECT_BY_CLIENTS_BY_PRODUIT);
		preparedStatement.setInt(1, p.getnProduit());
		resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()){
			Client c = new Client(resultSet.getInt(2), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getString(7), resultSet.getDouble(8), resultSet.getInt(9));
			clients.add(c);
		}
		
		return clients;
	}
	
	/**
	 * R�cup�re les diff�rents contrats d'un client
	 * @param c - Client c 
	 * @return un mod�le pouvant �tre utilis� dans une JTable
	 * @throws SQLException
	 */
	public TableModelResultSet ensContrat(Client c) throws SQLException {
		TableModelResultSet t;
		
		preparedStatement = connect.prepareStatement(REQUEST_SELECT_CONTRAT);
		preparedStatement.setInt(1, c.getNumClient());

		resultSet = preparedStatement.executeQuery();
		t = new TableModelResultSet(resultSet);
		
		return t;
	}

	public List<Client> getMesClients() {
		return mesClients;
	}

	public void setMesClients(List<Client> mesClients) {
		this.mesClients = mesClients;
	}

	/**
	 * M�thode de fermeture de la connexion � la base de donn�e et des objets
	 * utilis�s
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
