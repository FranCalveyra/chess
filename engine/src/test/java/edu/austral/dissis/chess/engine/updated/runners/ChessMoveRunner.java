package edu.austral.dissis.chess.engine.updated.runners;

import static edu.austral.dissis.chess.engine.updated.utils.GameAdapter.mapBoard;
import static edu.austral.dissis.chess.engine.updated.utils.GameAdapter.mapPosition;

import edu.austral.dissis.chess.test.TestBoard;
import edu.austral.dissis.chess.test.TestPosition;
import edu.austral.dissis.chess.test.Validity;
import edu.austral.dissis.chess.test.move.TestMoveRunner;
import edu.austral.dissis.common.utils.result.gameresult.BoardGameResult;
import edu.austral.dissis.chess.utils.result.CheckState;
import edu.austral.dissis.common.engine.BoardGame;
import edu.austral.dissis.common.utils.move.GameMove;
import edu.austral.dissis.common.utils.result.playresult.*;

import java.awt.Color;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class ChessMoveRunner implements TestMoveRunner {

  private BoardGame game;

  public ChessMoveRunner(BoardGame game) {
    this.game = game;
  }

  @NotNull
  @Override
  public Validity executeMove(
      @NotNull TestBoard testBoard,
      @NotNull TestPosition fromPosition,
      @NotNull TestPosition toPosition) {
    game =
        new BoardGame(
            mapBoard(testBoard),
            game.getWinConditions(),
            game.getPromoter(),
            game.getTurnSelector(),
            game.getPreMovementValidator());
    BoardGameResult result =
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
            new CheckState(Color.BLACK), Validity.VALID,
            new PromotedPiece(), Validity.VALID,
            new PieceTaken(), Validity.VALID,
            new InvalidPlay(""), Validity.INVALID
    );
    return validityMap.get(playResult);
  }
}
