package edu.austral.dissis.common.piece.movement.restrictions;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.utils.move.GameMove;

public class PieceHasNotMoved implements MovementRestriction {
  @Override
  public boolean isValidRestriction(GameMove move, Board context) {
    return context.pieceAt(move.from()).hasNotMoved();
  }
}
