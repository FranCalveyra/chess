package edu.austral.dissis.chess.piece.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.Position;

public class QueenMovement implements PieceMovement {
  @Override
  public boolean isValidMove(Position oldPos, Position newPos, Board context) {
    return new DiagonalMovement().isValidMove(oldPos, newPos, context)
        || new RookMovement().isValidMove(oldPos, newPos, context);
  }
}
