package edu.austral.dissis.chess.rule.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.Position;

public class PawnMovementRule implements PieceMovementRule {

  @Override
  public boolean isValidMove(Position oldPos, Position newPos, Board context) {
    int oldX = oldPos.getColumn();
    int oldY = oldPos.getRow();
    int newX = newPos.getColumn();
    int newY = newPos.getRow();
    return (oldX == newX && (Math.abs(newY - oldY) == 1 || new PawnFirstMoveRule().isValidMove(oldPos,newPos,context))) && !isPieceBetween(oldPos,newPos,context);
  }
}
