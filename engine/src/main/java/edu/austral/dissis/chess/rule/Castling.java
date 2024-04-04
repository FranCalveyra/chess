package edu.austral.dissis.chess.rule;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.rule.movement.PieceMovementRule;
import edu.austral.dissis.chess.utils.Position;

public class Castling implements PieceMovementRule {
  // Only valid whenever king and rooks haven't been moved

  @Override
  public boolean isValidMove(Position oldPos, Position newPos, Board context) {
    Piece firstPiece = context.getActivePiecesAndPositions().get(oldPos);
    Piece secondPiece = context.getActivePiecesAndPositions().get(newPos);
    boolean colorCheck = firstPiece.getPieceColour() == secondPiece.getPieceColour();
    boolean typeCheck =
        (firstPiece.getType() == PieceType.KING && secondPiece.getType() == PieceType.ROOK)
            || (secondPiece.getType() == PieceType.KING && firstPiece.getType() == PieceType.ROOK);
    boolean movementCheck =
        firstPiece.checkValidMove(oldPos, newPos) && secondPiece.checkValidMove(newPos, oldPos);
    return colorCheck && typeCheck && movementCheck;
  }
}
