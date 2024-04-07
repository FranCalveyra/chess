package edu.austral.dissis.chess.piece.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.Position;

public class KingMovement implements PieceMovement {

  @Override
  public boolean isValidMove(Position oldPos, Position newPos, Board context) {
    // King should move only in one direction, one tile at a time
    return Math.abs(newPos.getColumn() - oldPos.getColumn()) == 1
        || Math.abs(newPos.getRow() - oldPos.getRow()) == 1
            && isNotPieceBetween(oldPos, newPos, context);
  }
}
