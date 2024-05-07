package edu.austral.dissis.common.turn;

import java.awt.Color;

public interface TurnSelector {
  Color getCurrentTurn();

  TurnSelector changeTurn();
}
