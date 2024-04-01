package edu.austral.dissis.chess.rule.movement;

import edu.austral.dissis.chess.utils.Position;

public class KnightMovementRule implements PieceMovementRule {

  @Override
  public boolean isValidMove(Position oldPos, Position newPos) {
    int oldX = oldPos.getColumn(), oldY = oldPos.getRow();
    int newX = newPos.getColumn(), newY = newPos.getRow();
    int deltaX = Math.abs(newX-oldX), deltaY = Math.abs(newY-oldY);
    return deltaX == 2 && deltaY == 1 || deltaX == 1 && deltaY == 2 ;
  }
}
