package edu.austral.dissis.chess.engine.updated.runners;

import static edu.austral.dissis.chess.engine.updated.utils.GameAdapter.mapBoard;
import static edu.austral.dissis.chess.engine.updated.utils.GameAdapter.mapPosition;
import static edu.austral.dissis.chess.utils.ChessMoveResult.INVALID_MOVE;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.test.TestBoard;
import edu.austral.dissis.chess.test.TestPosition;
import edu.austral.dissis.chess.test.Validity;
import edu.austral.dissis.chess.test.move.TestMoveRunner;
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.utils.GameResult;
import org.jetbrains.annotations.NotNull;

public class ChessMoveRunner implements TestMoveRunner {

  private ChessGame game;

  public ChessMoveRunner(ChessGame game) {
    this.game = game;
  }

  @NotNull
  @Override
  public Validity executeMove(
      @NotNull TestBoard testBoard,
      @NotNull TestPosition fromPosition,
      @NotNull TestPosition toPosition) {
    game =
        new ChessGame(
            mapBoard(testBoard),
            game.getWinConditions(),
            game.getCheckConditions(),
            game.getPromoter(),
            game.getSelector(),
            game.getPreMovementValidator());
    GameResult result =
        game.makeMove(new ChessMove(mapPosition(fromPosition), mapPosition(toPosition)));
    return result.moveResult() == INVALID_MOVE ? Validity.INVALID : Validity.VALID;
  }
}
