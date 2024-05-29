package edu.austral.dissis.chess.engine.updated.runners;

import static edu.austral.dissis.chess.engine.updated.utils.GameAdapter.mapBoard;
import static edu.austral.dissis.chess.engine.updated.utils.TestAdapter.mapTestBoard;

import edu.austral.dissis.chess.gui.GameOver;
import edu.austral.dissis.chess.gui.InvalidMove;
import edu.austral.dissis.chess.gui.Move;
import edu.austral.dissis.chess.gui.MoveResult;
import edu.austral.dissis.chess.gui.NewGameState;
import edu.austral.dissis.chess.gui.PlayerColor;
import edu.austral.dissis.chess.gui.Position;
import edu.austral.dissis.chess.test.TestBoard;
import edu.austral.dissis.chess.test.TestPosition;
import edu.austral.dissis.chess.test.game.BlackCheckMate;
import edu.austral.dissis.chess.test.game.TestGameRunner;
import edu.austral.dissis.chess.test.game.TestMoveFailure;
import edu.austral.dissis.chess.test.game.TestMoveResult;
import edu.austral.dissis.chess.test.game.TestMoveSuccess;
import edu.austral.dissis.chess.test.game.WhiteCheckMate;
import edu.austral.dissis.common.game.BoardGame;
import edu.austral.dissis.common.ui.gameengine.BoardGameEngine;
import org.jetbrains.annotations.NotNull;

public class ChessGameRunner implements TestGameRunner {
  private final BoardGameEngine engine;

  public ChessGameRunner(BoardGameEngine engine) {
    this.engine = engine;
  }

  @NotNull
  @Override
  public TestMoveResult executeMove(@NotNull TestPosition from, @NotNull TestPosition to) {
    MoveResult moveResult =
        engine.applyMove(
            new Move(
                new Position(from.getRow(), from.getCol()),
                new Position(to.getRow(), to.getCol())));
    return mapMoveResult(moveResult);
  }

  @NotNull
  @Override
  public TestBoard getBoard() {
    return mapTestBoard(engine.getGame());
  }

  @NotNull
  @Override
  public TestGameRunner withBoard(@NotNull TestBoard testBoard) {
    BoardGame game = engine.getGame();
    return new ChessGameRunner(
        new BoardGameEngine(
            new BoardGame(
                mapBoard(testBoard),
                game.getWinConditions(),
                game.getPromoter(),
                game.getTurnSelector(),
                game.getPreMovementValidator())));
  }

  @NotNull
  @Override
  public TestMoveResult redo() {
    NewGameState redone = engine.redo();
    return mapMoveResult(redone);
  }

  @NotNull
  @Override
  public TestMoveResult undo() {
    NewGameState undone = engine.undo();
    return mapMoveResult(undone);
  }

  // Private stuff

  private TestMoveResult mapMoveResult(@NotNull MoveResult result) {
    switch (result) {
      case GameOver gameOver:
        return gameOver.getWinner() == PlayerColor.BLACK
            ? new BlackCheckMate(getBoard())
            : new WhiteCheckMate(getBoard());
      case InvalidMove invalidMove:
        return new TestMoveFailure(getBoard());
      case NewGameState validMove:
        return new TestMoveSuccess(this);
      default:
        throw new IllegalStateException();
    }
  }
}
