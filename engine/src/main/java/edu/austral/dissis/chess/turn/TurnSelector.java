package edu.austral.dissis.chess.turn;

import java.awt.Color;

public interface TurnSelector {
  Color selectTurn(int turnNumber);
}
