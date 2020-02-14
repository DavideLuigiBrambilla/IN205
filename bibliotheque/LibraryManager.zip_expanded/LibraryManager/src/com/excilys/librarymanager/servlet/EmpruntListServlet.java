package com.excilys.librarymanager.servlet;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.service.EmpruntService;
import com.excilys.librarymanager.service.impl.EmpruntServiceImpl;
import com.excilys.librarymanager.tables.Emprunt;

public class EmpruntListServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		switch (action) {
			case "/emprunt_list":
				listEmp(request, response);
				break;
			default:
				System.out.println("Default redirecting case from " + action + " !");
		}
	}

	private void listEmp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		EmpruntService empService = EmpruntServiceImpl.getInstance();
		List<Emprunt>  emprunts = new ArrayList<Emprunt>(); 
		String show = request.getParameter("show");			//on va charger le contenu de show dans une variable pour apres pouovoir le comparer
		try {
			emprunts = (("all".equals(show)) ? empService.getList() : empService.getListCurrent());		//difference entre la visualisation de tous les emprunts et seulement les currents
		}
		catch(ServiceException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute("emprunts", emprunts);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_list.jsp");
		dispatcher.forward(request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
