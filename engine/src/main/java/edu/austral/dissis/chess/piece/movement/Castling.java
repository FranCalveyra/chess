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
    ChessMove rookMove =
        new ChessMove(
            new ChessPosition(oldPos.getRow(), rookColumn),
            new ChessPosition(oldPos.getRow(), resultCol));
    return List.of(new ChessMove(oldPos, newPos), rookMove);
  }

  private boolean isCastlingPossible(ChessPosition oldPos, ChessPosition newPos, Board context) {
    // This is a king-only rule
    Piece king = context.pieceAt(oldPos);
    int rookColumn = newPos.getColumn() == 2 ? 0 : context.getColumns() - 1;
    Piece rook = context.pieceAt(new ChessPosition(newPos.getRow(), rookColumn));
    if (king == null || rook == null) {
      return false;
    }
    boolean colorCheck = king.getPieceColour() == rook.getPieceColour();
    boolean typeCheck = validateCastlingPieceTypes(king, rook);
    int columnDelta = Math.abs(newPos.getColumn() - oldPos.getColumn());
    boolean displacementCheck = oldPos.getRow() == newPos.getRow() && columnDelta == 2;
    boolean haveNotMoved = !king.hasMoved() && !rook.hasMoved();
    boolean generalChecks = colorCheck && typeCheck && haveNotMoved && displacementCheck;

    if (!generalChecks) {
      return false;
    }
    boolean isInCheckFromStart = new DefaultCheck(king.getPieceColour()).isValidRule(context);
    // If there's a piece between or is in check from start, return false
    if (!(new PiecePathValidator().isNoPieceBetween(oldPos, newPos, context, HORIZONTAL))
        || isInCheckFromStart) {
      return false;
    }
    return validateCheckBetween(oldPos, newPos, context);
  }

  private boolean validateCastlingPieceTypes(Piece king, Piece rook) {
    return king.getType() == PieceType.KING && rook.getType() == PieceType.ROOK;
  }

  private boolean validateCheckBetween(ChessPosition oldPos, ChessPosition newPos, Board context) {
    int fromColumn = Math.min(oldPos.getColumn(), newPos.getColumn());
    int toColumn = Math.max(oldPos.getColumn(), newPos.getColumn());
    Piece king = context.pieceAt(oldPos);
    for (int j = fromColumn + 1; j <= toColumn; j++) {
      ChessPosition currentTile = new ChessPosition(oldPos.getRow(), j);
      Board possibleBoard = context.removePieceAt(oldPos).addPieceAt(currentTile, king);
      if (new DefaultCheck(context.pieceAt(oldPos).getPieceColour()).isValidRule(possibleBoard)) {
        return false;
      }
    }
    return true;
  }
}
