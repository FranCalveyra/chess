package edu.austral.dissis.chess.piece.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.Position;

public class DiagonalMovement implements PieceMovement {

  @Override
  public boolean isValidMove(Position oldPos, Position newPos, Board context) {
    int deltaX = Math.abs(newPos.getColumn() - oldPos.getColumn());
    int deltaY = Math.abs(newPos.getRow() - oldPos.getRow());
    boolean moveCondition = deltaX == deltaY;
    return isNotPieceBetween(oldPos, newPos, context) && moveCondition;
    // They have to move the same amount in both coordinates.
    // If not, movement should resemble to a Knight movement.
  }

  @Override
  public boolean isNotPieceBetween(Position oldPos, Position newPos, Board context) {
    int fromRow = Math.min(oldPos.getRow(), newPos.getRow());
    int fromColumn = Math.min(oldPos.getColumn(), newPos.getColumn());
    int toRow = Math.max(oldPos.getRow(), newPos.getRow());
    int toColumn = Math.max(oldPos.getColumn(), newPos.getColumn());
    return noTeammateInDiagonal(
        new Position(fromRow, fromColumn), new Position(toRow, toColumn), context);
  }

  private boolean noTeammateInDiagonal(Position oldPos, Position newPos, Board context) {
    for (int i = oldPos.getRow(); i < newPos.getRow(); i++) {
      Piece pieceAt = context.pieceAt(new Position(oldPos.getRow() + i, oldPos.getColumn() + i));
      if (pieceAt == null) {
        return true;
      }
    }
    return false;
  }
}
