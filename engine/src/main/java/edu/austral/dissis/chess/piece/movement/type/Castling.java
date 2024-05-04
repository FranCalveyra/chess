package edu.austral.dissis.chess.piece.movement.type;

import static edu.austral.dissis.chess.utils.MoveType.HORIZONTAL;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.movement.PieceMovement;
import edu.austral.dissis.chess.rules.DefaultCheck;
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.utils.ChessPosition;
import edu.austral.dissis.chess.validators.PiecePathValidator;
import java.util.List;

public class Castling implements PieceMovement {
  // Only valid whenever king and rooks haven't been moved yet,
  // and move to be done doesn't leave put king in risk.

  @Override
  public boolean isValidMove(ChessMove move, Board context) {
    return isCastlingPossible(move.from(), move.to(), context);
  }

  @Override
  public List<ChessMove> getMovesToExecute(ChessMove move, Board context) {
    ChessPosition oldPos = move.from();
    ChessPosition newPos = move.to();
    int rookColumn = getRookColumn(context, newPos);
    int resultCol = getResultCol(rookColumn, oldPos);
    ChessPosition rookFrom = new ChessPosition(oldPos.getRow(), rookColumn);
    ChessPosition rookTo = new ChessPosition(oldPos.getRow(), resultCol);
    ChessMove rookMove = new ChessMove(rookFrom, rookTo);
    return List.of(move, rookMove);
  }

  private int getResultCol(int rookColumn, ChessPosition oldPos) {
    return rookColumn == 0 ? oldPos.getColumn() - 1 : oldPos.getColumn() + 1;
  }

  private int getRookColumn(Board context, ChessPosition newPos) {
    return newPos.getColumn() == 2 ? 0 : context.getColumns() - 1;
  }

  private boolean isCastlingPossible(ChessPosition oldPos, ChessPosition newPos, Board context) {
    Piece king = context.pieceAt(oldPos);
    int rookColumn = getRookColumn(context, newPos);
    Piece rook = context.pieceAt(new ChessPosition(newPos.getRow(), rookColumn));
    return validate(oldPos, newPos, context, king, rook);
  }

  private boolean validate(
      ChessPosition oldPos, ChessPosition newPos, Board context, Piece king, Piece rook) {
    if (king == null
        || rook == null
        || !validGeneralChecks(oldPos, newPos, king, rook)
        || checkOrBlockedPath(oldPos, newPos, context, king)) {
      return false;
    }
    return validateCheckBetween(oldPos, newPos, context);
  }

  private boolean checkOrBlockedPath(
      ChessPosition oldPos, ChessPosition newPos, Board context, Piece king) {
    boolean isInCheckFromStart = new DefaultCheck(king.getPieceColour()).isValidRule(context);
    return !(new PiecePathValidator().isNoPieceBetween(oldPos, newPos, context, HORIZONTAL))
        || isInCheckFromStart;
  }

  private boolean validCastlingDisplacement(
      ChessPosition oldPos, ChessPosition newPos, int columnDelta) {
    return oldPos.getRow() == newPos.getRow() && columnDelta == 2;
  }

  private boolean neitherHaveMoved(Piece king, Piece rook) {
    return !king.hasMoved() && !rook.hasMoved();
  }

  private boolean sameColor(Piece king, Piece rook) {
    return king.getPieceColour() == rook.getPieceColour();
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

  private boolean validGeneralChecks(
      ChessPosition oldPos, ChessPosition newPos, Piece king, Piece rook) {
    boolean colorCheck = sameColor(king, rook);
    int columnDelta = Math.abs(newPos.getColumn() - oldPos.getColumn());
    boolean displacementCheck = validCastlingDisplacement(oldPos, newPos, columnDelta);
    boolean haveNotMoved = neitherHaveMoved(king, rook);
    return colorCheck && haveNotMoved && displacementCheck;
  }
}
