package com.excilys.librarymanager.test;

import com.excilys.librarymanager.tables.Emprunt;
import com.excilys.librarymanager.tables.Livre;
import com.excilys.librarymanager.tables.Membre;

public class ModeleTest {

	public static void main(String[] args) {
		Emprunt emprunt = new Emprunt();
		Livre livre = new Livre();
		Membre membre = new Membre();
		System.out.println(emprunt.toString());
		System.out.println(livre.toString());
		System.out.println(membre.toString());
				
		System.out.println(emprunt.toString());
		

	}
	
}
