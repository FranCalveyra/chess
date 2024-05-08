package edu.austral.dissis.checkers.piece.movement.type;

import static edu.austral.dissis.checkers.piece.movement.type.CheckersNormalMovement.getCheckersMovementValidator;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.piece.movement.type.PieceMovement;
import edu.austral.dissis.common.utils.move.GameMove;

public class CheckersMovement implements PieceMovement {
  @Override
  public boolean isValidMove(GameMove move, Board context) {
    Piece piece = context.pieceAt(move.from());
    return getCheckersMovementValidator(piece.getPieceColour(), piece.getType())
        .isValidMove(move, context);
  }
}
