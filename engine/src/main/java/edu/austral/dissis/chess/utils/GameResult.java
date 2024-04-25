package edu.austral.dissis.chess.utils;

import edu.austral.dissis.chess.engine.ChessGame;

public class GameResult {
  private final ChessGame game;
  private final ResultEnum message;

  public GameResult(ChessGame game, ResultEnum message) {
    this.game = game;
    this.message = message;
  }

  public ChessGame getGame() {
    return game;
  }

  public ResultEnum getMessage() {
    return message;
  }
}
