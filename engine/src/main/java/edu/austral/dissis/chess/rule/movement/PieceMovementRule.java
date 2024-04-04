package edu.austral.dissis.chess.rule.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.Position;

public interface PieceMovementRule {
  // If I make a PieceMovementRule extend GameRule, O(ruleCheck) can be enormous.
  // Deficient.

  boolean isValidMove(Position oldPos, Position newPos, Board context);
  default Piece getPiece(Position currentPosition, Board context){
    return context.getActivePiecesAndPositions().get(currentPosition);
  }
  default boolean isPieceBetween(Position oldPos, Position newPos, Board context){
    Piece piece = getPiece(oldPos, context);
    for (int i = oldPos.getColumn(); i <= newPos.getColumn(); i++){
      for (int j = oldPos.getRow(); j <= newPos.getRow() ; j++) {
        Piece pieceAt = getPiece(new Position(i,j), context);
        if(pieceAt != null && pieceAt.getPieceColour() == piece.getPieceColour()) return false;
      }
    }
    return true;
  }
}
