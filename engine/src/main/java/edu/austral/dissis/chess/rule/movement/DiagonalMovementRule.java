package edu.austral.dissis.chess.rule.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.Position;

public class DiagonalMovementRule implements PieceMovementRule {

  @Override
  public boolean isValidMove(Position oldPos, Position newPos, Board context) {
    int deltaX = Math.abs(newPos.getColumn() - oldPos.getColumn());
    int deltaY = Math.abs(newPos.getRow() - oldPos.getRow());
    boolean moveCondition = deltaX == deltaY;
    return !isPieceBetween(oldPos, newPos, context) && moveCondition;
    // They have to move the same amount in both coordinates.
    // If not, movement should resemble to a Knight movement.
  }
}
