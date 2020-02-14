package com.excilys.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.service.LivreService;
import com.excilys.librarymanager.service.impl.LivreServiceImpl;
import com.excilys.librarymanager.tables.Livre;

public class LivreAddServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		switch (action) {
			case "/livre_add":
				addLivre(request, response);
				break;
			default:
				System.out.println("Default redirecting case from " + action + " !");
		}
}

	private void addLivre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_add.jsp");			//ici il faut seulement ajouter le livre
		dispatcher.forward(request, response);
	}


protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	LivreService livService = LivreServiceImpl.getInstance();
	Livre livre = new Livre();
	int idLivre = -1;				//identifiant pour la redirection sur la page des details
	try {
		idLivre = livService.create(request.getParameter("titre"), request.getParameter("auteur"), request.getParameter("isbn"));
		livre = livService.getById(idLivre);		//recuperation de l'identifiant
	} catch (ServiceException e) {
		System.out.println(e.getMessage());
		e.printStackTrace();
    }
	
	if(idLivre!=-1) {
		request.setAttribute("livre", livre);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_details.jsp");
		dispatcher.forward(request, response);
	}else {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_add.jsp");
		dispatcher.forward(request, response);
	}
}
}
