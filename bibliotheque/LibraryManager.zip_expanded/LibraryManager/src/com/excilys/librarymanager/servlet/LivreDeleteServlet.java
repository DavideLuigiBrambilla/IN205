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

public class LivreDeleteServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		switch (action) {
			case "/livre_delete":
				deleteLiv(request, response);
				break;
			default:
				System.out.println("Default redirecting case from " + action + " !");
		}
}

	private void deleteLiv(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
		LivreService livService = LivreServiceImpl.getInstance();
		Livre livre = new Livre();
		try {
			livre  = livService.getById(Integer.parseInt(request.getParameter("id")));		//livre en elimination
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw new ServletException("Probleme dans l'elimination d'un livre" , e);		//servlet exception comme demandé
		}
		request.setAttribute("livre", livre);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_delete.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LivreService livService = LivreServiceImpl.getInstance();
		List<Livre>  livres = new ArrayList<Livre>(); 
		String show = request.getParameter("show");
		try {
			livService.delete(Integer.parseInt(request.getParameter("id")));		//elimination
			livres = livService.getList();											//visualisation des livres
		} catch(ServiceException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw new ServletException("Probleme dans l'elimination d'un livre" , e);		//servlet exception comme demandé
		}
		request.setAttribute("livres", livres);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_list.jsp");
		dispatcher.forward(request, response);		
	}

}
