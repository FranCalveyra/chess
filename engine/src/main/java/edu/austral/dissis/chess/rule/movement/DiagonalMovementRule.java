package edu.austral.dissis.chess.rule.movement;

import edu.austral.dissis.chess.utils.Position;

public class DiagonalMovementRule implements PieceMovementRule {

  @Override
  public boolean isValidMove(Position oldPos, Position newPos) {
    int deltaX = Math.abs(newPos.getColumn() - oldPos.getColumn() );
    int deltaY = Math.abs(newPos.getRow() - oldPos.getRow() );
    return deltaX == deltaY; // They have to move the same amount in both coordinates. If not, movement should be resemblance to a Knight movement.
  }
}
