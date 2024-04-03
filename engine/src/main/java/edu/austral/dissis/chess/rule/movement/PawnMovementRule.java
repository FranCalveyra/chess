package edu.austral.dissis.chess.rule.movement;

import edu.austral.dissis.chess.utils.Position;

public class PawnMovementRule implements PieceMovementRule {

  @Override
  public boolean isValidMove(Position oldPos, Position newPos) {
    // Need to handle piece taking
    // Solution: create a new rule
    int oldX = oldPos.getColumn();
    int oldY = oldPos.getRow();
    int newX = newPos.getColumn();
    int newY = newPos.getRow();
    return oldX == newX && (newY - oldY == 1 || newY - oldY == 2);
  }
}
