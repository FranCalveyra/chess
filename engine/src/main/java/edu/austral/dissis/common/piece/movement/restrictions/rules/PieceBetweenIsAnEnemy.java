package edu.austral.dissis.common.piece.movement.restrictions.rules;

import static edu.austral.dissis.chess.utils.AuxStaticMethods.getPosBetween;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;

public class PieceBetweenIsAnEnemy implements MovementRestriction {
  @Override
  public boolean isValidRestriction(GameMove move, Board context) {
    BoardPosition between = getPosBetween(move);
    Piece pieceBetween = context.pieceAt(between);
    return pieceBetween != null && pieceBetween.getPieceColour()
        != context.pieceAt(move.from()).getPieceColour();
  }
}
