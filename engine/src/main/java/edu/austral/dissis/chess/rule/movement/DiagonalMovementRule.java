package edu.austral.dissis.chess.rule.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.Position;

public class DiagonalMovementRule implements PieceMovementRule{
  @Override
  public boolean isValidRule(Board context) {
    return false;
  }

  @Override
  public boolean isValidMove(Position oldPos, Position newPos) {
    return false;
  }
}
