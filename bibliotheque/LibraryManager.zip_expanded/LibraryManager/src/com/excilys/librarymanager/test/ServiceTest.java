package com.excilys.librarymanager.test;

import java.time.LocalDate;

import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.service.EmpruntService;
import com.excilys.librarymanager.service.LivreService;
import com.excilys.librarymanager.service.MembreService;
import com.excilys.librarymanager.service.impl.EmpruntServiceImpl;
import com.excilys.librarymanager.service.impl.LivreServiceImpl;
import com.excilys.librarymanager.service.impl.MembreServiceImpl;
import com.excilys.librarymanager.tables.Abonnement;
import com.excilys.librarymanager.tables.Emprunt;
import com.excilys.librarymanager.tables.Livre;
import com.excilys.librarymanager.tables.Membre;

public class ServiceTest {
	public static void main(String[] args) throws ServiceException {
		/**
		 * Emprunt
		 */
		
		//INITIALISATION OBJET MEMBRE
//		Emprunt emprunt = new Emprunt(7, m, l, LocalDate.of(2019, 12, 1), LocalDate.now());
				
		//INITIALISATION SERVICE
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();
		
		//GETLIST
		empruntService.getList();
		System.out.println("\n\n\n\n");
		empruntService.getListCurrent();
//		empruntService.getListCurrentByLivre(4);
//		empruntService.getListCurrentByMembre(5);
		
		//Emprunt creation
//		empruntService.create(4, 5, LocalDate.now());
		
		//Get by ID
//		empruntService.getById(8);
		
		//Create
//		empruntService.create(5, 6, LocalDate.now());
		
		//ReturnBook
//		empruntService.returnBook(1);
		
		//VERIFICATION METHODE COUNT
//		empruntService.count();
		
		//Disponibilité livre
		if(empruntService.isLivreDispo(14)) {
			System.out.println("OUI");
		}
		else {System.out.println("noOUI");}
		
		
		//Membre membre = new Membre();
		// Disponibilité emprunt
		//empruntService.isEmpruntPossible(membre);
		
		/**
		 * Livre
		 */
		
//		LivreService livreservice = LivreServiceImpl.getInstance();
		
		//GET
//		livreservice.getList();
//		livreservice.getListDispo();
//		livreservice.getById(13);
		
		//VERIFICATION DE METHODE CREATE
//		livreservice.create("VictorA", "Titre", "isbnelegido2");
		
		//VERIFICATION DE METHODE 
//		Livre livre = new Livre(12,"Araujo","Victor","elegidoisbn2");
//		livreservice.update(livre);
		
		//DELETE
//		livreservice.delete(16);
		
		//Count
//		livreservice.count();
		
		/**
		 * Membre
		 */
		
//		MembreService membreService = MembreServiceImpl.getInstance();
		
//		GET
//		membreService.getList();
		//TODO Logica del service
//		membreService.getListMembreEmpruntPossible();
//		membreService.getById(8);
		
//		membreService.create("Araujo", "Victor", "Vzla", "correo@dominio.com", "5694641");

//		
//		Membre membre = new Membre(13,"AraujoBelen","VictorA","ParisFrance","meemail@email.com","0145", Abonnement.PREMIUM);
//		membreService.update(membre); //Corregir el 7mo parametro del update
//		
//		System.out.println("*********************");
//		membreService.getById(13);
		
//		membreService.delete(13);
		
//		membreService.count();
	}

}
