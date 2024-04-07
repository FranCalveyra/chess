package edu.austral.dissis.chess.turn;

import edu.austral.dissis.chess.engine.Board;
import java.awt.Color;

public class StandardTurnSelector implements TurnSelector {

    @Override
    public Color selectTurn(Board context, int turnNumber) {
        return turnNumber%2 == 0 ? Color.WHITE : Color.BLACK;
    }
}
