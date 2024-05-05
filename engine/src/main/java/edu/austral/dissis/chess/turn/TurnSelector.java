package edu.austral.dissis.chess.turn;

import java.awt.Color;

public interface TurnSelector {
  Color getCurrentTurn();

  TurnSelector changeTurn();
}
