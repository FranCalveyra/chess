package edu.austral.dissis.chess.piece.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.Position;
import java.util.ArrayList;
import java.util.List;

public interface PieceMovement {

  boolean isValidMove(Position oldPos, Position newPos, Board context);

  default boolean noPieceBetween(Position oldPos, Position newPos, Board context) {
    int fromColumn = Math.min(oldPos.getColumn(), newPos.getColumn());
    int fromRow = Math.min(oldPos.getRow(), newPos.getRow());
    int toColumn = Math.max(oldPos.getColumn(), newPos.getColumn());
    int toRow = Math.max(oldPos.getRow(), newPos.getRow());
    return !isTeammateBetween(
        new Position(fromRow, fromColumn), new Position(toRow, toColumn), context);
  }

  default List<Position> getPossibleMoves(Position oldPos, Board context) {
    List<Position> possiblePositions = new ArrayList<>();
    for (int i = 0; i < context.getRows(); i++) {
      for (int j = 0; j < context.getColumns(); j++) {
        Position currentPos = new Position(i, j);
        if (isValidMove(oldPos, currentPos, context)
            && !possiblePositions.contains(currentPos)
            && context.pieceAt(currentPos) == null) {
          possiblePositions.add(currentPos);
        }
      }
    }
    return possiblePositions;
  }

  // Private methods
  private boolean isTeammateBetween(Position fromPos, Position toPos, Board context) {
    // Change for loops depending on displacement type
    for (int i = fromPos.getRow(); i <= toPos.getRow(); i++) {
      for (int j = fromPos.getColumn(); j <= toPos.getColumn(); j++) {
        Piece pieceAt = context.pieceAt(new Position(i, j));
        if (pieceAt != null
            && pieceAt.getPieceColour() != context.pieceAt(fromPos).getPieceColour()) {
          return true;
        }
      }
    }
    return false;
  }
}
