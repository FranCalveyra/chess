package edu.austral.dissis.common.piece.movement.restrictions.validators;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;

public class AbsRowDistance implements MovementRestrictionValidator {
  private final int rowDistance;

  public AbsRowDistance(int rowDistance) {
    this.rowDistance = rowDistance;
  }

  @Override
  public boolean isValidMove(GameMove move, Board context) {
    BoardPosition oldPos = move.from();
    BoardPosition newPos = move.to();
    return Math.abs(newPos.getRow() - oldPos.getRow()) == rowDistance;
  }
}
