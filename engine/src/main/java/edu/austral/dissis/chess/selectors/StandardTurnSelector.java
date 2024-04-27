package edu.austral.dissis.chess.selectors;

import java.awt.Color;

public class StandardTurnSelector implements TurnSelector {

  @Override
  public Color selectTurn(int turnNumber) {
    return turnNumber % 2 == 0 ? Color.WHITE : Color.BLACK;
  }
}
