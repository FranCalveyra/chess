package edu.austral.dissis.chess.utils;

import edu.austral.dissis.chess.engine.ChessGame;

public final class GameResult {
  private final ChessGame game;
  private final ChessMoveResult moveResult; // TODO: Program to interface, use State Pattern with ChessMoveResult

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

  @Override
  public String toString() {
    return "GameResult[" + "game=" + game + ", " + "moveResult=" + moveResult + ']';
  }
}
