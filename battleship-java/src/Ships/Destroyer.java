package Ships;
//classe fille Destroyer avec deux constructeurs

public class Destroyer extends AbstractShip{
	
	public Destroyer(){					//premiere constructeur 
		//on utilise super pour faire reference a la methode mere
		super('D', "Destroyer", 2, Orien.EAST);
	}
	
	public Destroyer(Orien orientation){	//deuxieme constructeur en specifiant le valeur d'orientation
		super('D', "Destroyer", 2, orientation);
	}
}
