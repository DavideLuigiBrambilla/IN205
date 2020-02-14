package com.excilys.librarymanager.servlet;

import java.io.IOException;
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
import com.excilys.librarymanager.service.impl.EmpruntServiceImpl;
import com.excilys.librarymanager.service.impl.LivreServiceImpl;
import com.excilys.librarymanager.tables.Emprunt;
import com.excilys.librarymanager.tables.Livre;

public class LivreDetailsServlet extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		switch (action) {
			case "/livre_details":
				detailsLiv(request, response);
				break;
			default:
				System.out.println("Default redirecting case from " + action + " !");
		}
	}

	private void detailsLiv(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		LivreService livService = LivreServiceImpl.getInstance();
		EmpruntService empService = EmpruntServiceImpl.getInstance();
		Livre livre = new Livre();
		List<Emprunt> emprunts = new ArrayList<Emprunt>();
		try {
			livre = livService.getById(Integer.parseInt(request.getParameter("id")));						//informations de livre
			emprunts = empService.getListCurrentByLivre(Integer.parseInt(request.getParameter("id"))); 		//information sur un possible emprunt current
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw new ServletException("Probleme dans l'affichage des details d'un livre" , e);		//exception comme demandé
		}
		
		request.setAttribute("livre", livre);
		request.setAttribute("emprunts", emprunts);			
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_details.jsp");
		dispatcher.forward(request, response);			
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LivreService livService = LivreServiceImpl.getInstance();
		EmpruntService empService = EmpruntServiceImpl.getInstance();
		Livre livre = new Livre();
		List<Emprunt> emprunts= new ArrayList<Emprunt>();
		try {
			livre = livService.getById(Integer.parseInt(request.getParameter("id")));		//mis a jour du livre
			livre.setTitre(request.getParameter("titre"));
			livre.setAut(request.getParameter("auteur"));
			livre.setISBN(request.getParameter("isbn"));
			livService.update(livre);																		//livre a visualiser
			emprunts = empService.getListCurrentByLivre(Integer.parseInt(request.getParameter("id")));	//information su un possible emprunt current
				
		} catch(ServiceException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw new ServletException("Probleme dans l'affichage des details d'un livre" , e);		//exception comme demandé
		}
		request.setAttribute("livre", livre);
		request.setAttribute("emprunts", emprunts);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_details.jsp");
		dispatcher.forward(request, response);
	}
	
}

