import java.util.Arrays;
import java.util.List;

import Board.Board;
import Board.Hit;
import Menu.Player;
import Ships.AbstractShip.Orien;
import Ships.AbstractShip;
import Ships.AircraftCarrier;
import Ships.BattleShip;
import Ships.Destroyer;
import Ships.Submarine;

//dans mon programme les x me font deplacer entre les lettres et les y me font deplacer entre les nombres

public class TestBoard {						//fonction de test pour afficher les lignes avec un main
	public static void main(String[] args){
		//Board prova = new Board("Davide", 15);	//creation de une variable de type class Board et initialisation avec le deux constructeurs
		Board my = new Board("Davide");
		//test.print();
		Board opponent = new Board("Opponent");
		List<AbstractShip> ships = Arrays.asList(new Destroyer(), 	//on est en train de creer la liste de navires que on va inserer 
												 new Submarine(),
												 new Submarine(),
												 new BattleShip(),
												 new AircraftCarrier());
				
		my.putShip(new Submarine(Orien.SOUTH), (5)-1, (2)-1); //je vais faire une subtraction de 1 comme ca j'ai vraiment deplacé le navire en B,2
		opponent.putShip(new Submarine(Orien.SOUTH), (2)-1, (2)-1);
		//test.putShip(new AircraftCarrier(), (1)-1, (5)-1);
		//test.putShip(new Destroyer(Orien.EAST), (5)-1, (4)-1); //qui in realta la sto mettendo in E(4) - 4
		//test.putShip(new BattleShip(Orien.NORTH), (4)-1, (9)-1);
		
		Player player = new Player(my, opponent, ships);			//creation d'un classe de type Player
		//player.putShips();											//insertion des navires dans la grille de test
		//test.setHit(true, 5,5);						//ici je suis en train de la mettre en 6,6
		//test.setHit(false, 2, 7);					//ici je suis en train de la mettre en 3,8
		
		Hit testHit;
		
		testHit = opponent.sendHit((2)-1, (2)-1);	//ici on va mettre le send vers la grille opponent
		my.setHit(true,(2)-1, (2)-1);				//ici on va mettre le SetHit true parce que on sait deja que il y a un navire 
		my.print();
		opponent.print();
		System.out.println(testHit.toString());
		
		testHit = opponent.sendHit((3)-1, (5)-1);	
		my.setHit(false,(3)-1, (5)-1);
		my.print();
		opponent.print();
		System.out.println(testHit.toString());
		System.out.println("\n \n \n");
		
		testHit = opponent.sendHit((2)-1, (3)-1);
		my.setHit(true,(2)-1, (3)-1);
		my.print();
		opponent.print();
		System.out.println(testHit.toString());
		System.out.println("\n \n \n");
		
		testHit = opponent.sendHit((2)-1, (3)-1);
		my.setHit(true,(2)-1, (3)-1);
		my.print();
		opponent.print();
		System.out.println(testHit.toString());	//position deja frappé
		System.out.println("\n \n \n");
		
		testHit = opponent.sendHit((2)-1, (4)-1);
		my.setHit(true,(2)-1, (4)-1);			//on fait couler le navire comme ça on peut verifier que  le message de detruction a bien marché
		my.print();
		opponent.print();
		System.out.println(testHit.toString()); //nom du navire aue est coulé
		System.out.println("\n \n \n");
		
		System.out.println("HASSHIP" + opponent.hasShip(1, 3));			//voir ce que affiche hasShip ou il y a un navire coulé ---> true
		//reflexion 2 - on va laisser que hasShip retourne true aussi si le navire est deja coulé 
		
		//test.print();							//verification que la fonction ca marche bien
	}	
}
