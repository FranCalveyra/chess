package edu.austral.dissis.chess.piece.movement;

import static edu.austral.dissis.chess.piece.movement.MoveType.DIAGONAL;
import static edu.austral.dissis.chess.piece.movement.MoveType.HORIZONTAL;
import static edu.austral.dissis.chess.piece.movement.MoveType.VERTICAL;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.Position;

public class KingMovement implements PieceMovement {

  @Override
  public boolean isValidMove(Position oldPos, Position newPos, Board context) {
    // King should move only in one direction, one tile at a time
    boolean horizontalMove = Math.abs(newPos.getColumn() - oldPos.getColumn()) == 1;
    boolean verticalMove = Math.abs(newPos.getRow() - oldPos.getRow()) == 1;
    boolean diagonalMove = horizontalMove && verticalMove;
    MoveType moveType;
    if (diagonalMove) {
      moveType = DIAGONAL;
    } else if (horizontalMove) {
      moveType = HORIZONTAL;
    } else if (verticalMove) {
      moveType = VERTICAL;
    } else {
      return false;
    }
    return new PiecePathValidator().isNoPieceBetween(oldPos, newPos, context, moveType);
  }
}
