package com.excilys.librarymanager.tables;

public class Livre {
	private int id;
	private String titre;
	private String auteur;
	private String isbn;
	
	public Livre() {
	}
	
	public Livre(String titre, String auteur, String isbn) {
		this();
		this.titre = titre;
		this.auteur = auteur;
		this.isbn = isbn;
	}
	
	public Livre(int id, String titre, String auteur, String isbn) {
		this(titre, auteur, isbn);
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitre() {
		return this.titre;
	}
	
	public void setTitre(String titre) {
		this.titre = titre;
	}
	
	public String getAut() {
		return this.auteur;
	}
	
	public void setAut(String aut) {
		this.auteur = aut;
	}
	
	public String getISBN() {
		return this.isbn;
	}
	
	public void setISBN(String isbn) {
		this.isbn = isbn;
	}

	public String toString(){  
		  return getClass().getSimpleName() + "{"
				  + "\n ID-LIV: " + this.id
				  + "\n TITRE: " + this.titre
				  + "\n AUTEUR: " + this.auteur
				  + "\n ISBN: " + this.isbn
				  + "}"; 
		 }
}
