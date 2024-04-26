package edu.austral.dissis.chess.utils;

import edu.austral.dissis.chess.engine.ChessGame;

public class GameResult {
  private final ChessGame game;
  private final MoveResult message;

  public GameResult(ChessGame game, MoveResult message) {
    this.game = game;
    this.message = message;
  }

  public ChessGame getGame() {
    return game;
  }

  public MoveResult getMessage() {
    return message;
  }
}
