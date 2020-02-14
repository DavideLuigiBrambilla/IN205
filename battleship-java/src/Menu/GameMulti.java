package Menu;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import Board.Board;
import Board.Hit;
import Ships.AbstractShip;
import Ships.AircraftCarrier;
import Ships.BattleShip;
import Ships.Destroyer;
import Ships.Submarine;

public class GameMulti {

    /* ***
     * Constante
     */
    public static final File SAVE_FILE = new File("savegame.dat");

    /* ***
     * Attributs
     */
    private Player player1;
    private Player player2;	//je defini le deux joueurs comme Player

    /* ***
     * Constructeurs
     */
    public GameMulti() {}
    
    private static void sleep(int ms) {				//fonction pour attendre quelaue second apres le frappe
    	try {
    	Thread.sleep(ms);
    	} catch (InterruptedException e) {
    	e.printStackTrace();
    	}
    	}
    
    public GameMulti init() {
      //  if (!loadSave()) {
        	
            // init attributes
        	Scanner keyboard = new Scanner(System.in);
            System.out.println("Player 1 insert your name:");
            String nom1 = keyboard.nextLine();			// use a scanner to read player name
            
            System.out.println("Player 2 insert your name:");
            String nom2 = keyboard.nextLine();			//player name 2
            
            //use a scanner to read the size
            System.out.println("Insert the size:");
            int size = keyboard.nextInt();				//use a scanner to read the size
   
            // init boards
            Board b1 = new Board(nom1, size);
            Board b2 = new Board(nom2, size);
            
            b1.print();
            b2.print();
            // init this.player1 & this.player2
            this.player1 = new Player(b1, b2, createDefaultShips());
            this.player2 = new Player(b2, b1, createDefaultShips());
            
            // place player ships
            player1.putShips();
            deplacement();								//je utilise deplacement pour ne faire montrer a l'autre ma grille
            player2.putShips();
            //b2.print();								J'avais mis ce print pour voir si le grille du Player fonctionne bien
            deplacement();
       // }
        return this;
    }

    /* ***
     * Méthodes
     */
    public void run() {
        int[] coords = new int[2];
        Board b1 = player1.board;
        Board b2 = player2.board;
        Hit hit;

        // main loop
        b1.print();											//on affiche le board de player 1(le notre)
        boolean done;
        do {
        	System.out.println("PLAYER 1");					//chaque fois un joueur doit choisir ou frapper il peut voir ses grilles
        	b1.print();
            hit = Hit.MISS; // player1 send a hit
            hit = player1.sendHit(coords);
            boolean strike = false;
            strike = (hit != Hit.MISS);						//strike va devenir true si j'ai frappé un navire ---> j'ai la possibilité de jouer une autre fois
            if(strike == false) strike = (hit == Hit.DEJA); //strike va devenir true si j'ai frappé une position deja frappé ---> j'ai la possibilité de jouer un autre fois
            // set this hit on his board (b1) ---> l'implementation de cette partir je l'ai deja implmenté dans la fonction sendHit de player 1 
            
            done = updateScore();							//je vois si est le moment d'arreter le jeu
            b1.print();										//J'affiche le board apres le frappe
            System.out.println(makeHitMessage(false /* outgoing hit */, coords, hit));
            sleep(2000);									//j'attend 2 seconds avant de faire changer le joueur
            //save();
            deplacement();									//je utilise deplacement pour ne faire montrer a l'autre ma grille
 
            
            if (!done && !strike) {
                do {
                	System.out.println("PLAYER 2");			//chaque fois un joueur doit choisir ou frapper il peut voir ses grilles
                	b2.print();
                    hit = Hit.MISS; 						//player2 send a hit.
                    hit = player2.sendHit(coords);	      	//Je utilise le methode de envoye de frappe d'un classe player(non plus automatique)
                    strike = (hit != Hit.MISS);						//strike va devenir true si j'ai frappé un navire ---> j'ai la possibilité de jouer une autre fois
                    if(strike == false) strike = (hit == Hit.DEJA); //strike va devenir true si j'ai frappé une position deja frappé ---> j'ai la possibilité de jouer un autre fois
                    done = updateScore();
                    b2.print();								//J'affiche le board apres le frappe 
                    System.out.println(makeHitMessage(true /* incoming hit */, coords, hit));
                    sleep(2000);							//j'attend 2 seconds avant de faire changer le joueur
                    deplacement();							//je utilise deplacement pour ne faire montrer a l'autre ma grille

                    //if (!done) {
                    //    save();
                    //}
                } while(strike && !done);
            }

        } while (!done);

        SAVE_FILE.delete();
        System.out.println(String.format("Player %d won", player1.lose ? 2 : 1));
        //sin.close();
    }


    /*private void save() {
        try {
            // TODO bonus 2 : uncomment
            //  if (!SAVE_FILE.exists()) {
            //      SAVE_FILE.getAbsoluteFile().getParentFile().mkdirs();
            //  }

            // TODO bonus 2 : serialize players

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean loadSave() {
        if (SAVE_FILE.exists()) {
            try {
                // TODO bonus 2 : deserialize players

                return true;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
*/
    
    public void deplacement(){										//fonction que sert a faire deplacer le grille pour ne pas faire le montrer a l'adversaire
    	for(int i=0; i<1000; i++) System.out.println();
    }
    
    private boolean updateScore() {
        for (Player player : new Player[]{player1, player2}) {
            int destroyed = 0;
            for (AbstractShip ship : player.getShips()) {
                if (ship.issunk()) {
                    destroyed++;
                }
            }

            player.destroyedCount = destroyed;
            player.lose = destroyed == player.getShips().length;
            if (player.lose) {
                return true;
            }
        }
        return false;
    }
    
    private String makeHitMessage(boolean incoming, int[] coords, Hit hit) {
        String msg;
        ColorUtil.Color color = ColorUtil.Color.RESET;
        switch (hit) {
            case MISS:
                msg = hit.toString();
                break;
            case STRIKE:
                msg = hit.toString();
                color = ColorUtil.Color.RED;
                break;
            default:
                msg = hit.toString() + " coulé";
                color = ColorUtil.Color.RED;
        }
        msg = String.format("%s Frappe en %c%d : %s", incoming ? "<=" : "=>",
                ((char) ('A' + coords[0])),
                (coords[1] + 1), msg);
        return ColorUtil.colorize(msg, color);
    }

    private static List<AbstractShip> createDefaultShips() {
        return Arrays.asList(new AbstractShip[]{new Destroyer(), new Submarine(), new Submarine(), new BattleShip(), new AircraftCarrier()});
    }

    public static void main(String args[]) {
        new GameMulti().init().run();
    }
}
