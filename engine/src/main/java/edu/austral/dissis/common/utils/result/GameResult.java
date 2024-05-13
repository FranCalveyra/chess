package edu.austral.dissis.common.utils.result;

import edu.austral.dissis.common.engine.Game;

public interface GameResult {
  Game game();

  PlayResult moveResult();
}
