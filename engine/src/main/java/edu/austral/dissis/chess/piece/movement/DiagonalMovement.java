package edu.austral.dissis.chess.piece.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.Pair;
import edu.austral.dissis.chess.utils.Position;

public class DiagonalMovement implements PieceMovement {

  @Override
  public boolean isValidMove(Position oldPos, Position newPos, Board context) {
    int deltaX = Math.abs(newPos.getColumn() - oldPos.getColumn());
    int deltaY = Math.abs(newPos.getRow() - oldPos.getRow());
    boolean moveCondition = deltaX == deltaY;
    if (context.pieceAt(oldPos) == null || !moveCondition) {
      return false;
    }
    return noPieceBetween(oldPos, newPos, context);
    // They have to move the same amount in both coordinates.
    // If not, movement should resemble to a Knight movement.
  }

  @Override
  public boolean noPieceBetween(Position oldPos, Position newPos, Board context) {
    return noTeammateInDiagonal(oldPos, newPos, context);
  }

  private boolean noTeammateInDiagonal(Position oldPos, Position newPos, Board context) {
    int deltaX = newPos.getColumn() - oldPos.getColumn();
    int deltaY = newPos.getRow() - oldPos.getRow();
    return checkDiagonal(oldPos, newPos, context, deltaX, deltaY);
  }

  private boolean checkDiagonal(
      Position oldPos, Position newPos, Board context, int deltaX, int deltaY) {
    if (deltaX > 0) {
      if (deltaY > 0) {
        return checkDiagonal(oldPos, newPos, context, new Pair<>(1, 1));
      } else {
        return checkDiagonal(oldPos, newPos, context, new Pair<>(-1, 1));
      }
    } else {
      if (deltaY > 0) {
        return checkDiagonal(oldPos, newPos, context, new Pair<>(1, -1));
      } else {
        return checkDiagonal(oldPos, newPos, context, new Pair<>(-1, -1));
      }
    }
  }

  private boolean checkDiagonal(
      Position oldPos, Position newPos, Board context, Pair<Integer> vector) {
    int deltaRow = vector.getFirst();
    int deltaColumn = vector.getSecond();
    for (int i = oldPos.getRow() + deltaRow, j = oldPos.getColumn() + deltaColumn;
        i != newPos.getRow() && j != newPos.getColumn();
        i += deltaRow, j += deltaColumn) {
      if (i < 0 || j < 0 || i >= context.getRows() || j >= context.getColumns()) {
        break;
      }
      Position currentPosition = new Position(i, j);
      Piece pieceAt = context.pieceAt(currentPosition);
      if (pieceAt != null) {
        return false;
      }
    }
    Piece lastPiece = context.pieceAt(newPos);
    return lastPiece == null
        || lastPiece.getPieceColour() != context.pieceAt(oldPos).getPieceColour();
  }
}
