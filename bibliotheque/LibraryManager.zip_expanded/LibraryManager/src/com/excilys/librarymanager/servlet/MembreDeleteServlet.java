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
import com.excilys.librarymanager.service.MembreService;
import com.excilys.librarymanager.service.impl.MembreServiceImpl;
import com.excilys.librarymanager.tables.Membre;

public class MembreDeleteServlet extends HttpServlet{

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		switch (action) {
			case "/membre_delete":
				deleteMem(request, response);
				break;
			default:
				System.out.println("Default redirecting case from " + action + " !");
		}
}

	private void deleteMem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		MembreService memService = MembreServiceImpl.getInstance();
		Membre membre = new Membre();
		try {
			membre  = memService.getById(Integer.parseInt(request.getParameter("id")));						//membre en elimination
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw new ServletException("Probleme dans l'elimination d'un membre" , e);						//exception comme demandé
		}
		request.setAttribute("membre", membre);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_delete.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MembreService memService = MembreServiceImpl.getInstance();
		List<Membre>  membres = new ArrayList<Membre>(); 
		String show = request.getParameter("show");
		try {
			memService.delete(Integer.parseInt(request.getParameter("id")));								//elimination du membre
			membres = memService.getList();																	//list des membres
		} catch(ServiceException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw new ServletException("Probleme dans l'elimination d'un membre" , e);						//exception comme demandé
		}
		request.setAttribute("membres", membres);															//membres pour le fichier .jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_list.jsp");
		dispatcher.forward(request, response);	
	}
}