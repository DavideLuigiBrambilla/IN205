package com.excilys.librarymanager.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.service.EmpruntService;
import com.excilys.librarymanager.service.LivreService;
import com.excilys.librarymanager.service.MembreService;
import com.excilys.librarymanager.service.impl.EmpruntServiceImpl;
import com.excilys.librarymanager.service.impl.LivreServiceImpl;
import com.excilys.librarymanager.service.impl.MembreServiceImpl;
import com.excilys.librarymanager.tables.Emprunt;
import com.excilys.librarymanager.tables.Livre;
import com.excilys.librarymanager.tables.Membre;

public class EmpruntAddServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		switch (action) {
			case "/emprunt_add":
				addEmprunt(request, response);
				break;
			default:
				System.out.println("Default redirecting case from " + action + " !");
			}
	}

	private void addEmprunt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		MembreService memService = MembreServiceImpl.getInstance();
		LivreService livService = LivreServiceImpl.getInstance();
		List<Membre>  membres = new ArrayList<Membre>();
		List<Livre>  livres = new ArrayList<Livre>();
		try {
			membres = memService.getListMembreEmpruntPossible();		//emprunt possible
			livres = livService.getListDispo();							//livres dispo
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw new ServletException("Probleme dans la creation d'un emprunt" , e);
		}
		request.setAttribute("membres", membres);			//on doit visualiser les membres et les livres dispo  
		request.setAttribute("livres", livres);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_add.jsp");			//on va sur l'ajout d'un emprunt
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EmpruntService empService = EmpruntServiceImpl.getInstance();
		List<Emprunt>  emprunts = new ArrayList<Emprunt>();
		try {
			empService.create(Integer.parseInt(request.getParameter("idMembre")), Integer.parseInt(request.getParameter("idLivre")), LocalDate.now());
			emprunts = empService.getListCurrent();			//apres la creation on visualise les emprunts currents
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw new ServletException("Probleme dans la creation d'un emprunt" , e);		//lancement de Servlet exception comme demandé
	    }
		request.setAttribute("emprunts", emprunts);																//visualisartion de la liste current des emprunts
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_list.jsp");			//on va sur la list des emprunts
		dispatcher.forward(request, response);
	}
}

