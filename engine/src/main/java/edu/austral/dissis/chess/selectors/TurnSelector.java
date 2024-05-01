package edu.austral.dissis.chess.selectors;

import java.awt.Color;

public interface TurnSelector {
  Color getCurrentTurn();
  TurnSelector changeTurn();
}
