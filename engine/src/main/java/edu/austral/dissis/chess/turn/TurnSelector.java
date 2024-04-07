package edu.austral.dissis.chess.turn;

import edu.austral.dissis.chess.engine.Board;
import java.awt.Color;

public interface TurnSelector {
  Color selectTurn(Board context, int turnNumber);
}
