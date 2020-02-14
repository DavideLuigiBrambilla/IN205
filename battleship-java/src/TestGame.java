import java.util.Arrays;
import java.util.List;

import Board.BattleShipsAI;
import Board.Board;
import Board.Hit;
import Ships.AbstractShip;
import Ships.AircraftCarrier;
import Ships.BattleShip;
import Ships.Destroyer;
import Ships.Submarine;


public class TestGame {
	
	private static void sleep(int ms){
		try{
			Thread.sleep(ms);	//the timeout is in milliseconds
		}catch (InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		
		Board board = new Board("Game");	//initialisation de board
		
		//board.print();
		
		AbstractShip[] ships = new AbstractShip[] { new Destroyer(), 	//liste de navires --> je ne peux pas utiliser une ArrayList parce que ma fonction putShips prend en argument un type ships[]
							   		 			    new Submarine(),
							   		 			    new Submarine(),
							   		 			    new BattleShip(),
							   		 			    new AircraftCarrier()
							   						};
	
		BattleShipsAI battle = new BattleShipsAI(board ,board);
		
		Hit res;
		int num = 0;		//pour compter le nombre des navires
		
		battle.putShips(ships);	//je vais placer les navires de façon automatique
		char[] lettres = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		board.print();
		
		while(num < 5){	//si on a encore des navires  
			sleep(1000);
			int coords[] = battle.pickRandomCoord();
			res = battle.sendHit(coords);
			if(res.getValue() > 0){		//on a que quand une navire est coulé le valeur de Hit est positif
				num++;					//on augmente le nombre des navires coulés
				System.out.println(" Le frappe " + lettres[coords[0]] + "-" + (coords[1]+1) + " a fait couler " + res.toString());
			}
			else{
				System.out.println(" Le frappe " + lettres[coords[0]] + "-" + (coords[1]+1) + " : " + res.toString());
			}
		board.print();
		}
	}
}
