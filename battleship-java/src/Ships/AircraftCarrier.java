package Ships;

import java.io.Serializable;
//classe fille AircraftCarrier avec deux constructeurs

public class AircraftCarrier extends AbstractShip{
	
	public AircraftCarrier(){					//premiere constructeur 
		//on utilise super pour faire reference a la methode mere
		super('A', "AircraftCarrier", 5, Orien.EAST);
	}
	
	public AircraftCarrier(Orien orientation){	//deuxieme constructeur en specifiant le valeur d'orientation
		super('A', "AircraftCarrier", 5, orientation);
	}
}