package com.excilys.librarymanager.tables;


public class Membre {
	
	private int id;
	private String nom;
	private String prenom;
	private String  addresse;
	private String email;
	private String telephone;
	private Abonnement abonnement;
	
	public Membre() {
	}
	
	public Membre(String nom, String prenom, String addresse, String email, String tel, Abonnement abb) {
		this();
		this.nom = nom;
		this.prenom = prenom;
		this.addresse = addresse;
		this.email = email;
		this.telephone = tel;
		this.abonnement = abb;
	}
	
	public Membre(int id, String nom, String prenom, String addresse, String email, String tel, Abonnement abb) {
		this(nom, prenom, addresse, email, tel, abb);
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		
		this.id = id;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getPrenom() {
		return this.prenom;
	}
	
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	public String getAddr() {
		return this.addresse;
	}
	
	public void setAddr(String addr) {
		this.addresse = addr;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String mail) {
		this.email = mail;
	}
	
	public String getTel() {
		return this.telephone;
	}
	
	public void setTel(String tel) {
		this.telephone = tel;
	}
	
	public Abonnement getAbonnement() {
		return this.abonnement;
	}
	
	public void setAbonnement(Abonnement abb) {
		this.abonnement = abb;
	}
	
	public String toString(){  
		  return getClass().getSimpleName() + "{"
				  + "\n ID-MEM: " + this.id +
				  "\n NOM: " + this.nom +
				  "\n PRENOM: " + this.prenom + 
				  "\n ADDRESSE: " + this.addresse +
				  "\n EMAIL: " + this.email +
				  "\n TELEPHONE: " + this.telephone +
				  "\n ABONNEMENT: " + this.abonnement
				  + "}";  
		 }
}
