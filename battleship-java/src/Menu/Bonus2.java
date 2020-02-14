
/* Je suis en train d'implementer le Bonus2 avec le sauvegarde d'un jeu: j'ai pris comme base le fichier Game.java
 * */
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

public class Bonus2 {

    /* ***
     * Constante
     */
    public static final File SAVE_FILE = new File("savegame.dat");

    /* ***
     * Attributs
     */
    private Player player1;
    private AIPlayer player2;	//Je defini player 2 comme un player automatique

    /* ***
     * Constructeurs
     */
    public Bonus2() {}

    public Bonus2 init() {
        if (!loadSave()) {
        	
            // init attributes
        	Scanner keyboard = new Scanner(System.in);
            System.out.println("Insert your name:");
            String nom = keyboard.nextLine();			// use a scanner to read player name
            
            //use a scanner to read the size
            System.out.println("Insert the size:");
            int size = keyboard.nextInt();				//use a scanner to read the size
   
            // init boards
            Board b1 = new Board(nom, size);
            Board b2 = new Board("Player", size);
            
            b1.print();
            // init this.player1 & this.player2
            this.player1 = new Player(b1, b2, createDefaultShips());
            this.player2 = new AIPlayer(b2, b1, createDefaultShips());
            
            // place player ships
            player1.putShips();
            player2.aiputShips();
            //b2.print();								J'avais mis ce print pour voir si le grille du Player fonctionne bien
        }
        return this;
    }

    /* ***
     * Méthodes
     */
    public void run() {
        int[] coords = new int[2];
        Board b1 = player1.board;
        Hit hit;

        // main loop
        b1.print();											//on affiche le board de player 1(le notre)
        boolean done;
        do {
            hit = Hit.MISS; // player1 send a hit
            hit = player1.sendHit(coords);
            boolean strike = false;
            strike = (hit != Hit.MISS);						//strike va devenir true si j'ai frappé un navire ---> j'ai la possibilité de jouer une autre fois
            if(strike == false) strike = (hit == Hit.DEJA); //strike va devenir true si j'ai frappé une position deja frappé ---> j'ai la possibilité de jouer un autre fois
            // set this hit on his board (b1) ---> l'implementation de cette partir je l'ai deja implmenté dans la fonction sendHit de player 1 
            
            done = updateScore();							//je vois si est le moment d'arreter le jeu
            b1.print();
            System.out.println(makeHitMessage(false /* outgoing hit */, coords, hit));

            save();
            
            if (!done && !strike) {
                do {
                    hit = Hit.MISS; 						// player2 send a hit.
                    hit = player2.aisendHit(coords);		//Je utilise le methode de envoye de frappe automatiques
                    strike = hit != Hit.MISS;
                    if (strike) {
                    	b1.print();
                    }
                    System.out.println(makeHitMessage(true /* incoming hit */, coords, hit));
                    done = updateScore();

                    if (!done) {
                        save();
                    }
                } while(strike && !done);
            }

        } while (!done);

        SAVE_FILE.delete();
        System.out.println(String.format("joueur %d gagne", player1.lose ? 2 : 1));
        //sin.close();
    }


    private void save() {
        try {
            // bonus 2 : uncomment
              if (!SAVE_FILE.exists()) {
                  SAVE_FILE.getAbsoluteFile().getParentFile().mkdirs();
              							}
            /*Pour faire cette partie de serialisation j'ai recherché sur internet comment
             *  serializer un Object: en utilisant le type ObjectOutputStream et en utilisant 
             *  la fonction writeObject.
             * */  
            // bonus 2 : serialize players
              FileOutputStream file = new FileOutputStream(SAVE_FILE);
              
              ObjectOutputStream obj = new ObjectOutputStream(file);
              obj.writeObject(player1);
              obj.writeObject(player2);
              obj.close();
              file.close();
              System.out.println("Serialized data saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean loadSave() {
        if (SAVE_FILE.exists()) {
            try {
                // bonus 2 : deserialize players
            	//Pour faire cette partie de deserialisation j'ai utilisé le type ObjectInputStream et en utilisant la fonction readObject.
                 
            	ObjectInputStream obji = new ObjectInputStream(new FileInputStream(SAVE_FILE));
            	player1 = (Player) obji.readObject();
            	player2 = (AIPlayer) obji.readObject();
            	obji.close();
                return true;
                
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
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
        new Bonus2().init().run();
    }
}
