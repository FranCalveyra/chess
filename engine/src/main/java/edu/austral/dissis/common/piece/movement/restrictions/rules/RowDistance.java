package edu.austral.dissis.common.piece.movement.restrictions.rules;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;

public class RowDistance implements MovementRestriction {
  private final int rowDistance;

  public RowDistance(int rowDistance) {
    this.rowDistance = rowDistance;
  }

  @Override
  public boolean isValidRestriction(GameMove move, Board context) {
    BoardPosition oldPos = move.from();
    BoardPosition newPos = move.to();
    return newPos.getRow() - oldPos.getRow() == rowDistance;
  }
}
