package edu.austral.dissis.chess.piece.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.Position;

public class HorizontalMovement implements PieceMovement {
  @Override
  public boolean isValidMove(Position oldPos, Position newPos, Board context) {
    boolean validMove =
        oldPos.getColumn() != newPos.getColumn() && oldPos.getRow() == newPos.getRow();
    if (!validMove) {
      return false;
    }
    return this.noPieceBetween(oldPos, newPos, context);
  }

  @Override
  public boolean noPieceBetween(Position oldPos, Position newPos, Board context) {
    int fromColumn = Math.min(oldPos.getColumn(), newPos.getColumn());
    int toColumn = Math.max(oldPos.getColumn(), newPos.getColumn());
    for (int j = fromColumn + 1; j < toColumn; j++) {
      Position currentTile = new Position(oldPos.getRow(), j);
      if (context.pieceAt(currentTile) != null) {
        return false;
      }
    }
    Piece lastPiece = context.pieceAt(newPos);
    return lastPiece == null
        || lastPiece.getPieceColour() != context.pieceAt(oldPos).getPieceColour();
  }
}
