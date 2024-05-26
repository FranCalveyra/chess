package edu.austral.dissis.common.utils.result.gameresult;

import edu.austral.dissis.common.game.BoardGame;
import edu.austral.dissis.common.utils.result.playresult.PlayResult;

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
