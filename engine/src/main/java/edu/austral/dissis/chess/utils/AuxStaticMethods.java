package edu.austral.dissis.chess.utils;

import static edu.austral.dissis.chess.utils.move.ChessPosition.fromAlgebraic;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.gui.CachedImageResolver;
import edu.austral.dissis.chess.gui.DefaultImageResolver;
import edu.austral.dissis.chess.gui.GameEngine;
import edu.austral.dissis.chess.gui.ImageResolver;
import edu.austral.dissis.chess.providers.GameProvider;
import edu.austral.dissis.chess.ui.ChessGameEngine;
import edu.austral.dissis.chess.utils.move.ChessMove;
import edu.austral.dissis.chess.utils.result.GameResult;
import edu.austral.dissis.chess.utils.type.GameType;

public class AuxStaticMethods {
  public static ChessMove moveFromAlgebraic(String fullMove) {
    String from = fullMove.substring(0, 2);
    String to = fullMove.substring(6);
    return new ChessMove(fromAlgebraic(from), fromAlgebraic(to));
  }

  public static GameResult makeMove(ChessGame game, String move) {
    return game.makeMove(moveFromAlgebraic(move));
  }

  public static Pair<GameEngine, ImageResolver> setupGame(GameType type) {
    final GameEngine gameEngine = new ChessGameEngine(new GameProvider().provide(type));
    final ImageResolver imageResolver = new CachedImageResolver(new DefaultImageResolver());
    return new Pair<>(gameEngine, imageResolver);
  }
}
