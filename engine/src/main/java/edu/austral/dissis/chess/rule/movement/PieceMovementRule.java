package edu.austral.dissis.chess.rule.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.rule.GameRule;
import edu.austral.dissis.chess.utils.Position;

public interface PieceMovementRule extends GameRule {
  @Override
  boolean isValidRule(Board context);

  boolean isValidMove(Position oldPos, Position newPos);
}
