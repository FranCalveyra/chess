package edu.austral.dissis.chess.validators;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.ChessPosition;
import edu.austral.dissis.chess.utils.MoveType;
import edu.austral.dissis.chess.utils.Pair;

public class PiecePathValidator {

  // Checks if there's no piece between two given positions
  public boolean isNoPieceBetween(
      ChessPosition from, ChessPosition to, Board context, MoveType moveType) {
    if (outOfBoardBounds(from, context) || outOfBoardBounds(to, context)) {
      return false;
    }
    return !switch (moveType) {
      case DIAGONAL -> !checkDiagonal(from, to, context); // Border cases
      case VERTICAL -> checkVertical(from, to, context);
      case HORIZONTAL -> checkHorizontal(from, to, context);
    };
  }

  private boolean checkHorizontal(ChessPosition from, ChessPosition to, Board context) {
    return from.getColumn() > to.getColumn()
        ? isPieceInHorizontal(to, from, context)
        : isPieceInHorizontal(from, to, context);
  }

  private boolean checkVertical(ChessPosition from, ChessPosition to, Board context) {
    return from.getRow() > to.getRow()
        ? isPieceInVertical(to, from, context)
        : isPieceInVertical(from, to, context);
  }

  private boolean checkDiagonal(ChessPosition from, ChessPosition to, Board context) {
    return noTeammateInDiagonal(from, to, context);
  }

  private boolean isPieceInHorizontal(ChessPosition from, ChessPosition to, Board context) {
    for (int j = from.getColumn() + 1; j < to.getColumn(); j++) {
      ChessPosition p = new ChessPosition(from.getRow(), j);
      Piece currentPiece = context.pieceAt(p);
      if (currentPiece != null) {
        return true;
      }
    }
    return false;
  }

  private boolean isPieceInVertical(ChessPosition from, ChessPosition to, Board context) {
    for (int i = from.getRow() + 1; i < to.getRow(); i++) {
      ChessPosition p = new ChessPosition(i, from.getColumn());
      Piece currentPiece = context.pieceAt(p);
      if (currentPiece != null) {
        return true;
      }
    }
    return false;
  }

  private boolean noTeammateInDiagonal(ChessPosition oldPos, ChessPosition newPos, Board context) {
    int deltaX = newPos.getColumn() - oldPos.getColumn();
    int deltaY = newPos.getRow() - oldPos.getRow();
    return noOneInDiagonal(oldPos, newPos, context, deltaX, deltaY);
  }

  private boolean noOneInDiagonal(
      ChessPosition oldPos, ChessPosition newPos, Board context, int deltaX, int deltaY) {
    if (deltaX > 0) {
      if (deltaY > 0) {
        return checkDiagonalWithDelta(oldPos, newPos, context, new Pair<>(1, 1));
      } else {
        return checkDiagonalWithDelta(oldPos, newPos, context, new Pair<>(-1, 1));
      }
    } else {
      if (deltaY > 0) {
        return checkDiagonalWithDelta(oldPos, newPos, context, new Pair<>(1, -1));
      } else {
        return checkDiagonalWithDelta(oldPos, newPos, context, new Pair<>(-1, -1));
      }
    }
  }

  private boolean checkDiagonalWithDelta(
      ChessPosition oldPos, ChessPosition newPos, Board context, Pair<Integer, Integer> vector) {
    int deltaRow = vector.first();
    int deltaColumn = vector.second();
    for (int i = oldPos.getRow() + deltaRow, j = oldPos.getColumn() + deltaColumn;
        i != newPos.getRow() && j != newPos.getColumn();
        i += deltaRow, j += deltaColumn) {
      if (i < 0 || j < 0 || i >= context.getRows() || j >= context.getColumns()) {
        break;
      }
      ChessPosition currentChessPosition = new ChessPosition(i, j);
      Piece pieceAt = context.pieceAt(currentChessPosition);
      if (pieceAt != null) {
        return false;
      }
    }
    Piece lastPiece = context.pieceAt(newPos);
    return lastPiece == null
        || lastPiece.getPieceColour() != context.pieceAt(oldPos).getPieceColour();
  }

  private boolean outOfBoardBounds(ChessPosition pos, Board context) {
    int i = pos.getRow();
    int j = pos.getColumn();
    return i >= context.getRows() || i < 0 || j >= context.getColumns() || j < 0;
  }
}
