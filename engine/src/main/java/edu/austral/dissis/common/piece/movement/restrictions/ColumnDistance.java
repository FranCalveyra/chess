package edu.austral.dissis.common.piece.movement.restrictions;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;

public class ColumnDistance implements MovementRestriction {
  private final int columnDistance;

  public ColumnDistance(int columnDistance) {
    this.columnDistance = columnDistance;
  }

  @Override
  public boolean isValidRestriction(GameMove move, Board context) {
    BoardPosition oldPos = move.from();
    BoardPosition newPos = move.to();
    return newPos.getColumn() - oldPos.getColumn() == columnDistance;
  }
}
