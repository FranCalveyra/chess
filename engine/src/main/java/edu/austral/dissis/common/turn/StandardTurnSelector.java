package edu.austral.dissis.common.turn;

import java.awt.Color;

public class StandardTurnSelector implements TurnSelector {
  // Mutable, it's turn manager
  private final Color currentTurn;
  private final int turnNumber;

  public StandardTurnSelector() {
    currentTurn = Color.WHITE;
    turnNumber = 0;
  }

  private StandardTurnSelector(Color currentTurn, int turnNumber) {
    this.currentTurn = currentTurn;
    this.turnNumber = turnNumber;
  }

  @Override
  public Color getCurrentTurn() {
    return currentTurn;
  }

  @Override
  public TurnSelector changeTurn() {
    int nextTurnNumber = turnNumber + 1;
    Color nextTurn = nextTurnNumber % 2 == 0 ? Color.WHITE : Color.BLACK;
    return new StandardTurnSelector(nextTurn, nextTurnNumber);
  }
}
