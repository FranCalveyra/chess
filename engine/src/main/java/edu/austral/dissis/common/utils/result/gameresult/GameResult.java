package edu.austral.dissis.common.utils.result.gameresult;

import edu.austral.dissis.common.game.Game;
import edu.austral.dissis.common.utils.result.playresult.PlayResult;

public interface GameResult {
  Game game();

  PlayResult moveResult();
}
