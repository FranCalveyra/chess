package edu.austral.dissis.chess.engine.updated.runners;

import static edu.austral.dissis.chess.engine.updated.utils.GameAdapter.mapBoard;
import static edu.austral.dissis.chess.engine.updated.utils.GameAdapter.mapPosition;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.test.TestBoard;
import edu.austral.dissis.chess.test.TestPosition;
import edu.austral.dissis.chess.test.Validity;
import edu.austral.dissis.chess.test.move.TestMoveRunner;
import edu.austral.dissis.chess.utils.move.ChessMove;
import edu.austral.dissis.chess.utils.result.CheckState;
import edu.austral.dissis.chess.utils.result.ChessMoveResult;
import edu.austral.dissis.chess.utils.result.GameResult;
import edu.austral.dissis.chess.utils.result.GameWon;
import edu.austral.dissis.chess.utils.result.ValidMove;
import java.awt.Color;
import java.util.Map;
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
            game.getTurnSelector(),
            game.getPreMovementValidator());
    GameResult result =
        game.makeMove(new ChessMove(mapPosition(fromPosition), mapPosition(toPosition)));
    return getValidity(result.moveResult());
  }

  private Validity getValidity(ChessMoveResult chessMoveResult) {
    Map<ChessMoveResult, Validity> validityMap =
        Map.of(
            new GameWon(Color.BLACK), Validity.VALID,
            new GameWon(Color.WHITE), Validity.VALID,
            new ValidMove(), Validity.VALID,
            new CheckState(Color.WHITE), Validity.VALID,
            new CheckState(Color.BLACK), Validity.VALID);
    return validityMap.get(chessMoveResult);
  }
}
