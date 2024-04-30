package edu.austral.dissis.chess.utils;

import edu.austral.dissis.chess.engine.ChessGame;
import java.util.Objects;

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

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (GameResult) obj;
    return Objects.equals(this.game, that.game) && Objects.equals(this.moveResult, that.moveResult);
  }

  @Override
  public int hashCode() {
    return Objects.hash(game, moveResult);
  }

  @Override
  public String toString() {
    return "GameResult[" + "game=" + game + ", " + "moveResult=" + moveResult + ']';
  }
}
