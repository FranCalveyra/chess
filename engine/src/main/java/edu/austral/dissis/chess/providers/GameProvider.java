package edu.austral.dissis.chess.providers;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.promoters.StandardPromoter;
import edu.austral.dissis.chess.rules.Check;
import edu.austral.dissis.chess.rules.DefaultCheck;
import edu.austral.dissis.chess.selectors.StandardTurnSelector;
import edu.austral.dissis.chess.utils.GameType;
import java.awt.Color;
import java.util.List;

public class GameProvider {
  public ChessGame provide(GameType gameType) {
    if (gameType != GameType.DEFAULT) {
      return null;
    }
    RuleProvider ruleProvider = new RuleProvider();
    return new ChessGame(new Board(new ChessPieceMapProvider().provide(gameType)),
            ruleProvider.provideWinConditions(gameType),
            List.of(new DefaultCheck(Color.WHITE), new DefaultCheck(Color.BLACK)),
            new StandardPromoter(), new StandardTurnSelector(), Color.WHITE,
            ruleProvider.providePreMovementValidator(gameType)
            );
  }
}
