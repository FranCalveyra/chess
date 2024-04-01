package edu.austral.dissis.chess.rule.movement;

import edu.austral.dissis.chess.utils.Position;

public class RookMovementRule implements PieceMovementRule {

  @Override
  public boolean isValidMove(Position oldPos, Position newPos) {
    boolean xMove = oldPos.getColumn() != newPos.getColumn() && oldPos.getRow() == newPos.getRow();
    boolean yMove = oldPos.getColumn() == newPos.getColumn() && oldPos.getRow() != newPos.getRow();
    return xMove || yMove;
  }
}
