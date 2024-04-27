package edu.austral.dissis.chess.providers;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.promoters.StandardPromoter;
import edu.austral.dissis.chess.rules.DefaultCheck;
import edu.austral.dissis.chess.selectors.StandardTurnSelector;
import edu.austral.dissis.chess.utils.GameType;

import java.awt.Color;
import java.util.List;

public class GameProvider {
    public ChessGame provide(GameType gameType){
        if(gameType != GameType.DEFAULT){
            return null;
        }
        return new ChessGame(new Board(new ChessPieceMapProvider().provide(gameType)),
                new RuleProvider().provide(gameType),
                List.of(new DefaultCheck(Color.BLACK), new DefaultCheck(Color.WHITE)),
                new StandardPromoter(), new StandardTurnSelector(),Color.WHITE);
    }
}
