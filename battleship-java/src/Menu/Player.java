package Menu;
import java.io.Serializable;
import java.util.List;

import Board.Board;
import Board.Hit;
import Ships.AbstractShip;
import Ships.AbstractShip.Orien;

public class Player implements Serializable {
    /* **
     * Attributs
     */
    protected Board board;
    protected Board opponentBoard;
    protected int destroyedCount;
    protected AbstractShip[] ships;
    protected boolean lose;

    /* **
     * Constructeur
     */
    public Player(Board board, Board opponentBoard, List<AbstractShip> ships) {
        this.board = board;
        this.ships = ships.toArray(new AbstractShip[0]);
        this.opponentBoard = opponentBoard;
    }

    /* **
     * Méthodes
     */

    /**
     * Read keyboard input to get ships coordinates. Place ships on given coordinates.
     */
    public void putShips() {
        boolean done = false;
        int i = 0;

        do {
            AbstractShip s = ships[i];
            String msg = String.format("placer %d : %s(%d)", i + 1, s.getnom(), s.gettaille());
            System.out.println(msg);
            InputHelper.ShipInput res = InputHelper.readShipInput();
            // set ship orientation
            Orien o = null;
            if(res.orientation.equals("n")){	//ici on est en train de verifier l'orientation defini dans InputHelper
            	o = Orien.NORTH;
            }
            if(res.orientation.equals("s")){	
            	o = Orien.SOUTH;
            }
            if(res.orientation.equals("e")){	
            	o = Orien.EAST;
            }
            if(res.orientation.equals("w")){	
            	o = Orien.WEST;
            }
            
            s.setorientation(o);
            // put ship at given position
            try{									//ici on est en train de placer les navires sur le board
            	board.putShip(s, res.x, res.y);
            }catch(IllegalArgumentException e){
            	System.err.println("Error! Impossible to place the ship here!");	//exception d'erreur
            	i--;																//je decrement i pour reinserer le navire
            }
            // when ship placement successful
            ++i;
            done = i == 5;

            board.print();
        } while (!done);
    }

   public Hit sendHit(int[] coords) {
        boolean done;
        Hit hit = null;

        do {
            System.out.println("Où frapper?");
            InputHelper.CoordInput hitInput = InputHelper.readCoordInput();
            // call sendHit on this.opponentBoard
            try{
            	hit = opponentBoard.sendHit(hitInput.x, hitInput.y);	//je place le frappe sur l'autre board
            	done = true;
            	if(opponentBoard.hasShip(hitInput.x, hitInput.y)){		//je verifie si il y a un navire
            		board.setHit(true, hitInput.x, hitInput.y);			//si oui je fais un set true  ----> X rouge
            	}
            	else{
            		board.setHit(false, hitInput.x, hitInput.y);		//si non je fais un set false ----> X predefini
            	}
            }
            catch(Exception e){
        	   done = false;
        	   System.out.println("Not valid coordinates!");
            }
            // : Game expects sendHit to return BOTH hit result & hit coords.
            // return hit is obvious. But how to return coords at the same time ?
            
            //Avant de retourner la fonction on peut modifier les valeurs passés en parametres pour pouvoir retourner les coords
            coords[0] = hitInput.x;
            coords[1] = hitInput.y;
        } while (!done);
       
        return hit;
    }

    public AbstractShip[] getShips() {
        return ships;
    }

    public void setShips(AbstractShip[] ships) {
        this.ships = ships;
    }

}