package edu.austral.dissis.common.piece.movement.restrictions.rules;

import static edu.austral.dissis.chess.utils.AuxStaticMethods.getPosBetween;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;

public class PieceBetweenIsAnEnemy implements MovementRestriction {
  @Override
  public boolean isValidRestriction(GameMove move, Board context) {
    BoardPosition between = getPosBetween(move);
    return context.pieceAt(between).getPieceColour()
        != context.pieceAt(move.from()).getPieceColour();
  }
}
