package com.excilys.librarymanager.test;

import java.time.LocalDate;

import com.excilys.librarymanager.dao.EmpruntDao;
import com.excilys.librarymanager.dao.LivreDao;
import com.excilys.librarymanager.dao.MembreDao;
import com.excilys.librarymanager.dao.impl.EmpruntDaoImpl;
import com.excilys.librarymanager.dao.impl.LivreDaoImpl;
import com.excilys.librarymanager.dao.impl.MembreDaoImpl;
import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.tables.Abonnement;
import com.excilys.librarymanager.tables.Emprunt;
import com.excilys.librarymanager.tables.Livre;
import com.excilys.librarymanager.tables.Membre;

public class DaoTest {

	
	public static void main(String[] args) throws DaoException {
		
		//INITIALISATION OBJET EMPRUNT
		Membre m = new Membre(50,"AraujoBelen","VictorA","ParisFrance","meemail@email.com","0145", Abonnement.PREMIUM);
		Livre l = new Livre(11,"Araujo","Victor","elegidoisbn");
		Emprunt emprunt = new Emprunt(7, m, l, LocalDate.of(2019, 12, 1), LocalDate.now());
		//INITIALISATION DAO
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		//GETLIST
		empruntDao.getList();
		empruntDao.getListCurrent();
		empruntDao.getListCurrentByLivre(2);
		empruntDao.getListCurrentByMembre(5);
		//D'ABORD, CREATION D'UN EMPRUNT
		empruntDao.create(4, 5, LocalDate.now());
		//L'EMPRUNT DONNE POUR LA PREMIERE EXECUTION, ID = 7
		//J'INTRODUIS 7 ET JE MONTRE QU'IL EXISTE, ET EN MEME TEMPS, QUE LA METHODE GETBYID FONCTIONNE BIEN
		empruntDao.getById(7);
		//METHODE UPDATE, AVEC LES VALEURS CONTENUES DANS L'OBJET
		empruntDao.update(emprunt);
		//JE REFAIS UNE RECHERCHE ET JE MONTRE QUE LA METHODE FONCTIONNE BIEN
		empruntDao.getById(1);
		//VERIFICATION METHODE COUNT
		empruntDao.count();
		
	//*****************************************************************************************************
		
		//INITIALISATION OBJET LIVRE
		Livre livre = new Livre(11,"Araujo","Victor","elegidoisbn");
		//INITIALISATION DAO
		LivreDao livreDao = LivreDaoImpl.getInstance();
		//VERIFICATION DE METHODE GETBYID
		livreDao.getById(1);
		//VERIFICATION DE METHODE CREATE
		livreDao.create("Victor", "Titre", "isbnelegido");
		//VERIFICATION DE METHODE 
		livreDao.update(livre);
		//JE REFAIS UNE RECHERCHE POUR MONTRER QUE LA FONCTION UPDATE MARCHE BIEN
		livreDao.getById(11);
		//VERIFICATION DELETE
		livreDao.delete(11);
		//SI ON FAIT UNE RECHERCHE APRES D'AVOIR EFFACE UN LIVRE ON VA 
		//VOIR QUE LE CHAMPS EST VIDE, ON VERIFIE DONC LE BON FONCTIONNEMENT DE LA METHODE
		livreDao.getById(11);
		//VERIFICATION COUNT
		livreDao.count();
		
	//***********************************************************************************************************
	
	//INITIALISATION DAO
		MembreDao membreDao =  MembreDaoImpl.getInstance();
	//INITIALISATION OBJET MEMBRE
		//Membre membre = new Membre(50,"AraujoBelen","VictorA","ParisFrance","meemail@email.com","0145", Abonnement.PREMIUM);
	//VERIFICATION GETBYID
		membreDao.getById(1);
	//VERIFICATION CREATE
		membreDao.create("Belen", "Antonio", "Venezuela", "yo@email.com", "0000");
		membreDao.getById(13);
	//membreDao.update(membre);
	//VERIFICATION COUNT	
		membreDao.count();
	//VERIFICATION GETLIST
		membreDao.getList();
	
		}
}
