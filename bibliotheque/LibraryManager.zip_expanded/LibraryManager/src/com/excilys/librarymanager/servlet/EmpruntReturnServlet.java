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
import com.excilys.librarymanager.service.impl.EmpruntServiceImpl;
import com.excilys.librarymanager.tables.Emprunt;

public class EmpruntReturnServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		switch (action) {
			case "/emprunt_return":
				retEmp(request, response);
				break;
			default:
				System.out.println("Default redirecting case from " + action + " !");
		}
	}

	
	private void retEmp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		EmpruntService empService = EmpruntServiceImpl.getInstance();
		List<Emprunt>  emprunts = new ArrayList<Emprunt>();
		int id=-1;
		try {
			if(request.getParameter("id")!=null)
				id = Integer.parseInt(request.getParameter("id"));			//on va sauvegarder le id dans id
			emprunts=empService.getListCurrent();
		}
		catch(ServiceException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw new ServletException("Probleme dans le return d'un emprunt" , e);		//Servlet exception comme demandé
		}
		request.setAttribute("emprunts", emprunts);
		request.setAttribute("idRet", id);									//on a defini "idRet" pour n'avoir pas des problems dans le fichier .jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_return.jsp");
		dispatcher.forward(request, response);
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EmpruntService empService = EmpruntServiceImpl.getInstance();
		List<Emprunt>  emprunts = new ArrayList<Emprunt>();
		try {
			empService.returnBook(Integer.parseInt(request.getParameter("id")));			//return du livre
			emprunts = empService.getListCurrent();											//return du list current
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw new ServletException("Probleme dans le return d'un emprunt" , e);
	    }
		request.setAttribute("emprunts", emprunts);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_list.jsp");
		dispatcher.forward(request, response);
	}
}
