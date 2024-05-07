package edu.austral.dissis.chess.providers;

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

public class ChessGameProvider {
  public ChessGame provide(GameType gameType) {
    ChessRuleProvider ruleProvider = new ChessRuleProvider();
    if (gameType == GameType.DEFAULT) {
      return new ChessGame(
          new MapBoard(new ChessPieceMapProvider().provide(gameType, 8, 8)),
          ruleProvider.provideWinConditions(gameType),
          List.of(new DefaultCheck(Color.WHITE), new DefaultCheck(Color.BLACK)),
          new StandardPromoter(),
          new StandardTurnSelector(),
          ruleProvider.providePreMovementValidator(gameType));
    } else if (gameType == GameType.SPECIAL) {
      return new ChessGame(
          new MapBoard(new ChessPieceMapProvider().provide(GameType.CAPABLANCA, 10, 10), 10, 10),
          List.of(new Extinction(Color.WHITE), new Extinction(Color.BLACK)),
          List.of(new DefaultCheck(Color.WHITE), new DefaultCheck(Color.BLACK)),
          new StandardPromoter(),
          new IncrementalTurnSelector(),
          ruleProvider.providePreMovementValidator(GameType.DEFAULT));
    } else if (gameType == GameType.CAPABLANCA) {
      return new ChessGame(
          new MapBoard(new ChessPieceMapProvider().provide(GameType.CAPABLANCA, 10, 10), 10, 10),
          ruleProvider.provideWinConditions(GameType.DEFAULT),
          List.of(new DefaultCheck(Color.WHITE), new DefaultCheck(Color.BLACK)),
          new StandardPromoter(),
          new StandardTurnSelector(),
          ruleProvider.providePreMovementValidator(GameType.DEFAULT));
    }
    return null;
  }
}
