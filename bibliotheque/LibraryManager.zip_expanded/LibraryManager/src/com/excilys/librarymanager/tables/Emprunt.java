package com.excilys.librarymanager.tables;

public class Emprunt {
	private int id;
	private Membre membre;
	private Livre livre;
	private java.time.LocalDate dateEmprunt;
	private java.time.LocalDate dateRetour;
	
	public Emprunt() {
	}
	
	public Emprunt(Membre membre, Livre livre, java.time.LocalDate dateEmpr) {
		this();
		this.membre = membre;
		this.livre = livre;
		this.dateEmprunt = dateEmpr;
	}
	
	public Emprunt(Membre membre, Livre livre, java.time.LocalDate dateEmpr, java.time.LocalDate dateRet) {
		this(membre, livre, dateEmpr);
		this.dateRetour = dateRet;
	}
	
	public Emprunt(int id, Membre membre, Livre livre, java.time.LocalDate dateEmpr, java.time.LocalDate dateRet) {
		this(membre, livre, dateEmpr, dateRet);
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Membre getMem() {
		return this.membre;
	}
	
	public void setMem(Membre mem) {
		this.membre = mem;
	}
	
	public Livre getLiv() {
		return this.livre;
	}
	
	public void setLiv(Livre liv) {
		this.livre = liv;
	}
	
	public java.time.LocalDate getDateEmpr() {
		return this.dateEmprunt;
	}
	
	public void setDateEmp(java.time.LocalDate DateEm) {
		this.dateEmprunt = DateEm;
	}
	
	public java.time.LocalDate getDateRet() {
		return this.dateRetour;
	}
	
	public void setDateRet(java.time.LocalDate DateRet) {
		this.dateRetour = DateRet;
	}
	
	public String toString(){  
		  return getClass().getSimpleName() + "{"
				  +"\n ID-EMPR: " + this.id +
				  "\n MEMBRE:" + this.membre +
				  "\n LIVRE:" + this.livre +
				  "\n DATE EMPRUNT: " + this.dateEmprunt +
				  "\n DATE RETOUR: " + this.dateRetour
				  + "}"; 
		 } 
}
