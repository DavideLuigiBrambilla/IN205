package com.excilys.librarymanager.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.excilys.librarymanager.dao.LivreDao;
import com.excilys.librarymanager.dao.impl.LivreDaoImpl;
import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.service.EmpruntService;
import com.excilys.librarymanager.service.LivreService;
import com.excilys.librarymanager.tables.Livre;

public class LivreServiceImpl implements LivreService{

	//Singleton
	private static LivreServiceImpl instance = new LivreServiceImpl();
	private LivreServiceImpl() { }	
	public static LivreService getInstance() {		
		return instance;
	}
	
	@Override
	public List<Livre> getList() throws ServiceException {
		// TODO Auto-generated method stub
		LivreDao livreDao = LivreDaoImpl.getInstance();
		List<Livre> livres = new ArrayList<>();		
		try {
			livres = livreDao.getList();
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return livres;
	}

	@Override
	public List<Livre> getListDispo() throws ServiceException {
		// TODO Auto-generated method stub
		LivreDao livreDao = LivreDaoImpl.getInstance();
		List<Livre> livres = new ArrayList<>();	
		List<Livre> livresDispo = new ArrayList<>();
		EmpruntService emp = EmpruntServiceImpl.getInstance();
		try {
			livres = livreDao.getList(); 
			for(int i = 0; i< livres.size();i++) {
				if(emp.isLivreDispo(livres.get(i).getId())) {
					livresDispo.add(livres.get(i));
				}
			}	
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return livresDispo;
	}

	@Override
	public Livre getById(int id) throws ServiceException {
		// TODO Auto-generated method stub
		LivreDao livreDao = LivreDaoImpl.getInstance();
		Livre livre = new Livre();
		try {
			livre = livreDao.getById(id);
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		}
		return livre;
	}

	@Override
	public int create(String titre, String auteur, String isbn) throws ServiceException {
		// TODO Auto-generated method stub
		LivreDao livreDao = LivreDaoImpl.getInstance();
		int i =-1;
		if(titre == null || titre == "") {
			throw new ServiceException("Titre null ou vide");		//lancement des exception comme demandé
		}
		try {
			i = livreDao.create(titre, auteur, isbn);
		}  catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} 
		return i;
	}

	@Override
	public void update(Livre livre) throws ServiceException {
		// TODO Auto-generated method stub
		LivreDao livreDao = LivreDaoImpl.getInstance();
		if(livre.getTitre() == null || livre.getTitre() == "") {
			throw new ServiceException("Titre null ou vide");
		}
		try {
			livreDao.update(livre);
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());
			}
	}

	@Override
	public void delete(int id) throws ServiceException {
		// TODO Auto-generated method stub
		LivreDao livreDao = LivreDaoImpl.getInstance();
		try {
			livreDao.delete(id);
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());
			}
	}

	@Override
	public int count() throws ServiceException {
		// TODO Auto-generated method stub
		LivreDao livreDao = LivreDaoImpl.getInstance();
		int i=-1;
		try {
			i = livreDao.count();
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());
			}
		return i;
	}

}
