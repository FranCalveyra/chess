package edu.austral.dissis.chess.validators;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.common.utils.move.GameMove;
import edu.austral.dissis.common.utils.result.PlayResult;

public interface PreMovementValidator {
  PlayResult getMoveValidity(GameMove move, ChessGame game);
}
