package edu.austral.dissis.checkers.turn;

import edu.austral.dissis.common.turn.TurnSelector;
import edu.austral.dissis.common.utils.result.playresult.PieceTaken;
import edu.austral.dissis.common.utils.result.playresult.PlayResult;
import java.awt.Color;

public class CheckersTurnSelector implements TurnSelector {
  private final Color currentTurn;

  public CheckersTurnSelector() {
    currentTurn = Color.BLACK;
  }

  private CheckersTurnSelector(Color currentTurn) {
    this.currentTurn = currentTurn;
  }

  @Override
  public Color getCurrentTurn() {
    return currentTurn;
  }

  @Override
  public TurnSelector changeTurn(PlayResult result) {
    return result.getClass() == PieceTaken.class
        ? this
        : new CheckersTurnSelector(currentTurn == Color.RED ? Color.BLACK : Color.RED);
  }
}
