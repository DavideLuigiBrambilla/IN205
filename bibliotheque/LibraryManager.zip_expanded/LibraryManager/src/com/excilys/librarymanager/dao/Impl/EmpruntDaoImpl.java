package com.excilys.librarymanager.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.librarymanager.dao.EmpruntDao;
import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.persistence.ConnectionManager;
import com.excilys.librarymanager.tables.Abonnement;
import com.excilys.librarymanager.tables.Emprunt;
import com.excilys.librarymanager.tables.Livre;
import com.excilys.librarymanager.tables.Membre;

public class EmpruntDaoImpl implements EmpruntDao {

	//implementation "lazy instance"
	private static EmpruntDaoImpl instance;
	private EmpruntDaoImpl() { }
	
	public static EmpruntDao getInstance() {
		if(instance == null) {
			instance = new EmpruntDaoImpl();
		}
		return instance;
	}
	
	//requetes SQL demandées sur la consigne
	private static final String SELECT_ALL_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre ORDER BY dateRetour DESC;";
	private static final String SELECT_NOT_RETURNED_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL;";
	private static final String SELECT_NOT_RETURNED_MEM_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND membre.id = ?;";
	private static final String SELECT_NOT_RETURNED_LIV_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND livre.id = ?;";
	private static final String SELECT_ONE_QUERY = "SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE e.id = ?;";
	private static final String CREATE_QUERY = "INSERT INTO Emprunt (idMembre, idLivre, dateEmprunt, dateRetour) VALUES (?, ?, ?, ?);";
	private static final String UPDATE_QUERY = "UPDATE Emprunt SET idMembre=?, idLivre=?,dateEmprunt=?, dateRetour=? WHERE id=?;";
	private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM emprunt;";
	
