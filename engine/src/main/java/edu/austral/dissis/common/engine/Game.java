package edu.austral.dissis.common.engine;

import edu.austral.dissis.common.utils.move.GameMove;
import edu.austral.dissis.common.utils.result.gameresult.GameResult;

public interface Game {
  GameResult makeMove(GameMove move);
}
