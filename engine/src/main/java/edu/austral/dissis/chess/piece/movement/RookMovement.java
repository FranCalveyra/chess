package edu.austral.dissis.chess.piece.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.Position;

public class RookMovement implements PieceMovement {

  @Override
  public boolean isValidMove(Position oldPos, Position newPos, Board context) {
    return new HorizontalMovement().isValidMove(oldPos, newPos, context)
        || new VerticalMovement().isValidMove(oldPos, newPos, context);
  }
}
