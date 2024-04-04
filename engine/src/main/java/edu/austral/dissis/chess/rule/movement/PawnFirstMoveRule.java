package edu.austral.dissis.chess.rule.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.Position;

public class PawnFirstMoveRule implements PieceMovementRule {
  @Override
  public boolean isValidMove(Position oldPos, Position newPos, Board context) {
    //TODO
    //Need pawn original position.
    return false;
  }
}
