package edu.austral.dissis.common.utils.result;

import edu.austral.dissis.common.engine.BoardGame;

public interface GameResult {
    BoardGame game();
    PlayResult moveResult();
}
