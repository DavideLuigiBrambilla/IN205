package com.excilys.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.service.MembreService;
import com.excilys.librarymanager.service.impl.MembreServiceImpl;
import com.excilys.librarymanager.tables.Membre;

public class MembreAddServlet extends HttpServlet{

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		switch (action) {
			case "/membre_add":
				addMembre(request, response);
				break;
			default:
				System.out.println("Default redirecting case from " + action + " !");
			}
	}

	private void addMembre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_add.jsp");
		dispatcher.forward(request, response);
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MembreService memService = MembreServiceImpl.getInstance();
		Membre membre = new Membre();
		int idMembre = -1;			//identifiant
		
		try {
			idMembre = memService.create(request.getParameter("nom"),request.getParameter("prenom"), request.getParameter("adresse"), request.getParameter("email"), request.getParameter("telephone"));
			membre = memService.getById(idMembre);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
	    }
		
		if(idMembre!=-1) {				//si c'est bien je vais visualiser les details, sinon je return sur add
			request.setAttribute("membre", membre);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_details.jsp");
			dispatcher.forward(request, response);
		}else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_add.jsp");
			dispatcher.forward(request, response);
		}	
	}
	
}

