package model;

public class Produit {

	private int nProduit;
	private String nom;
	private int nRisque;
	private Double tauxRevenu, effortBudgetaire;
	
	public Produit(int nProduit, String nom, int nRisque, Double tauxRevenu, Double effortBudgetaire) {
		super();
		this.nProduit = nProduit;
		this.nom = nom;
		this.nRisque = nRisque;
		this.tauxRevenu = tauxRevenu;
		this.effortBudgetaire = effortBudgetaire;
	}
	
	public int getnProduit() {
		return nProduit;
	}
	public void setnProduit(int nProduit) {
		this.nProduit = nProduit;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getnRisque() {
		return nRisque;
	}
	public void setnRisque(int nRisque) {
		this.nRisque = nRisque;
	}
	public Double getTauxRevenu() {
		return tauxRevenu;
	}
	public void setTauxRevenu(Double tauxRevenu) {
		this.tauxRevenu = tauxRevenu;
	}
	public Double getEffortBudgetaire() {
		return effortBudgetaire;
	}
	public void setEffortBudgetaire(Double effortBudgetaire) {
		this.effortBudgetaire = effortBudgetaire;
	}
	
}
