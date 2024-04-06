package edu.austral.dissis.chess.rule.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.Position;

public interface PieceMovementRule {
  // If I make a PieceMovementRule extend GameRule, O(ruleCheck) can be enormous.
  // Deficient.

  boolean isValidMove(Position oldPos, Position newPos, Board context);

  private Piece getPiece(Position currentPosition, Board context) {
    return context.getActivePiecesAndPositions().get(currentPosition);
  }

  default boolean isPieceBetween(Position oldPos, Position newPos, Board context) {
    //TODO: check on displacement type
    int oldColumn = oldPos.getColumn();
    int oldRow = oldPos.getRow();
    int newColumn = newPos.getColumn();
    int newRow = newPos.getRow();
    int compareColumn = newColumn - oldColumn;
    int compareRow = newRow - oldRow;
    int fromColumn = compareColumn > 0 ? oldColumn : newColumn;
    int fromRow = compareRow > 0 ? oldRow : newRow;
    int toColumn = compareColumn > 0 ? newColumn : oldColumn;
    int toRow = compareRow > 0 ? newRow : oldRow;
    return isTeammateBetween(new Position(fromRow, fromColumn), new Position(toRow, toColumn), context, compareRow, compareColumn);
  }

  private boolean isTeammateBetween(Position oldPos, Position newPos, Board context, int deltaRow, int deltaColumn) {
  //Change for loops depending on displacement type
    for (int i = oldPos.getRow(); i < newPos.getRow(); i++) {
      for (int j = oldPos.getColumn(); j < newPos.getColumn(); j++) {
        Piece pieceAt = getPiece(new Position(i, j), context);
        if (pieceAt != null) return true;
      }
    }
    return false;
  }
}