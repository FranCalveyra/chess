package edu.austral.dissis.chess.engine.updated.runners;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.rule.WinCondition;
import edu.austral.dissis.chess.test.TestBoard;
import edu.austral.dissis.chess.test.TestPosition;
import edu.austral.dissis.chess.test.game.TestGameRunner;
import edu.austral.dissis.chess.test.game.TestMoveResult;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TestChessGameRunner implements TestGameRunner {
    //TODO: implement

    private final ChessGame game;
    public TestChessGameRunner(ChessGame game){
        this.game = game;
    }

    @NotNull
    @Override
    public TestMoveResult executeMove(@NotNull TestPosition from, @NotNull TestPosition to) {
        return null;
    }

    @NotNull
    @Override
    public TestBoard getBoard() {
        return null;
    }

    @NotNull
    @Override
    public TestGameRunner withBoard(@NotNull TestBoard testBoard) {
        return null;
    }
}
