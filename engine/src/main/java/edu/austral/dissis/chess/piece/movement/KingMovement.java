package edu.austral.dissis.chess.piece.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.Position;

public class KingMovement implements PieceMovement {

  @Override
  public boolean isValidMove(Position oldPos, Position newPos, Board context) {
    // King should move only in one direction, one tile at a time
    boolean horizontalMove = Math.abs(newPos.getColumn() - oldPos.getColumn()) == 1;
    boolean verticalMove = Math.abs(newPos.getRow() - oldPos.getRow()) == 1;
    boolean diagonalMove = horizontalMove && verticalMove;
    boolean move = horizontalMove || verticalMove || diagonalMove;
    return noPieceBetween(oldPos, newPos, context) && move;
  }
}
