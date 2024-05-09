package edu.austral.dissis.chess.utils;

import static edu.austral.dissis.common.utils.move.BoardPosition.fromAlgebraic;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.gui.CachedImageResolver;
import edu.austral.dissis.chess.gui.DefaultImageResolver;
import edu.austral.dissis.chess.gui.GameEngine;
import edu.austral.dissis.chess.gui.ImageResolver;
import edu.austral.dissis.chess.providers.GameProvider;
import edu.austral.dissis.chess.ui.gameengine.ChessGameEngine;
import edu.austral.dissis.chess.utils.enums.GameType;
import edu.austral.dissis.chess.utils.result.ChessGameResult;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;

public class AuxStaticMethods {
  public static GameMove moveFromAlgebraic(String fullMove) {
    String from = fullMove.substring(0, 2);
    String to = fullMove.substring(6);
    return new GameMove(fromAlgebraic(from), fromAlgebraic(to));
  }

  public static ChessGameResult makeMove(ChessGame game, String move) {
    return game.makeMove(moveFromAlgebraic(move));
  }

  public static Pair<GameEngine, ImageResolver> setupGame(GameType type) {
    //TODO: test it
    final GameEngine gameEngine = new ChessGameEngine(new GameProvider().provide(type));
    final ImageResolver imageResolver = new CachedImageResolver(new DefaultImageResolver());
    return new Pair<>(gameEngine, imageResolver);
  }

  public static BoardPosition getPosBetween(GameMove move) {
    //TODO: test it
    int rowDelta = move.to().getRow() - move.from().getRow();
    int colDelta = move.to().getColumn() - move.from().getColumn();
    return lastPos(move.from(), colDelta, rowDelta);
  }

  private static BoardPosition lastPos(BoardPosition from, int deltaX, int deltaY) {
    if (deltaX > 0) {
      if (deltaY > 0) {
        return getEnemyPos(from, new Pair<>(1, 1));
      } else {
        return getEnemyPos(from, new Pair<>(-1, 1));
      }
    } else {
      if (deltaY > 0) {
        return getEnemyPos(from, new Pair<>(1, -1));
      } else {
        return getEnemyPos(from, new Pair<>(-1, -1));
      }
    }
  }

  private static BoardPosition getEnemyPos(BoardPosition from, Pair<Integer, Integer> vector) {
    return new BoardPosition(from.getRow() + vector.first(), from.getColumn() + vector.second());
  }
}
