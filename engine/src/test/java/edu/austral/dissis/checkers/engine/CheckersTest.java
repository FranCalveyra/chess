package edu.austral.dissis.checkers.engine;

import static edu.austral.dissis.common.utils.move.BoardPosition.fromAlgebraic;
import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.austral.dissis.checkers.piece.movement.CheckersType;
import edu.austral.dissis.checkers.providers.CheckersPieceProvider;
import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.providers.GameProvider;
import edu.austral.dissis.chess.utils.enums.GameType;
import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.board.MapBoard;
import edu.austral.dissis.common.utils.move.GameMove;
import edu.austral.dissis.common.utils.result.GameResult;
import edu.austral.dissis.common.utils.result.GameWon;
import edu.austral.dissis.common.utils.result.ValidPlay;
import java.awt.Color;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class CheckersTest {
  // TODO: IMPLEMENT
  private ChessGame game = new GameProvider().provide(GameType.DEFAULT_CHECKERS);
  private final CheckersPieceProvider pieceProvider = new CheckersPieceProvider();

  @Test
  public void initialMovement() {
    GameResult result = game.makeMove(new GameMove(fromAlgebraic("a3"), fromAlgebraic("b4")));
    assertEquals(new ValidPlay(), result.moveResult());
  }

  @Test
  public void promotionShouldWork() {
    GameResult result;
    Board board =
        new MapBoard(
            Map.of(
                fromAlgebraic("a7"),
                pieceProvider.provideCheckersPiece(Color.RED, CheckersType.MAN)));
    game =
        new ChessGame(
            board,
            game.getWinConditions(),
            game.getCheckConditions(),
            game.getPromoter(),
            game.getTurnSelector(),
            game.getPreMovementValidator());
    result = game.makeMove(new GameMove(fromAlgebraic("a7"), fromAlgebraic("b8")));
    assertEquals(new GameWon(Color.RED), result.moveResult());
    assertEquals(
        CheckersType.KING,
        ((ChessGame) result.game()).getBoard().pieceAt(fromAlgebraic("b8")).getType());
  }
}
