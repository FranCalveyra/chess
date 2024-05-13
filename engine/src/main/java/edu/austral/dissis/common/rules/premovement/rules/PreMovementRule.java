package edu.austral.dissis.common.rules.premovement.rules;

import edu.austral.dissis.common.engine.BoardGame;
import edu.austral.dissis.common.utils.move.GameMove;

public interface PreMovementRule {
  boolean isValidRule(GameMove move, BoardGame game);
}
