package edu.austral.dissis.common.rules.premovement.validators;

import edu.austral.dissis.chess.engine.BoardGame;
import edu.austral.dissis.common.utils.move.GameMove;
import edu.austral.dissis.common.utils.result.PlayResult;

public interface PreMovementValidator {
  PlayResult getMoveValidity(GameMove move, BoardGame game);
}