	@Override
	public List<Emprunt> getList() throws DaoException {
		List<Emprunt> emprunts = new ArrayList<Emprunt>();
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();		//connection
			preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);	//chargement requete
			res = preparedStatement.executeQuery();				//obtention du resultat de la requete SQL
			while(res.next()) {									//on va ajouter chaque element retourné par la requete SQL à la list des emprunts
				Emprunt e = new Emprunt(res.getInt("id"),
										new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), Abonnement.valueOf(res.getString("abonnement"))),
										new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"), res.getString("isbn")), 
										res.getDate("dateEmprunt").toLocalDate(),
										null == res.getDate("dateRetour") ? null : res.getDate("dateRetour").toLocalDate());
				emprunts.add(e);
			}
			System.out.println("GET: " + emprunts);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des emprunts", e);
		} finally {										//fermeture des elements utilisés comme dans TD3
			try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return emprunts;
	}

	@Override
	public List<Emprunt> getListCurrent() throws DaoException {
		// TODO Auto-generated method stub
		List<Emprunt> emprunts = new ArrayList<Emprunt>();
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_NOT_RETURNED_QUERY);	
			res = preparedStatement.executeQuery();
			while(res.next()) {
				Emprunt e = new Emprunt(res.getInt("id"),
										new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), Abonnement.valueOf(res.getString("abonnement"))),
										new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"), res.getString("isbn")), 
										res.getDate("dateEmprunt").toLocalDate(),
										null == res.getDate("dateRetour") ? null : res.getDate("dateRetour").toLocalDate());
				emprunts.add(e);
			}
			System.out.println("GET: " + emprunts);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des emprunts non rendu", e);
		} finally {
			try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return emprunts;
	}

	@Override
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException {
		// TODO Auto-generated method stub
		List<Emprunt> emprunts = new ArrayList<Emprunt>();
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_NOT_RETURNED_MEM_QUERY);
			preparedStatement.setInt(1, idMembre);
			res = preparedStatement.executeQuery();
			while(res.next()) {
				Emprunt e = new Emprunt(res.getInt("id"),
									new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), Abonnement.valueOf(res.getString("abonnement"))),
									new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"), res.getString("isbn")), 
									res.getDate("dateEmprunt").toLocalDate(),
									null == res.getDate("dateRetour") ? null : res.getDate("dateRetour").toLocalDate());
			emprunts.add(e);
		}
			System.out.println("GET: " + emprunts);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des emprunts non rendu pour un membre donné", e);
		} finally {
			try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return emprunts;
	}

	@Override
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException {
		// TODO Auto-generated method stub
		List<Emprunt> emprunts = new ArrayList<Emprunt>();
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_NOT_RETURNED_LIV_QUERY);
			preparedStatement.setInt(1, idLivre);
			res = preparedStatement.executeQuery();
			while(res.next()) {
				Emprunt e = new Emprunt(res.getInt("id"),
									new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), Abonnement.valueOf(res.getString("abonnement"))),
									new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"), res.getString("isbn")), 
									res.getDate("dateEmprunt").toLocalDate(),
									null == res.getDate("dateRetour") ? null : res.getDate("dateRetour").toLocalDate());
			emprunts.add(e);
		}
			System.out.println("GET: " + emprunts);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des emprunts non rendu pour un membre donné", e);
		} finally {
			try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return emprunts;

	}

	@Override
	public Emprunt getById(int id) throws DaoException {
		// TODO Auto-generated method stub
		Emprunt emprunt = new Emprunt();
		Membre membre = new Membre();
		Livre livre = new Livre();
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_ONE_QUERY);
			preparedStatement.setInt(1, id);
			res = preparedStatement.executeQuery();
			if(res.next()) {
				//initilisation emprunt
				emprunt.setId(res.getInt("idEmprunt"));
				//initialisation membre
				membre.setId(res.getInt("idMembre"));
				membre.setNom(res.getString("nom"));
				membre.setPrenom(res.getString("prenom"));
				membre.setAddr(res.getString("adresse"));
				membre.setEmail(res.getString("email"));
				membre.setTel(res.getString("telephone"));
				membre.setAbonnement(Abonnement.valueOf(res.getString("abonnement")));
				//initialisation livre
				livre.setId(res.getInt("idLivre"));
				livre.setTitre(res.getString("titre"));
				livre.setAut(res.getString("auteur"));
				livre.setISBN(res.getString("isbn"));
				//initialisation emprunt(fin)
				emprunt.setMem(membre);
				emprunt.setLiv(livre);
				emprunt.setDateEmp(res.getDate("dateEmprunt").toLocalDate());
				emprunt.setDateRet(null == res.getDate("dateRetour") ? null : res.getDate("dateRetour").toLocalDate());
			}
			else {
	        	System.out.println("Cet id: " + id + " n'est pas associé a aucune emprunt");
	        }
			
			System.out.println("GET: " + emprunt);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération du emprunt avec id: " + id, e);
		} finally {
			try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return emprunt;
	}

	@Override
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException {
		// TODO Auto-generated method stub
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int id = -1;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, idMembre);
			preparedStatement.setInt(2, idLivre);
			preparedStatement.setDate(3, null == dateEmprunt ? null : java.sql.Date.valueOf(dateEmprunt));
			preparedStatement.setDate(4, null);
			preparedStatement.executeUpdate();
			res = preparedStatement.getGeneratedKeys();
			if(res.next()){
				id = res.getInt(1);
			}

			System.out.println("CREATE emprunt de id: " + id);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la création du emprunt avec id: " + id, e);
		} finally {
			try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Emprunt emprunt) throws DaoException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(UPDATE_QUERY);		//chargement eds champs a utiliser dans la requete SQL
			preparedStatement.setInt(1, emprunt.getMem().getId());
			preparedStatement.setInt(2, emprunt.getLiv().getId());
			preparedStatement.setDate(3, java.sql.Date.valueOf(emprunt.getDateEmpr()));
			preparedStatement.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
			preparedStatement.setInt(5, emprunt.getId());
			preparedStatement.executeUpdate();									//execution requete

			System.out.println("UPDATE: " + emprunt);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la mise à jour du Emprunt: " + emprunt, e);
		} finally {
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int count() throws DaoException {
		// TODO Auto-generated method stub
		int count = -1;
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(COUNT_QUERY);
			res = preparedStatement.executeQuery();
			while(res.next()) {
				count = res.getInt(1); 					//count est directement obtenu de la requete SQL
			}
			System.out.println("COUNT: " + count);
		} catch (SQLException e) {
			throw new DaoException("Problème dans le COUNT", e);
		} finally {
			try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return count;
	}

}
