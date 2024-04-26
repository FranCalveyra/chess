package edu.austral.dissis.chess.engine.exam;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.engine.updated.runners.ChessGameRunner;
import edu.austral.dissis.chess.promoter.StandardPromoter;
import edu.austral.dissis.chess.provider.ChessPieceMapProvider;
import edu.austral.dissis.chess.winConditions.*;
import edu.austral.dissis.chess.test.game.GameTester;
import edu.austral.dissis.chess.turn.StandardTurnSelector;
import edu.austral.dissis.chess.utils.GameType;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ChessTestCase {
    //Setup
    private final java.util.List<WinCondition> rules =
            new ArrayList<>(
                    List.of(
                            new CheckMate(Color.BLACK),
                            new CheckMate(Color.WHITE)));
    private final List<Check> checks = List.of(
            new DefaultCheck(Color.BLACK),
            new DefaultCheck(Color.WHITE));

    private final ChessPieceMapProvider pieceMapProvider = new ChessPieceMapProvider();
    private final ChessGame game = new ChessGame(new Board(pieceMapProvider.provide(GameType.DEFAULT)),
            rules,checks, new StandardPromoter(), new StandardTurnSelector(), Color.WHITE);

    private final ChessGameRunner gameRunner = new ChessGameRunner(game);
    private final GameTester gameTester = new GameTester(gameRunner);

    @TestFactory
    public Stream<DynamicTest> examTests(){
        return gameTester.test();
    }
}
