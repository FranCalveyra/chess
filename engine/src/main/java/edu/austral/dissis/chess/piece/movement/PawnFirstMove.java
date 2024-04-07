package edu.austral.dissis.chess.piece.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.Position;

public class PawnFirstMove implements PieceMovement {
  @Override
  public boolean isValidMove(Position oldPos, Position newPos, Board context) {
    // Need pawn original position. Get if from default
    Piece piece = context.getActivePiecesAndPositions().get(oldPos);
    int oldX = oldPos.getColumn();
    int oldY = oldPos.getRow();
    int newX = newPos.getColumn();
    int newY = newPos.getRow();
    boolean horizontalMove = oldX == newX;
    boolean verticalMove = Math.abs(newY - oldY) == 2;
    return horizontalMove
        && verticalMove
        && !piece.hasMoved()
        && isNotPieceBetween(oldPos, newPos, context);
  }
}
