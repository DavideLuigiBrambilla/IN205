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
import com.excilys.librarymanager.service.MembreService;
import com.excilys.librarymanager.service.impl.EmpruntServiceImpl;
import com.excilys.librarymanager.service.impl.LivreServiceImpl;
import com.excilys.librarymanager.service.impl.MembreServiceImpl;
import com.excilys.librarymanager.tables.Emprunt;

public class DashboardServlet extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		switch (action) {
			case "/dashboard":
			dashboard(request, response);				//seul cas appel a la fonction parce que je dois executer les taches au meme temps
			break;	
			default:
				System.out.println("Default redirecting case from " + action + " !");			
			}
	}

	private void dashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
		EmpruntService empService = EmpruntServiceImpl.getInstance();			//istances des trois elements
		MembreService memService = MembreServiceImpl.getInstance();
		LivreService livService = LivreServiceImpl.getInstance();
		List<Emprunt> emprunts = new ArrayList<>();
		int nombreEmp = -1;
		int nombreMem = -1;
		int nombreLiv = -1;
		try {
			emprunts = empService.getListCurrent();								//recuperations des quatres choses a afficher
			nombreEmp = empService.count();
			nombreLiv = livService.count();
			nombreMem = memService.count();
			
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute("emprunts", emprunts);
		request.setAttribute("nombreemp", nombreEmp);
		request.setAttribute("nombreliv", nombreLiv);
		request.setAttribute("nombremem", nombreMem);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/dashboard.jsp");
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		//on a du inserer doPost pour chaque servlet vu que on avait des problemes du a l'inexistence du ce dernier
		doGet(request, response);
	}
}
