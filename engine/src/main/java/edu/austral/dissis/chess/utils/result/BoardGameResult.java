package edu.austral.dissis.chess.utils.result;

import edu.austral.dissis.common.engine.BoardGame;
import edu.austral.dissis.common.utils.result.GameResult;
import edu.austral.dissis.common.utils.result.PlayResult;

public class BoardGameResult implements GameResult {
  private final BoardGame game;
  private final PlayResult playResult;

  public BoardGameResult(BoardGame game, PlayResult playResult) {
    this.game = game;
    this.playResult = playResult;
  }

  @Override
  public BoardGame game() {
    return game;
  }

  @Override
  public PlayResult moveResult() {
    return playResult;
  }
}
