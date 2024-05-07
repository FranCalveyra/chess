package edu.austral.dissis.chess.utils.result;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.common.utils.result.GameResult;
import edu.austral.dissis.common.utils.result.PlayResult;

public class ChessGameResult implements GameResult {
  private final ChessGame game;
  private final PlayResult playResult;

  public ChessGameResult(ChessGame game, PlayResult playResult) {
    this.game = game;
    this.playResult = playResult;
  }
  @Override
  public ChessGame game() {
    return game;
  }
  @Override
  public PlayResult moveResult() {
    return playResult;
  }
}
