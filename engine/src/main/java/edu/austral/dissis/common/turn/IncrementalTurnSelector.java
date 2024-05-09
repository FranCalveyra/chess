package edu.austral.dissis.common.turn;

import edu.austral.dissis.common.utils.result.PlayResult;
import java.awt.Color;

public class IncrementalTurnSelector implements TurnSelector {
  private final Color currentTurn;
  private final int turnNumber;
  private final int currentCap;

  public IncrementalTurnSelector() {
    this(Color.WHITE, 1, 1);
  }

  private IncrementalTurnSelector(
      final Color currentTurn, final int turnNumber, final int currentCap) {
    this.currentTurn = currentTurn;
    this.turnNumber = turnNumber;
    this.currentCap = currentCap;
  }

  @Override
  public Color getCurrentTurn() {
    return currentTurn;
  }

  @Override
  public TurnSelector changeTurn(PlayResult result) {
    int nextTurn = turnNumber + 1;
    if (nextTurn > currentCap) {
      Color nextTurnColor = currentTurn == Color.WHITE ? Color.BLACK : Color.WHITE;
      return new IncrementalTurnSelector(nextTurnColor, 1, currentCap + 1);
    }
    return new IncrementalTurnSelector(currentTurn, nextTurn, currentCap);
  }
}
