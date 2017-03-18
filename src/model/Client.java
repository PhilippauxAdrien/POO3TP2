package model;

/**
 * Cette classe représent un client comme présent dans la BDD
 * @author adrie
 *
 */
public class Client {
	
	private Integer numClient;
	private String nom;
	private String prenom;
	private Integer numSecu;
	private Double revenu;
	private String telephone;
	private Integer nivRisque;
	
	public Client(Integer numClient, String nom, String prenom, Integer numSecu, String telephone, Double revenu, Integer nivRisque) {
		super();
		this.numClient = numClient;
		this.nom = nom;
		this.prenom = prenom;
		this.numSecu = numSecu;
		this.revenu = revenu;
		this.nivRisque = nivRisque;
		this.telephone = telephone;
	}
	
	public Integer getNumClient() {
		return numClient;
	}
	public void setNumClient(Integer numClient) {
		this.numClient = numClient;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public Integer getNumSecu() {
		return numSecu;
	}
	public void setNumSecu(Integer numSecu) {
		this.numSecu = numSecu;
	}
	public Double getRevenu() {
		return revenu;
	}
	public void setRevenu(Double revenu) {
		this.revenu = revenu;
	}
	public Integer getNivRisque() {
		return nivRisque;
	}
	public void setNivRisque(Integer nivRisque) {
		this.nivRisque = nivRisque;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Override
	public String toString() {
		return numClient + ", " + nom + ", " + prenom;
	}
	
}
