package edu.austral.dissis.chess.providers;

import edu.austral.dissis.checkers.promoter.CheckersPromoter;
import edu.austral.dissis.checkers.providers.CheckersPieceMapProvider;
import edu.austral.dissis.checkers.turn.CheckersTurnSelector;
import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.promoters.StandardPromoter;
import edu.austral.dissis.chess.rules.winconds.DefaultCheck;
import edu.austral.dissis.chess.utils.enums.GameType;
import edu.austral.dissis.common.board.MapBoard;
import edu.austral.dissis.common.rules.winconds.Extinction;
import edu.austral.dissis.common.turn.IncrementalTurnSelector;
import edu.austral.dissis.common.turn.StandardTurnSelector;
import java.awt.Color;
import java.util.List;

public class GameProvider {
  public ChessGame provide(GameType gameType) {
    ChessRuleProvider ruleProvider = new ChessRuleProvider();
    if (gameType == GameType.DEFAULT_CHESS) {
      return new ChessGame(
          new MapBoard(new ChessPieceMapProvider().provide(gameType, 8, 8)),
          ruleProvider.provideWinConditions(gameType),
          List.of(new DefaultCheck(Color.WHITE), new DefaultCheck(Color.BLACK)),
          new StandardPromoter(),
          new StandardTurnSelector(),
          ruleProvider.providePreMovementValidator(gameType));
    } else if (gameType == GameType.SPECIAL_CHESS) {
      return new ChessGame(
          new MapBoard(
              new ChessPieceMapProvider().provide(GameType.CAPABLANCA_CHESS, 9, 12), 9, 12),
          List.of(new Extinction(Color.WHITE), new Extinction(Color.BLACK)),
          List.of(new DefaultCheck(Color.WHITE), new DefaultCheck(Color.BLACK)),
          new StandardPromoter(),
          new IncrementalTurnSelector(),
          ruleProvider.providePreMovementValidator(GameType.DEFAULT_CHESS));
    } else if (gameType == GameType.CAPABLANCA_CHESS) {
      return new ChessGame(
          new MapBoard(
              new ChessPieceMapProvider().provide(GameType.CAPABLANCA_CHESS, 8, 10), 8, 10),
          ruleProvider.provideWinConditions(GameType.DEFAULT_CHESS),
          List.of(new DefaultCheck(Color.WHITE), new DefaultCheck(Color.BLACK)),
          new StandardPromoter(),
          new StandardTurnSelector(),
          ruleProvider.providePreMovementValidator(GameType.DEFAULT_CHESS));
    } else if (gameType == GameType.DEFAULT_CHECKERS) {
      return new ChessGame(
          new MapBoard(new CheckersPieceMapProvider().provide(gameType, 8, 8)),
          List.of(new Extinction(Color.RED), new Extinction(Color.BLACK)),
          null,
          new CheckersPromoter(),
          new CheckersTurnSelector(),
          ruleProvider.providePreMovementValidator(GameType.DEFAULT_CHECKERS));
    }
    return null;
  }
}
