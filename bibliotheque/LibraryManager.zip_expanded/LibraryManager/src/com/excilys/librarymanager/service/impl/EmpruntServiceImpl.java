package com.excilys.librarymanager.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.librarymanager.dao.EmpruntDao;
import com.excilys.librarymanager.dao.impl.EmpruntDaoImpl;
import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.service.EmpruntService;
import com.excilys.librarymanager.tables.Emprunt;
import com.excilys.librarymanager.tables.Membre;

public class EmpruntServiceImpl implements EmpruntService {

	//Singleton comme demandé dans la consigne
	private static EmpruntServiceImpl instance = new EmpruntServiceImpl();
	private EmpruntServiceImpl() { }	
	public static EmpruntService getInstance() {		
		return instance;
	}
	
	@Override
	public List<Emprunt> getList() throws ServiceException {
		// TODO Auto-generated method stub
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();	//get instance
		List<Emprunt> emprunts = new ArrayList<Emprunt>();		
		try {
			emprunts = empruntDao.getList();					//utilisation des methodes defini dans DAO
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return emprunts;
	}

	@Override
	public List<Emprunt> getListCurrent() throws ServiceException {
		// TODO Auto-generated method stub
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		List<Emprunt> emprunts = new ArrayList<Emprunt>();		
		try {
			emprunts = empruntDao.getListCurrent();
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return emprunts;
	}

	@Override
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException {
		// TODO Auto-generated method stub
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		List<Emprunt> emprunts = new ArrayList<Emprunt>();		
		try {
			emprunts = empruntDao.getListCurrentByMembre(idMembre);
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return emprunts;
	}

	@Override
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException {
		// TODO Auto-generated method stub
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		List<Emprunt> emprunts = new ArrayList<Emprunt>();		
		try {
			emprunts = empruntDao.getListCurrentByMembre(idLivre);
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return emprunts;
	}

	@Override
	public Emprunt getById(int id) throws ServiceException {
		// TODO Auto-generated method stub
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		Emprunt emprunt = new Emprunt();
		try {
			emprunt = empruntDao.getById(id);
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return emprunt;
	}

	@Override
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws ServiceException {
		// TODO Auto-generated method stub
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		try {
			empruntDao.create(idMembre, idLivre, dateEmprunt);
		}  catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
	}

	@Override
	public void returnBook(int id) throws ServiceException {
		// TODO Auto-generated method stub
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		try {
			empruntDao.update(empruntDao.getById(id));		//lorsque on a un retour d'un livre on va faire un update pour fixer la date de retour
		}  catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		
	}

	@Override
	public int count() throws ServiceException {
		// TODO Auto-generated method stub
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		int i=-1;
		try {
			i = empruntDao.count();
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());
			}
		return i;
	}

	@Override
	public boolean isLivreDispo(int idLivre) throws ServiceException {
		// TODO Auto-generated method stub
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		List<Emprunt> cur = new ArrayList<Emprunt>();
		try {
			 cur = empruntDao.getListCurrentByLivre(idLivre);
			 return cur.isEmpty();							//je fais un retour pour voir si le livre n'est pas dans la liste des livres empruntés
				
		}  catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return false;
	}

	@Override
	public boolean isEmpruntPossible(Membre membre) throws ServiceException {
		// TODO Auto-generated method stub
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		List<Emprunt> cur = new ArrayList<Emprunt>();
		try {
			 cur = empruntDao.getListCurrentByMembre(membre.getId());
			 return cur.size() < membre.getAbonnement().getNum();		//je vais voir si la personne peut obtenir un livre
				
		}  catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return false;
	}

}
