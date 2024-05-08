package edu.austral.dissis.common.rules.premovement;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.common.utils.move.GameMove;

public interface PreMovementRule {
  boolean isValidRule(GameMove move, ChessGame game);
}
