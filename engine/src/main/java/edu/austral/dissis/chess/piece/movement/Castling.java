package edu.austral.dissis.chess.piece.movement;

import static edu.austral.dissis.chess.utils.MoveType.HORIZONTAL;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.rules.DefaultCheck;
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.utils.ChessPosition;
import edu.austral.dissis.chess.validators.PiecePathValidator;
import java.util.List;

public class Castling implements PieceMovement {
  // Only valid whenever king and rooks haven't been moved yet,
  // and move that is wanted to be done doesn't leave the king in check/checkmate.

  @Override
  public boolean isValidMove(ChessPosition oldPos, ChessPosition newPos, Board context) {
    return isCastlingPossible(oldPos, newPos, context);
  }

  @Override
  public List<ChessMove> getMovesToExecute(
      ChessPosition oldPos, ChessPosition newPos, Board context) {
    int rookColumn = newPos.getColumn() == 2 ? 0 : context.getColumns() - 1;
    int resultCol = rookColumn == 0 ? oldPos.getColumn() - 1 : oldPos.getColumn() + 1;
    return List.of(
        new ChessMove(oldPos, newPos),
        new ChessMove(
            new ChessPosition(oldPos.getRow(), rookColumn),
            new ChessPosition(oldPos.getRow(), resultCol)));
  }

  private boolean isCastlingPossible(ChessPosition oldPos, ChessPosition newPos, Board context) {
    Piece firstPiece = context.pieceAt(oldPos);
    int rookColumn = newPos.getColumn() == 2 ? 0 : context.getColumns() - 1;
    Piece secondPiece = context.pieceAt(new ChessPosition(newPos.getRow(), rookColumn));
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
    boolean displacementCheck = oldPos.getRow() == newPos.getRow() && columnDelta == 2;
    boolean movementCheck = !firstPiece.hasMoved() && !secondPiece.hasMoved();
    boolean generalChecks = colorCheck && typeCheck && movementCheck && displacementCheck;

    if (!generalChecks) {
      return false;
    }
    boolean isInCheckFromStart = new DefaultCheck(firstPiece.getPieceColour()).isValidRule(context);
    // If there's a piece between or is in check from start, return false
    if (!(new PiecePathValidator().isNoPieceBetween(oldPos, newPos, context, HORIZONTAL))
        || isInCheckFromStart) {
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
