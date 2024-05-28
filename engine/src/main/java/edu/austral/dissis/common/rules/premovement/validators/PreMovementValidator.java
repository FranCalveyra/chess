package edu.austral.dissis.common.rules.premovement.validators;

import edu.austral.dissis.common.game.BoardGame;
import edu.austral.dissis.common.utils.move.GameMove;

public interface PreMovementValidator {
  boolean isValidRule(GameMove move, BoardGame game);

  // Make it immutable
  String getFailureMessage();
}
