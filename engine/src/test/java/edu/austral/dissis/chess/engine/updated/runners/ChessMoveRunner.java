package edu.austral.dissis.chess.engine.updated.runners;

import static edu.austral.dissis.chess.engine.updated.utils.GameAdapter.mapBoard;
import static edu.austral.dissis.chess.engine.updated.utils.GameAdapter.mapPosition;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.test.TestBoard;
import edu.austral.dissis.chess.test.TestPosition;
import edu.austral.dissis.chess.test.Validity;
import edu.austral.dissis.chess.test.move.TestMoveRunner;
import edu.austral.dissis.chess.utils.result.CheckState;
import edu.austral.dissis.chess.utils.result.ChessGameResult;
import edu.austral.dissis.common.utils.move.GameMove;
import edu.austral.dissis.common.utils.result.GameWon;
import edu.austral.dissis.common.utils.result.PlayResult;
import edu.austral.dissis.common.utils.result.ValidPlay;
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
    ChessGameResult result =
        game.makeMove(new GameMove(mapPosition(fromPosition), mapPosition(toPosition)));
    return getValidity(result.moveResult());
  }

  private Validity getValidity(PlayResult playResult) {
    Map<PlayResult, Validity> validityMap =
        Map.of(
            new GameWon(Color.BLACK), Validity.VALID,
            new GameWon(Color.WHITE), Validity.VALID,
            new ValidPlay(), Validity.VALID,
            new CheckState(Color.WHITE), Validity.VALID,
            new CheckState(Color.BLACK), Validity.VALID);
    return validityMap.get(playResult);
  }
}
