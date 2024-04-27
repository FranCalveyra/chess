package edu.austral.dissis.chess.piece.movement;

import static edu.austral.dissis.chess.utils.MoveType.HORIZONTAL;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.rules.DefaultCheck;
import edu.austral.dissis.chess.utils.ChessPosition;
import edu.austral.dissis.chess.validator.PiecePathValidator;

public class Castling implements PieceMovement {
  // Only valid whenever king and rooks haven't been moved yet.

  @Override
  public boolean isValidMove(ChessPosition oldPos, ChessPosition newPos, Board context) {
    return isCastlingPossible(oldPos, newPos, context);
  }

  private boolean isCastlingPossible(ChessPosition oldPos, ChessPosition newPos, Board context) {
    Piece firstPiece = context.pieceAt(oldPos);
    Piece secondPiece = context.pieceAt(newPos);
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
    boolean generalChecks = colorCheck && typeCheck && movementCheck && displacementCheck;

    if (!generalChecks) {
      return false;
    }
    boolean isInCheckFromStart = new DefaultCheck(firstPiece.getPieceColour()).isValidRule(context);
    if (!(new PiecePathValidator()
            .isNoPieceBetween(
                new ChessPosition(oldPos.getRow(), oldPos.getColumn() + 1),
                new ChessPosition(newPos.getRow(), newPos.getColumn() - 1),
                context,
                HORIZONTAL)
        && !isInCheckFromStart)) {
      return false;
    }
    return validateCheckBetween(oldPos, newPos, context);
  }

  private boolean validateCheckBetween(ChessPosition oldPos, ChessPosition newPos, Board context) {
    int fromColumn = Math.min(oldPos.getColumn(), newPos.getColumn());
    int toColumn = Math.max(oldPos.getColumn(), newPos.getColumn());
    for (int j = fromColumn + 1; j < toColumn; j++) {
      ChessPosition currentTile = new ChessPosition(oldPos.getRow(), j);
      Board possibleBoard = context.updatePiecePosition(currentTile, newPos);
      if (new DefaultCheck(context.pieceAt(oldPos).getPieceColour()).isValidRule(possibleBoard)) {
        return false;
      }
    }
    return true;
  }
}
