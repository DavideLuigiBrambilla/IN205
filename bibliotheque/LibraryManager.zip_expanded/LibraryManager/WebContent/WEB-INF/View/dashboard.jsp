<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Library Management</title>
  <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
  <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/css/materialize.min.css">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <style>
   <%@include file='/assets/css/custom.css' %>
  </style>
</head>

<body>
  <jsp:include page='menu.jsp'></jsp:include>
  <main>
    <section class="content">
      <div class="page-announce valign-wrapper">
        <a href="#" data-activates="slide-out" class="button-collapse valign hide-on-large-only"><i class="material-icons">menu</i></a>
        <h1 class="page-announce-text valign">Tableau de bord</h1>
      </div>
      <div class="row">
        <div class="col l4 s6">
          <div class="small-box bg-aqua">
            <div class="inner">
              <h3><%if (request.getAttribute("nombremem") != null) {
                out.println("<p>" + request.getAttribute("nombremem") + "</p>");}%></h3> <!-- TODO : afficher le nombre de membres � la place de 12 -->
              <p>Membres</p>
            </div>
            <div class="icon">
            <ion-icon name="people"></ion-icon>
            </div>
            <a href="membre_list" class="small-box-footer" class="animsition-link">Liste des membres <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <div class="col l4 s6">
          <div class="small-box bg-green">
            <div class="inner">
              <h3><%if (request.getAttribute("nombreliv") != null) {
                out.println("<p>" + request.getAttribute("nombreliv") + "</p>");}%></h3> <!-- TODO : afficher le nombre de livres � la place de 27 -->
              <p>Livres</p>
            </div>
            <div class="icon">
              <ion-icon name="book"></ion-icon>
            </div>
            <a href="livre_list" class="small-box-footer" class="animsition-link">Liste des livres <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <div class="col l4 s6">
          <div class="small-box bg-yellow">
            <div class="inner">
              <h3><%if (request.getAttribute("nombreemp") != null) {
                out.println("<p>" + request.getAttribute("nombreemp") + "</p>");}%></h3></h3> <!-- TODO : afficher le nombre d'emprunts � la place de 1515 -->
              <p>Emprunts</p>
            </div>
            <div class="icon">
              <ion-icon name="bookmarks"></ion-icon>
            </div>
            <a href="emprunt_list" class="small-box-footer" class="animsition-link">Liste des emprunts <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <div class="container">
	        <div class="col s12">
	          <h5>Emprunts en cours</h5>
	          <table class="striped">
                <thead>
                    <tr>
                        <th>Livre</th>
                        <th>Membre emprunteur</th>
                        <th>Date d'emprunt</th>
                        <th>Retour</th>
                    </tr>
                </thead>
                <tbody id="results">
                <!-- 
                    <tr>
                        <td>Titre du livre, <em>de Nom de l'auteur</em></td>
                        <td>Pr�nom et nom du membre emprunteur</td>
                        <td>Date de l'emprunt</td>
                        <td>
                            <a href="emprunt_return?id=idDeLEmprunt"><ion-icon class="table-item" name="log-in"></a>
                        </td>
                    </tr>
                    -->
                    <c:forEach items="${emprunts}" var = "emprunt">
				        <tr>
				            <td>${emprunt.getLiv().getTitre()}<em>${emprunt.getLiv().getAut()}</em></td>
				            <td>${emprunt.getMem().getNom()} ${emprunt.getMem().getPrenom()}</td>
				            <td>${emprunt.getDateEmpr()}</td>
				            <td>
                            	<a href="emprunt_return?id=${emprunt.getId()}"><ion-icon class="table-item" name="log-in"></a>
                       		</td>
				        </tr>
			    	</c:forEach>
                     <!-- TODO : parcourir la liste des emprunts en cours et les afficher selon la structure d'exemple ci-dessus -->
                </tbody>
            </table>
          </div>
        </div>
      </div>
    </section>
  </main>
  <jsp:include page='footer.jsp'></jsp:include>
</body>
</html>
