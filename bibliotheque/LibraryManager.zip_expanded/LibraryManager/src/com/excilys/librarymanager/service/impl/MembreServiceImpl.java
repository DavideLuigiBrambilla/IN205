package com.excilys.librarymanager.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.excilys.librarymanager.dao.MembreDao;
import com.excilys.librarymanager.dao.impl.MembreDaoImpl;
import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.service.EmpruntService;
import com.excilys.librarymanager.service.MembreService;
import com.excilys.librarymanager.tables.Membre;

public class MembreServiceImpl implements MembreService {

	//Singleton
	private static MembreServiceImpl instance = new MembreServiceImpl();
	private MembreServiceImpl() { }	
	public static MembreService getInstance() {		
		return instance;
	}
	
	@Override
	public List<Membre> getList() throws ServiceException {
		// TODO Auto-generated method stub
		MembreDao membreDao = MembreDaoImpl.getInstance();
		List<Membre> membres = new ArrayList<Membre>();		
		try {
			membres = membreDao.getList();
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return membres;
	}

	@Override
	public List<Membre> getListMembreEmpruntPossible() throws ServiceException {		//list pour voir qui eut emprunter un livre
		// TODO Auto-generated method stub
		MembreDao membreDao = MembreDaoImpl.getInstance();
		List<Membre> membres = new ArrayList<Membre>();	
		List<Membre> membresEmpruntDispo = new ArrayList<Membre>();
		EmpruntService emp = EmpruntServiceImpl.getInstance();
		try {																			//cycle sur la list
			membres = membreDao.getList(); 
			for(int i = 0; i < membres.size(); i++) {
				if(emp.isEmpruntPossible(membres.get(i))) {
					membresEmpruntDispo.add(membres.get(i));
				}
			}
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return membresEmpruntDispo;														//return la list correct
	}

	@Override
	public Membre getById(int id) throws ServiceException {
		// TODO Auto-generated method stub
		MembreDao membreDao = MembreDaoImpl.getInstance();
		Membre membre = new Membre();
		try {
			membre = membreDao.getById(id);
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return membre;
	}

	@Override
	public int create(String nom, String prenom, String adresse, String email, String telephone)
			throws ServiceException {
		// TODO Auto-generated method stub;
		MembreDao livreDao = MembreDaoImpl.getInstance();
		int i = -1;
		if(nom == null || nom == "" || prenom == null || prenom == "") {
			throw new ServiceException("Nom ou Prenom null ou vide");					//return l'exception comme demandé
		}
		try {
			i = livreDao.create(nom.toUpperCase(), prenom, adresse, email, telephone);
		}  catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} 
		return i;
	}

	@Override
	public void update(Membre membre) throws ServiceException {
		// TODO Auto-generated method stub
		MembreDao membreDao = MembreDaoImpl.getInstance();
		if(membre.getNom() == null ||membre.getNom() == "" || membre.getPrenom() == null || membre.getPrenom() == "") {
			throw new ServiceException("Nom ou Prenom null ou vide");					//return l'exception comme demandé
		}
		try {
			membre.setNom(membre.getNom().toUpperCase());
			membreDao.update(membre);
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
	}

	@Override
	public void delete(int id) throws ServiceException {
		// TODO Auto-generated method stub
		MembreDao membreDao = MembreDaoImpl.getInstance();
		try {
			membreDao.delete(id);
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
	}

	@Override
	public int count() throws ServiceException {
		// TODO Auto-generated method stub
		MembreDao membreDao = MembreDaoImpl.getInstance();
		int i = -1;
		try {
			i = membreDao.count();
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return i;
	}

}
