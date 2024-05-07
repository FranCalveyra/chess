package edu.austral.dissis.chess.validators;

import edu.austral.dissis.chess.utils.Pair;
import edu.austral.dissis.chess.utils.enums.MoveType;
import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.utils.move.BoardPosition;

public class PiecePathValidator {

  // Checks if there's no piece between two given positions
  public boolean isNoPieceBetween(
      BoardPosition from, BoardPosition to, Board context, MoveType moveType) {
    switch (moveType) {
      case DIAGONAL:
        return checkDiagonal(from, to, context);
        // Border cases
      case VERTICAL:
        return !checkVertical(from, to, context);
      case HORIZONTAL:
        return !checkHorizontal(from, to, context);
      default:
        throw new IllegalArgumentException();
    }
  }

  private boolean checkHorizontal(BoardPosition from, BoardPosition to, Board context) {
    return from.getColumn() > to.getColumn()
        ? isPieceInHorizontal(to, from, context)
        : isPieceInHorizontal(from, to, context);
  }

  private boolean checkVertical(BoardPosition from, BoardPosition to, Board context) {
    return from.getRow() > to.getRow()
        ? isPieceInVertical(to, from, context)
        : isPieceInVertical(from, to, context);
  }

  private boolean checkDiagonal(BoardPosition from, BoardPosition to, Board context) {
    return noTeammateInDiagonal(from, to, context);
  }

  private boolean isPieceInHorizontal(BoardPosition from, BoardPosition to, Board context) {
    for (int j = from.getColumn() + 1; j < to.getColumn(); j++) {
      BoardPosition p = new BoardPosition(from.getRow(), j);
      Piece currentPiece = context.pieceAt(p);
      if (currentPiece != null) {
        return true;
      }
    }
    return false;
  }

  private boolean isPieceInVertical(BoardPosition from, BoardPosition to, Board context) {
    for (int i = from.getRow() + 1; i < to.getRow(); i++) {
      BoardPosition p = new BoardPosition(i, from.getColumn());
      Piece currentPiece = context.pieceAt(p);
      if (currentPiece != null) {
        return true;
      }
    }
    return false;
  }

  private boolean noTeammateInDiagonal(BoardPosition oldPos, BoardPosition newPos, Board context) {
    int deltaX = newPos.getColumn() - oldPos.getColumn();
    int deltaY = newPos.getRow() - oldPos.getRow();
    return noOneInDiagonal(oldPos, newPos, context, deltaX, deltaY);
  }

  private boolean noOneInDiagonal(
      BoardPosition oldPos, BoardPosition newPos, Board context, int deltaX, int deltaY) {
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
      BoardPosition oldPos, BoardPosition newPos, Board context, Pair<Integer, Integer> vector) {
    int deltaRow = vector.first();
    int deltaColumn = vector.second();
    for (int i = oldPos.getRow() + deltaRow, j = oldPos.getColumn() + deltaColumn;
        i != newPos.getRow() && j != newPos.getColumn();
        i += deltaRow, j += deltaColumn) {
      if (i < 0 || j < 0 || i >= context.getRows() || j >= context.getColumns()) {
        break;
      }
      BoardPosition currentBoardPosition = new BoardPosition(i, j);
      Piece pieceAt = context.pieceAt(currentBoardPosition);
      if (pieceAt != null) {
        return false;
      }
    }
    Piece lastPiece = context.pieceAt(newPos);
    return lastPiece == null
        || lastPiece.getPieceColour() != context.pieceAt(oldPos).getPieceColour();
  }
}
