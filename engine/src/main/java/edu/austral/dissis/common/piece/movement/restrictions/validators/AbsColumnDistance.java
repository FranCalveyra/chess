package edu.austral.dissis.common.piece.movement.restrictions.validators;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;

public class AbsColumnDistance implements MovementRestrictionValidator {
  private final int columnDistance;

  public AbsColumnDistance(int columnDistance) {
    this.columnDistance = columnDistance;
  }

  @Override
  public boolean isValidMove(GameMove move, Board context) {
    BoardPosition oldPos = move.from();
    BoardPosition newPos = move.to();
    return Math.abs(newPos.getColumn() - oldPos.getColumn()) == columnDistance;
  }
}
