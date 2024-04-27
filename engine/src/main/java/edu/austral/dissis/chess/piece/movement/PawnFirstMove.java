package edu.austral.dissis.chess.piece.movement;

import static edu.austral.dissis.chess.utils.MoveType.VERTICAL;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.ChessPosition;
import edu.austral.dissis.chess.validators.PiecePathValidator;

public class PawnFirstMove implements PieceMovement {
  @Override
  public boolean isValidMove(ChessPosition oldPos, ChessPosition newPos, Board context) {
    // Need pawn original position. Get if from default
    Piece piece = context.getPiecesAndPositions().get(oldPos);
    int oldX = oldPos.getColumn();
    int oldY = oldPos.getRow();
    int newX = newPos.getColumn();
    int newY = newPos.getRow();
    boolean horizontalMove = oldX == newX;
    boolean verticalMove = Math.abs(newY - oldY) == 2;
    return horizontalMove
        && verticalMove
        && piece.hasNotMoved()
        && new PiecePathValidator().isNoPieceBetween(oldPos, newPos, context, VERTICAL);
  }
}
