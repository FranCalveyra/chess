package edu.austral.dissis.chess.utils.result;

import edu.austral.dissis.chess.engine.ChessGame;

public final class GameResult {
  private final ChessGame game;
  private final ChessMoveResult moveResult;

  public GameResult(ChessGame game, ChessMoveResult moveResult) {
    this.game = game;
    this.moveResult = moveResult;
  }

  public ChessGame game() {
    return game;
  }

  public ChessMoveResult moveResult() {
    return moveResult;
  }
}
