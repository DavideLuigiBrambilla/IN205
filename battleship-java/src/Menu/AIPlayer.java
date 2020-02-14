package Menu;
import java.io.Serializable;
import java.util.List;

import Board.Board;
import Board.Hit;
import Ships.AbstractShip;

public class AIPlayer extends Player implements Serializable {
    /* **
     * Attribut
     */
    private BattleShipsAI ai;

    /* **
     * Constructeur
     */
    public AIPlayer(Board ownBoard, Board opponentBoard, List<AbstractShip> ships) {
        super(ownBoard, opponentBoard, ships);
        ai = new BattleShipsAI(ownBoard, opponentBoard);
    }

    // AIPlayer must not inherit "keyboard behavior" from player. Call ai instead.
    public void aiputShips(){
    	ai.putShips(ships);
    }
    
    public Hit aisendHit(int[] coords){
    	return ai.sendHit(coords);
    }
}
