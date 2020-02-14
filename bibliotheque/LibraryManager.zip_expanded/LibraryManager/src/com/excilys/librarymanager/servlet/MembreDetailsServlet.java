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
import com.excilys.librarymanager.service.MembreService;
import com.excilys.librarymanager.service.impl.EmpruntServiceImpl;
import com.excilys.librarymanager.service.impl.MembreServiceImpl;
import com.excilys.librarymanager.tables.Abonnement;
import com.excilys.librarymanager.tables.Emprunt;
import com.excilys.librarymanager.tables.Membre;

public class MembreDetailsServlet extends HttpServlet{

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		switch (action) {
			case "/membre_details":
				detailsMem(request, response);
				break;
			default:
				System.out.println("Default redirecting case from " + action + " !");
		}
	}

	private void detailsMem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		MembreService memService = MembreServiceImpl.getInstance();
		Membre membre = new Membre();
		EmpruntService empService = EmpruntServiceImpl.getInstance();
		List<Emprunt> emprunts = new ArrayList<Emprunt>();
		try {
			membre = memService.getById(Integer.parseInt(request.getParameter("id")));						//pour afficher les details du membre
			emprunts = empService.getListCurrentByMembre(Integer.parseInt(request.getParameter("id")));		//current list emprunt du membre
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw new ServletException("Probleme dans l'affichage des details d'un membre" , e);		//exception comme demandé
		}
		request.setAttribute("membre", membre);																//elements pour le traitement dans lefichier .jsp
		request.setAttribute("emprunts", emprunts);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_details.jsp");		
		dispatcher.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MembreService memService = MembreServiceImpl.getInstance();
		Membre membre = new Membre();
		EmpruntService empService = EmpruntServiceImpl.getInstance();
		List<Emprunt> emprunts = new ArrayList<Emprunt>();
		try {
			membre = memService.getById(Integer.parseInt(request.getParameter("id")));				//mis a jour du membre
			membre.setNom(request.getParameter("nom"));
			membre.setPrenom(request.getParameter("prenom"));
			membre.setAbonnement(Abonnement.valueOf(request.getParameter("abonnement")));
			membre.setAddr(request.getParameter("adresse"));
			membre.setEmail(request.getParameter("email"));
			membre.setTel(request.getParameter("telephone"));
			memService.update(membre);
			emprunts = empService.getListCurrentByLivre(Integer.parseInt(request.getParameter("id")));		//visualisation des emprunts liés au membre
			
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw new ServletException("Probleme dans l'affichage des details d'un membre" , e);		//exception comme demandé
		}
		request.setAttribute("membre", membre);
		request.setAttribute("emprunts", emprunts);														//elements pour le fichier .jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_details.jsp");
		dispatcher.forward(request, response);
	}
}
