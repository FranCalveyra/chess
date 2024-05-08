package edu.austral.dissis.common.piece.movement.restrictions.rules;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.utils.move.GameMove;

public class IsAnEnemy implements MovementRestriction {
  @Override
  public boolean isValidRestriction(GameMove move, Board context) {
    Piece lastPiece = context.pieceAt(move.to());
    return lastPiece != null
        && lastPiece.getPieceColour() != context.pieceAt(move.from()).getPieceColour();
  }
}
