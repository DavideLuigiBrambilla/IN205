package Board;
import java.io.Serializable;

import Ships.AbstractShip;


public class ShipState implements Serializable{
	
	private AbstractShip s;		//attribute de type AbstractShip
	private boolean struck;				//attribute pour verifier aue le ship a ete touché
	
	public ShipState(AbstractShip ship){	//definition de un constructeur pour la classe que initialise struck a false
		this.s = ship;
		this.struck = false;
	}
	
	public void addStrike(){				//implementation du methode pour marquer la classe comme touché
		if(this.s.getstrikeCount() >= this.s.gettaille()){	//verification que le nombre des hits n'est deja egal a la taille
			throw new IllegalArgumentException("The number of hits is already maximum");
		}
		this.struck = true;
		this.s.addstrike();
	}
	
	public boolean isStruck(){				//implementation du methode pour retourner la valeur de struck
		return this.struck;
	}
	
	public String toString(){				//implementation du methode pour retourner le label du navire(en rouge si le navire est touché)
		if(this.struck == true){
			return ColorUtil.colorize(this.s.getlabel(), ColorUtil.Color.RED);	//je utilise la fonction colorize et pour faire ça je dois changer le type de label de char en Character: comme ça si struck est vrai je peux retourner le string en rouge
		}
		else {
			return ColorUtil.colorize(this.s.getlabel(), ColorUtil.Color.RESET);	//si struck est false on ecrit de façon standard
		}
		
	}
	
	public boolean isSunk(){				//implementation du methode pour voir si le navire est detruit
		return this.s.issunk();
	}
	
	public AbstractShip getShip(){			//implementation du methode pour retourner le navire
		return this.s;
	}
}
