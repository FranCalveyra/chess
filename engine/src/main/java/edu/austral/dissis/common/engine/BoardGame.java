package edu.austral.dissis.common.engine;

import edu.austral.dissis.common.utils.move.GameMove;
import edu.austral.dissis.common.utils.result.GameResult;

public interface BoardGame {
  GameResult makeMove(GameMove move);
}
