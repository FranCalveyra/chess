package edu.austral.dissis.chess.piece.movement;

import static edu.austral.dissis.chess.piece.movement.MoveType.HORIZONTAL;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.Position;

public class HorizontalMovement implements PieceMovement {
  @Override
  public boolean isValidMove(Position oldPos, Position newPos, Board context) {
    boolean validMove =
        (oldPos.getColumn() != newPos.getColumn()) && (oldPos.getRow() == newPos.getRow());
    if (!validMove) {
      return false;
    }
    return new PiecePathValidator().isNoPieceBetween(oldPos, newPos, context, HORIZONTAL);
  }
}
