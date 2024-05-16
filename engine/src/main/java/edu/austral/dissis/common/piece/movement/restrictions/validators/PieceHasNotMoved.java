package edu.austral.dissis.common.piece.movement.restrictions.validators;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.utils.move.GameMove;

public class PieceHasNotMoved implements MovementRestrictionValidator {
  @Override
  public boolean isValidMove(GameMove move, Board context) {
    return context.pieceAt(move.from()).hasNotMoved();
  }
}
