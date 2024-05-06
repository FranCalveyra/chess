package edu.austral.dissis.chess.providers;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.engine.MapBoard;
import edu.austral.dissis.chess.promoters.StandardPromoter;
import edu.austral.dissis.chess.rules.DefaultCheck;
import edu.austral.dissis.chess.rules.Extinction;
import edu.austral.dissis.chess.turn.IncrementalTurnSelector;
import edu.austral.dissis.chess.turn.StandardTurnSelector;
import edu.austral.dissis.chess.utils.type.GameType;
import java.awt.Color;
import java.util.List;

public class GameProvider {
  public ChessGame provide(GameType gameType) {
    RuleProvider ruleProvider = new RuleProvider();
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
          new MapBoard(new ChessPieceMapProvider().provide(GameType.SPECIAL, 10, 10), 10, 10),
          List.of(new Extinction(Color.WHITE), new Extinction(Color.BLACK)),
          List.of(new DefaultCheck(Color.WHITE), new DefaultCheck(Color.BLACK)),
          new StandardPromoter(),
          new IncrementalTurnSelector(),
          ruleProvider.providePreMovementValidator(GameType.DEFAULT));
    }
    return null;
  }
}
