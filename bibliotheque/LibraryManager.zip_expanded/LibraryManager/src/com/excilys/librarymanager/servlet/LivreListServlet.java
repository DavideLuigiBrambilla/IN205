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
import com.excilys.librarymanager.service.LivreService;
import com.excilys.librarymanager.service.impl.LivreServiceImpl;
import com.excilys.librarymanager.tables.Livre;

public class LivreListServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		switch (action) {
			case "/livre_list":
				listLiv(request, response);
				break;
			default:
				System.out.println("Default redirecting case from " + action + " !");
		}
	}

	private void listLiv(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
		LivreService livService = LivreServiceImpl.getInstance();
		List<Livre>  livres = new ArrayList<Livre>();
		try {
			livres =livService.getList();					//recuperation de la list des livres
		} catch(ServiceException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute("livres", livres);				//livres pour les afficher dans le fichier .jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_list.jsp");
		dispatcher.forward(request, response);		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
