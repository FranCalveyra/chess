package edu.austral.dissis.chess.rule;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.rule.movement.PieceMovementRule;
import edu.austral.dissis.chess.utils.Position;

import java.awt.*;

public class Castling implements PieceMovementRule {
  // Only valid whenever king and rooks haven't been moved yet.

  @Override
  public boolean isValidMove(Position oldPos, Position newPos, Board context) {
    Piece firstPiece = context.getActivePiecesAndPositions().get(oldPos);
    Piece secondPiece = context.getActivePiecesAndPositions().get(newPos);
    boolean colorCheck = firstPiece.getPieceColour() == secondPiece.getPieceColour();
    boolean typeCheck1 = (firstPiece.getType() == PieceType.KING && secondPiece.getType() == PieceType.ROOK);
    boolean typeCheck2 = (secondPiece.getType() == PieceType.KING && firstPiece.getType() == PieceType.ROOK);
    boolean typeCheck = typeCheck1 || typeCheck2;
    boolean movementCheck =
      firstPiece.checkValidMove(oldPos, newPos, context) && secondPiece.checkValidMove(newPos, oldPos, context);
    //TODO: check by colour, check if they haven't moved and the rest of castling rules. By now, it is ok.
    return colorCheck && typeCheck && movementCheck;
  }
}
