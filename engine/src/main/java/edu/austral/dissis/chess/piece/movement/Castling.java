package edu.austral.dissis.chess.piece.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.rule.DefaultCheck;
import edu.austral.dissis.chess.utils.Position;
import edu.austral.dissis.chess.utils.UnallowedMoveException;
import java.util.List;

public class Castling implements PieceMovement {
  // Only valid whenever king and rooks haven't been moved yet.

  @Override
  public boolean isValidMove(Position oldPos, Position newPos, Board context) {
    return isCastlingPossible(oldPos, newPos, context);
  }

  private boolean isCastlingPossible(Position oldPos, Position newPos, Board context) {
    Piece firstPiece = context.getPiecesAndPositions().get(oldPos);
    Piece secondPiece = context.getPiecesAndPositions().get(newPos);
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
    if (!this.noPieceBetween(
            new Position(oldPos.getRow(), oldPos.getColumn() + 1),
            new Position(newPos.getRow(), newPos.getColumn() - 1),
            context)
        || isInCheckFromStart) {
      return false;
    }
    return validateCheckBetween(oldPos, newPos, context);
  }

  private boolean validateCheckBetween(Position oldPos, Position newPos, Board context) {
    int fromColumn = Math.min(oldPos.getColumn(), newPos.getColumn());
    int toColumn = Math.max(oldPos.getColumn(), newPos.getColumn());
    Position fromPos = new Position(oldPos.getRow(), fromColumn);
    Position toPos = new Position(oldPos.getRow(), toColumn);

    Piece firstPiece = context.pieceAt(fromPos);
    Piece secondPiece = context.pieceAt(toPos);

    List<PieceMovement> firstPieceMoves = firstPiece.getMovements();
    List<PieceMovement> secondPieceMoves = secondPiece.getMovements();
    boolean firstValid = validateMovements(firstPieceMoves, fromPos, toPos, context);
    boolean secondValid = validateMovements(secondPieceMoves, toPos, fromPos, context);
    return firstValid && secondValid;
  }

  private boolean validateMovements(
      List<PieceMovement> pieceMovements, Position fromPos, Position toPos, Board context) {
    int fromColumn = fromPos.getColumn();
    int toColumn = toPos.getColumn();
    for (int j = fromColumn + 1; j < toColumn; j++) {
      Position currentTile = new Position(fromPos.getRow(), j);
      for (PieceMovement rule : pieceMovements) {
        if (rule.getClass() == Castling.class) {
          continue;
        }
        try {
          if (!rule.isValidMove(fromPos, toPos, context)) {
            return false;
          }
          Board possibleContext =
              context.updatePiecePosition(fromPos, currentTile, PieceType.QUEEN);
          if (new DefaultCheck(context.pieceAt(fromPos).getPieceColour())
              .isValidRule(possibleContext)) {
            return false;
          }
        } catch (UnallowedMoveException e) {
          continue;
        }
      }
    }
    return true;
  }

  @Override
  public boolean noPieceBetween(Position oldPos, Position newPos, Board context) {
    int fromColumn = Math.min(oldPos.getColumn(), newPos.getColumn());
    int toColumn = Math.max(oldPos.getColumn(), newPos.getColumn());
    for (int j = fromColumn + 1; j < toColumn; j++) {
      Position currentTile = new Position(oldPos.getRow(), j);
      if (context.pieceAt(currentTile) != null) {
        return false;
      }
    }
    return true;
  }
}
