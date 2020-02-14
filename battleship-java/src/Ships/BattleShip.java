package Ships;
//classe fille BattleShip avec deux constructeurs

public class BattleShip extends AbstractShip{
	
	public BattleShip(){					//premiere constructeur 
		//on utilise super pour faire reference a la methode mere
		super('B', "BattleShip", 4, Orien.EAST);
	}
	
	public BattleShip(Orien orientation){	//deuxieme constructeur en specifiant le valeur d'orientation
		super('B', "BattleShip", 4, orientation);
	}
}