package edu.austral.dissis.chess.validator;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.MoveType;
import edu.austral.dissis.chess.utils.Pair;
import edu.austral.dissis.chess.utils.Position;

public class PiecePathValidator {

    public boolean isNoPieceBetween(Position from, Position to, Board context, MoveType moveType) {
        if(outOfBoardBounds(from,context) || outOfBoardBounds(to,context)){
            return false;
        }
        return !switch (moveType) {
      case DIAGONAL -> !checkDiagonal(from, to, context); // Border cases
      case VERTICAL -> checkVertical(from, to, context);
      case HORIZONTAL -> checkHorizontal(from, to, context);
    };
  }

  private boolean checkHorizontal(Position from, Position to, Board context) {
    return from.getColumn() > to.getColumn()
        ? isPieceInHorizontal(to, from, context)
        : isPieceInHorizontal(from, to, context);
  }

  private boolean checkVertical(Position from, Position to, Board context) {
    return from.getRow() > to.getRow()
        ? isPieceInVertical(to, from, context)
        : isPieceInVertical(from, to, context);
  }

  private boolean checkDiagonal(Position from, Position to, Board context) {
    return noTeammateInDiagonal(from, to, context);
  }

  private boolean isPieceInHorizontal(Position from, Position to, Board context) {
    for (int j = from.getColumn() + 1; j < to.getColumn(); j++) {
      Position p = new Position(from.getRow(), j);
      Piece currentPiece = context.pieceAt(p);
      if (currentPiece != null) {
        return true;
      }
    }
    return false;
  }

  private boolean isPieceInVertical(Position from, Position to, Board context) {
    for (int i = from.getRow() + 1; i < to.getRow(); i++) {
      Position p = new Position(i, from.getColumn());
      Piece currentPiece = context.pieceAt(p);
      if (currentPiece != null) {
        return true;
      }
    }
    return false;
  }

  private boolean noTeammateInDiagonal(Position oldPos, Position newPos, Board context) {
    int deltaX = newPos.getColumn() - oldPos.getColumn();
    int deltaY = newPos.getRow() - oldPos.getRow();
    return noOneInDiagonal(oldPos, newPos, context, deltaX, deltaY);
  }

  private boolean noOneInDiagonal(
      Position oldPos, Position newPos, Board context, int deltaX, int deltaY) {
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
      Position oldPos, Position newPos, Board context, Pair<Integer> vector) {
    int deltaRow = vector.first();
    int deltaColumn = vector.second();
    for (int i = oldPos.getRow() + deltaRow, j = oldPos.getColumn() + deltaColumn;
        i != newPos.getRow() && j != newPos.getColumn();
        i += deltaRow, j += deltaColumn) {
      if (i < 0 || j < 0 || i >= context.getRows() || j >= context.getColumns()) {
        break;
      }
      Position currentPosition = new Position(i, j);
      Piece pieceAt = context.pieceAt(currentPosition);
      if (pieceAt != null) {
        return false;
      }
    }
    Piece lastPiece = context.pieceAt(newPos);
    return lastPiece == null
        || lastPiece.getPieceColour() != context.pieceAt(oldPos).getPieceColour();
  }
    private boolean outOfBoardBounds(Position pos, Board context) {
        int i = pos.getRow();
        int j = pos.getColumn();
        return i >= context.getRows() || i < 0 || j >= context.getColumns() || j < 0;
    }

}
