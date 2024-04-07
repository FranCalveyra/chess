package edu.austral.dissis.chess.piece.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.Position;
import java.util.ArrayList;
import java.util.List;

public interface PieceMovement {
  // If I make a PieceMovementRule extend GameRule, O(ruleCheck) can be enormous.
  // Deficient.

  boolean isValidMove(Position oldPos, Position newPos, Board context);

  default boolean isNotPieceBetween(Position oldPos, Position newPos, Board context) {
    int fromColumn = Math.min(oldPos.getColumn(), newPos.getColumn());
    int fromRow = Math.min(oldPos.getRow(), newPos.getRow());
    int toColumn = Math.max(oldPos.getColumn(), newPos.getColumn());
    int toRow = Math.max(oldPos.getRow(), newPos.getRow());
    return !isTeammateBetween(
        new Position(fromRow, fromColumn), new Position(toRow, toColumn), context);
  }

  default List<Position> getPossibleMoves(Position oldPos, Board context) {
    List<Position> possiblePositions = new ArrayList<>();
    int oldColumn = oldPos.getColumn();
    int oldRow = oldPos.getRow();
    // TODO
    return possiblePositions;
  }

  // Private methods
  private boolean isTeammateBetween(Position oldPos, Position newPos, Board context) {
    // Change for loops depending on displacement type
    for (int i = oldPos.getRow(); i < newPos.getRow(); i++) {
      for (int j = oldPos.getColumn(); j < newPos.getColumn(); j++) {
        Piece pieceAt = context.pieceAt(new Position(i, j));
        if (pieceAt != null) {
          return true;
        }
      }
    }
    return false;
  }
}
