package edu.austral.dissis.chess.piece.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.Position;

public class VerticalMovement implements PieceMovement {
  @Override
  public boolean isValidMove(Position oldPos, Position newPos, Board context) {
    return oldPos.getColumn() == newPos.getColumn()
        && oldPos.getRow() != newPos.getRow()
        && noPieceBetween(oldPos, newPos, context);
  }

  @Override
  public boolean noPieceBetween(Position oldPos, Position newPos, Board context) {
    int fromRow = Math.min(oldPos.getRow(), newPos.getRow());
    int toRow = Math.max(oldPos.getRow(), newPos.getRow());
    for (int i = fromRow + 1; i < toRow; i++) {
      Position currentTile = new Position(i, oldPos.getColumn());
      if (context.pieceAt(currentTile) != null
          && context.getCurrentTurn() != context.pieceAt(currentTile).getPieceColour()) {
        return false;
      }
    }
    Piece lastPiece = context.pieceAt(newPos);
    return lastPiece == null || lastPiece.getPieceColour() != context.pieceAt(oldPos).getPieceColour();
  }
}
