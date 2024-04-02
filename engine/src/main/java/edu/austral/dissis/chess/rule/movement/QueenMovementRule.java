package edu.austral.dissis.chess.rule.movement;

import edu.austral.dissis.chess.utils.Position;

public class QueenMovementRule implements PieceMovementRule {
  @Override
  public boolean isValidMove(Position oldPos, Position newPos) {
    int oldX = oldPos.getColumn();
    int oldY = oldPos.getRow();
    int newX = newPos.getColumn();
    int newY = newPos.getRow();
    boolean verticalMove = oldX == newX & oldY != newY;
    boolean horizontalMove = oldX != newX & oldY == newY;
    boolean diagonalMove = Math.abs(newX - oldX) == Math.abs(newY - oldY);
    return verticalMove || horizontalMove || diagonalMove;
  }
}
