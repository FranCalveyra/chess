package edu.austral.dissis.chess.rule.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.Position;

public interface PieceMovementRule {
  // If I make a PieceMovementRule extend GameRule, O(ruleCheck) can be enormous.
  // Deficient.

  boolean isValidMove(Position oldPos, Position newPos, Board context);

  default Piece getPiece(Position currentPosition, Board context) {
    return context.getActivePiecesAndPositions().get(currentPosition);
  }

  default boolean isPieceBetween(Position oldPos, Position newPos, Board context) {
    //TODO: check on negative displacements (oldX > newX || oldY > newY)
    int oldX = oldPos.getColumn();
    int oldY = oldPos.getRow();
    int newX = newPos.getColumn();
    int newY = newPos.getRow();
    int compareX = newX - oldX;
    int compareY = newY - oldY;
    int fromX = compareX>0 ? oldX : newX;
    int fromY = compareY>0 ? oldY : newY;
    int toX = compareX>0 ? newX : oldX;
    int toY = compareY>0 ? newY : oldY;
    return checkBetween(new Position(fromX,fromY), new Position(toX,toY), context);
  }
  private boolean checkBetween(Position oldPos, Position newPos, Board context) {
    Piece piece = getPiece(oldPos, context);
    for (int i = oldPos.getRow(); i <= newPos.getRow(); i++) {
      for (int j = oldPos.getColumn(); j <= newPos.getColumn(); j++) {
        Piece pieceAt = getPiece(new Position(i, j), context);
        if (pieceAt != null && pieceAt.getPieceColour() == piece.getPieceColour()) return false;
      }
    }
    return true;
  }

}
