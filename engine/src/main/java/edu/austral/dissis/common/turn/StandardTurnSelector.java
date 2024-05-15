package edu.austral.dissis.common.turn;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.utils.move.GameMove;
import edu.austral.dissis.common.utils.result.playresult.PlayResult;
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
  public TurnSelector changeTurn(GameMove lastMove, Board afterMoveBoard, PlayResult result) {
    int nextTurnNumber = turnNumber + 1;
    Color nextTurn = nextTurnNumber % 2 == 0 ? Color.WHITE : Color.BLACK;
    return new StandardTurnSelector(nextTurn, nextTurnNumber);
  }
}
