package edu.austral.dissis.chess.piece.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.utils.Position;
import edu.austral.dissis.chess.validator.DefaultCheckValidator;

public class Castling implements PieceMovement {
  // Only valid whenever king and rooks haven't been moved yet.

  @Override
  public boolean isValidMove(Position oldPos, Position newPos, Board context) {
    return isCastlingPossible(oldPos, newPos, context);
  }

  private boolean isCastlingPossible(Position oldPos, Position newPos, Board context) {
    Piece firstPiece = context.getActivePiecesAndPositions().get(oldPos);
    Piece secondPiece = context.getActivePiecesAndPositions().get(newPos);
    if (firstPiece == null || secondPiece == null) {
      return false;
    }
    boolean colorCheck = firstPiece.getPieceColour() == secondPiece.getPieceColour();
    boolean typeCheck1 =
        (firstPiece.getType() == PieceType.KING && secondPiece.getType() == PieceType.ROOK);
    boolean typeCheck2 =
        (secondPiece.getType() == PieceType.KING && firstPiece.getType() == PieceType.ROOK);
    boolean typeCheck = typeCheck1 || typeCheck2;
    int columnDelta = Math.abs(newPos.getColumn() - oldPos.getColumn());
    boolean displacementCheck =
        oldPos.getRow() == newPos.getRow() && (columnDelta == 3 || columnDelta == 4);
    boolean movementCheck = firstPiece.hasNotMoved() && secondPiece.hasNotMoved();
    boolean checkValidation = validateIfCheck(oldPos, newPos, context);
    return colorCheck
        && typeCheck
        && movementCheck
        && isNotPieceBetween(oldPos, newPos, context)
        && displacementCheck
        && checkValidation;
  }

  private boolean validateIfCheck(Position fromPos, Position toPos, Board context) {
    DefaultCheckValidator validator = new DefaultCheckValidator();
    int from = Math.min(fromPos.getColumn(), toPos.getColumn());
    int to = Math.max(fromPos.getColumn(), toPos.getColumn());
    int row = fromPos.getRow();
    for (int j = from; j <= to; j++) {
      Position currentPos = new Position(row, j);
      boolean check =
          validator.kingInCheck(
              context, context.getCurrentTurn(), toPos, context.pieceAt(currentPos));
      if (check) {
        return true;
      }
    }
    return false;
  }
}
