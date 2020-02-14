package Ships;							//Pour remedier au probleme de nombre des fichiers toujours augmentant j'ai decide de creer un nouveau package pour mettre tous les class d'un meme type dans un meme package 

import java.io.Serializable;



public abstract class AbstractShip implements Serializable{
	
	public enum Orien{					//Je vais definir a l'interieur de la classe mere les quatres types de orientation 
		NORTH, SOUTH, EAST, WEST;
		}
	
	private Character label; 				//on defini les elements de la classe mere label,nom,taille et orientation
	private String nom;
	private int taille;
	private Orien orientation;
	private int strikeCount;			//on va definir l'attribute strikeCount
	
	public AbstractShip(char label, String nom, int taille, Orien orientation){	//Constructeur pour AbstractShip avec les differentes parametres de initialisation
		this.label = label;
		this.nom = nom;
		this.taille = taille;
		this.orientation = orientation; 
	}
	
	public Character getlabel(){ //ici j'ai implementé les methodes pour acceder a partir de l'exterieur a les elements private
		return this.label;
	}
	
	public String getnom(){
		return this.nom;
	}
	
	public int gettaille(){
		return this.taille;
	}
	
	public Orien getorientation(){
		return this.orientation;
	}
	
	public int getstrikeCount(){
		return this.strikeCount;
	}

	public void setorientation(Orien o) {		//implementation du methode setorientation pour definir l'orientation du navire apres aue player a inseré le direction
		// Auto-generated method stub
		this.orientation = o;
		
	}
	
	public void addstrike(){					//implementation du methode pour manipuler les nombre de frappes
		if(this.strikeCount >= this.taille){
			throw new IllegalArgumentException("The number of Hit is already maximum");
		}
		this.strikeCount++;
	}
	
	public boolean issunk(){					//implemenation du methode pour verifier que le navire est detruit 
		if(this.taille == this.strikeCount)		
			return true;
		else
			return false;
	}
}
