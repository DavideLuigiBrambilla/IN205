package Board;
import java.io.Serializable;

import Ships.AbstractShip;
import Ships.AbstractShip.Orien;

public class Board implements IBoard,Serializable{
	
		private int size;				//je vais mettre private comme ca les champs sont visibles a l'interieur de la classe Board
		private String nom;
		private ShipState[][] navires; 	//changement a ShipState
		private Boolean[][] frappes;	//changement de boolean a Boolean de façon d'avoir aussi le valeur null
	

public Board(String nom, int size){ //Constructeur avec en arguments le nom et la taille de la grille
	this.size = size;
	this.nom = nom;
	this.navires = new ShipState[size][size];
	this.frappes = new Boolean[size][size];
}

public Board(String nom){ //ici j'ai deux signatures differentes de deux constructeurs du coup je peux les appeller avec le meme nom
	this.nom = nom;		  //c'est le deuxieme type de constructeur 
	this.size = 10;
	this.navires = new ShipState[10][10];
	this.frappes = new Boolean[10][10];
}

public void print(){	  //fonction pour afficher les deux grilles 
	
	char letter = 'A';
	char point = '.'; 
	int num = 1;
	
    System.out.println(this.nom);
    
    System.out.print("Navires:\n");
    
	for (int i = 0; i < this.size; i++){	//boucle pour afficher les lettres de la premiere grille
		System.out.print("\t" + letter);    //J'ai utilisé \t parce que comme ca j'ai un bonne identation
		letter++;
		}
	
	
	for (int i = 0; i < this.size; i++){ 	//boucle pour afficher les differentes lignes de la premiere grille
		System.out.print("\n");
		System.out.print(num);
		num++;
		for (int k = 0; k < this.size; k++){
			if(this.navires[k][i] == null){
				System.out.print("\t" + point);
			}
			else{
				System.out.print("\t" + this.navires[k][i].toString()); //ici on va retourner le label du navire (en rouge si il est touché)
			}
		}
	}
	
	System.out.println();
	System.out.println();
	System.out.println();
	System.out.println();
	
	System.out.print("Frappes: \n");
	
	letter = 'A';
	num = 1;
	
	for (int i = 0; i < this.size; i++){	//boucle pour afficher les lettres de la deuxieme grille
		System.out.print("\t" + letter); 	
		letter++;
		}
	
	
	for (int i = 0; i < this.size; i++){	//boucle pour afficher les differentes lignes de la premiere grille
		System.out.print("\n");
		System.out.print(num);
		num++;
		for (int k = 0; k < this.size; k++){
			if(this.frappes[k][i] == null){
				System.out.print(ColorUtil.colorize("\t" + point, ColorUtil.Color.BLUE));	//ja vais mettre le point en blue si hit est null
			}
			else if(this.frappes[k][i] == true){
				System.out.print(ColorUtil.colorize("\t" + 'X', ColorUtil.Color.RED));	//Je vais mettre la X en rouge si hit est vrai: il y a un navire
			}
			else if(this.frappes[k][i] == false){
				System.out.print(ColorUtil.colorize("\t" + 'X', ColorUtil.Color.WHITE)); //je vais mettre la X en blanc si hit est faux: il n'y a pas un navire
			}
		}
	}
	
	System.out.print("\n \n \n \n");
}

@Override
public int getSize() {
	// Auto-generated method stub
	return this.size;
}

@Override
public void putShip(AbstractShip ship, int x, int y) {	
	// Auto-generated method stub
	Orien o = ship.getorientation();
	int dirx = 0;
	int diry = 0;
	if(o == Orien.NORTH){								//avant:on va mettre cette condition parce que pour le programme la position 0 est la position 1 pour l'utilisateur
		if(y + 1 - ship.gettaille() < 0){			
			throw new IllegalArgumentException("Not possible! Ship outside of the grid!");
		}
		diry=-1;	//il va en bas diry=-1 et dirx = 0
	}
	else if(o == Orien.SOUTH){
		if(y + ship.gettaille() > this.size){		//on va retirer la condition de egalité parce que InputHelper gere bien les coordonnees A est 0 et 1 est la position 0
			throw new IllegalArgumentException("Not possible! Ship outside of the grid!");
		}
		diry=1;		//il va en haut diry=1 et dirx = 0
	}
	else if(o == Orien.EAST){
		if(x + ship.gettaille() > this.size){
			throw new IllegalArgumentException("Not possible! Ship outside of the grid!");
		}
		dirx=1;		//il va vers droite diry=0 et dirx = 1  
	}
	else if(o == Orien.WEST){
		if(x + 1 - ship.gettaille() < 0){
			throw new IllegalArgumentException("Not possible! Ship outside of the grid!");
		}
		dirx=-1; 	//il va vers droite diry=0 et dirx = -1
	}
	
	int curx = x;
	int cury = y;
	
	for(int i=0; i < ship.gettaille(); i++){				//ici on met un boucle pour verifier que les navires ne se chevauchent pas
		if(hasShip(curx, cury)){							//verification que il y a un ship dans la position donnée
			throw new IllegalArgumentException("Not possible! Ships one over an other!");	
		}
		curx = curx + dirx;									//il faut incrementer ici aussi les curseurs
		cury = cury + diry;
	}
	
	curx = x;
	cury = y;
	
	for(int i=0; i < ship.gettaille(); i++){				//ici on met le navire sur board
		this.navires[curx][cury] = new ShipState(ship);
		curx = curx + dirx;
		cury = cury + diry;
	}
}

@Override
public boolean hasShip(int x, int y) {
	// Auto-generated method stub
	if(x > this.size || y > this.size){
		throw new IllegalArgumentException("Not possible! Outside of the grid"); //on est en train de creer un exception en disant que il se trouve a l'exterieur du notre grid
	}
	boolean has=false;
	if(this.navires[x][y] != null){
		has = true;					//REFLEXION 2 : pour le moment je vais laisser aue mon methode HasShip ne fait pas la difference si le navire a ete coule ou pas
	}
		return has; 
}

@Override
public void setHit(boolean hit, int x, int y) {
	// Auto-generated method stub
	if(x > this.size || y > this.size){
		throw new IllegalArgumentException("Not possible! Outside of the grid"); //on est en train de creer un exception en disant que il se trouve a l'exterieur du notre grid
	}
	this.frappes[x][y] = hit;
	
}

@Override
public Boolean getHit(int x, int y) {
	// Auto-generated method stub
	if(x > this.size || y > this.size){
		throw new IllegalArgumentException("Not possible! Outside of the grid"); //on est en train de creer un exception en disant que il se trouve a l'exterieur du notre grid
	}
	return this.frappes[x][y];
}

@Override
public Hit sendHit(int x, int y) {		//methode pour donner des frappes dans l'opponent
	
	if(navires[x][y] == null){		//tableau navires de l'avversaire
		return Hit.MISS;			//returne de valeur MISS si il n'y a pas de navires 
	}
	else{
		if(navires[x][y].isStruck() == true){		//REFLEXION 1 - **deuxieme version** - en utilisant le methode isStruck on a pas besoin de donner ma grille parce que grace a struck on peut gerer ce methode
			return Hit.DEJA;						//on va retourner le valeur "Position-deja-frappé"
		}
		else{
			navires[x][y].addStrike();				// va ajouter le strike ou il ny a pas deja
			if(navires[x][y].isSunk() == true){		//si le navire est coulé
				System.out.println(Hit.fromInt(navires[x][y].getShip().gettaille()) + " coulé"); //ici je affiche que le navire est coulé; 
				return Hit.fromInt(navires[x][y].getShip().gettaille()); 						 //avec fromInt on peut obtenir le navire a partir de la taille
			}
		return Hit.STRIKE;
		}
	}
}

/* REFLEXION 1 - **premiere version**
 * Pour implementer le cas ou sendHit peut gere l'erreur d'envoyer deux fois sur la meme position je dois passer en parametre mon tableau aussi
 * 
public Hit sendHit(board my, int x, int y) {

	if(my.frappes[x][y] != null){			//if pour faire un controle que la position ne soit pas deja touchée: si il est touché je vais envoyer un message en disant l'erreur et je vais envoyer un nouveau type de Hit
		System.out.println("Position already hit! Essayer une autre");
		return Hit.DEJA;			//on va reenvoyer un type de hit pour communiquer que cette position a ete deja touché
	
	}
	
	if(navires[x][y] == null){		//tableau navires de l'avversaire
		
		return Hit.MISS;
	}
	else{
		
		navires[x][y].addStrike();
		
		if(navires[x][y].isSunk() == true){
			
			System.out.println(Hit.fromInt(navires[x][y].getShip().gettaille()) + " coulé"); //ici je affiche que le navire est coulé; 
			
			return Hit.fromInt(navires[x][y].getShip().gettaille()); //avec fromInt on peut obtenir le navire a partir de la taille
		}
		
		return Hit.STRIKE;
	}
}
*/
}