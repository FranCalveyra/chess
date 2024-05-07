package edu.austral.dissis.chess.utils;

import static edu.austral.dissis.common.utils.move.BoardPosition.fromAlgebraic;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.gui.CachedImageResolver;
import edu.austral.dissis.chess.gui.DefaultImageResolver;
import edu.austral.dissis.chess.gui.GameEngine;
import edu.austral.dissis.chess.gui.ImageResolver;
import edu.austral.dissis.chess.providers.ChessGameProvider;
import edu.austral.dissis.chess.ui.ChessGameEngine;
import edu.austral.dissis.chess.utils.enums.GameType;
import edu.austral.dissis.common.utils.move.GameMove;
import edu.austral.dissis.chess.utils.result.ChessGameResult;

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
    final GameEngine gameEngine = new ChessGameEngine(new ChessGameProvider().provide(type));
    final ImageResolver imageResolver = new CachedImageResolver(new DefaultImageResolver());
    return new Pair<>(gameEngine, imageResolver);
  }
}
