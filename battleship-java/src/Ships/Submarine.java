package Ships;
//classe fille Submarine avec deux constructeurs

public class Submarine extends AbstractShip{
	
	public Submarine(){					//premiere constructeur 
		//on utilise super pour faire reference a la methode mere
		super('S', "Submarine", 3, Orien.EAST);
	}
	
	public Submarine(Orien orientation){	//deuxieme constructeur en specifiant le valeur d'orientation
		super('S', "Submarine", 3, orientation);
	}
}
