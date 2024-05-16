package edu.austral.dissis.common.piece.movement.restrictions.validators;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.utils.move.GameMove;

public class IsAnEnemy implements MovementRestrictionValidator {
  @Override
  public boolean isValidMove(GameMove move, Board context) {
    Piece lastPiece = context.pieceAt(move.to());
    return lastPiece != null
        && lastPiece.getPieceColour() != context.pieceAt(move.from()).getPieceColour();
  }
}
