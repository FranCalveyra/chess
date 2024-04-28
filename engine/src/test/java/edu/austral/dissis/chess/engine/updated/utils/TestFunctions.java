package edu.austral.dissis.chess.engine.updated.utils;

import static edu.austral.dissis.chess.utils.ChessPosition.fromAlgebraic;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.utils.GameResult;

public class TestFunctions {
  public static ChessMove moveFromAlgebraic(String fullMove) {
    String from = fullMove.substring(0, 2);
    String to = fullMove.substring(6);
    return new ChessMove(fromAlgebraic(from), fromAlgebraic(to));
  }

  public static GameResult makeMove(ChessGame game, String move) {
    return game.makeMove(moveFromAlgebraic(move));
  }
}
