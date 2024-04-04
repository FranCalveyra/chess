package edu.austral.dissis.chess.rule.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.Position;

public class RookMovementRule implements PieceMovementRule {

  @Override
  public boolean isValidMove(Position oldPos, Position newPos, Board context) {
    boolean horizontalMove =
        oldPos.getColumn() != newPos.getColumn() && oldPos.getRow() == newPos.getRow();
    boolean verticalMove =
        oldPos.getColumn() == newPos.getColumn() && oldPos.getRow() != newPos.getRow();
    return horizontalMove || verticalMove;
  }
}
