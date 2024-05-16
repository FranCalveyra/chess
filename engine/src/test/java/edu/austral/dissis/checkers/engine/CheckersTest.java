package edu.austral.dissis.checkers.engine;

import static edu.austral.dissis.common.utils.AuxStaticMethods.makeMove;
import static edu.austral.dissis.common.utils.AuxStaticMethods.moveFromAlgebraic;
import static edu.austral.dissis.common.utils.move.BoardPosition.fromAlgebraic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.austral.dissis.checkers.piece.movement.CheckersType;
import edu.austral.dissis.checkers.providers.CheckersPieceProvider;
import edu.austral.dissis.chess.providers.GameProvider;
import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.board.MapBoard;
import edu.austral.dissis.common.engine.BoardGame;
import edu.austral.dissis.common.piece.movement.restrictions.validators.PieceBetweenIsAnEnemy;
import edu.austral.dissis.common.rules.winconds.NoAvailableMoves;
import edu.austral.dissis.common.utils.enums.GameType;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;
import edu.austral.dissis.common.utils.result.gameresult.BoardGameResult;
import edu.austral.dissis.common.utils.result.gameresult.GameResult;
import edu.austral.dissis.common.utils.result.playresult.GameWon;
import edu.austral.dissis.common.utils.result.playresult.InvalidPlay;
import edu.austral.dissis.common.utils.result.playresult.PieceTaken;
import edu.austral.dissis.common.utils.result.playresult.ValidPlay;
import java.awt.Color;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class CheckersTest {
  private BoardGame game = new GameProvider().provide(GameType.DEFAULT_CHECKERS);
  private final CheckersPieceProvider pieceProvider = new CheckersPieceProvider();

  @Test
  public void initialMovement() {
    game =
        new BoardGame(
            game.getBoard(),
            game.getWinConditions(),
            game.getPromoter(),
            game.getTurnSelector().changeTurn(null, game.getBoard(), new ValidPlay()),
            game.getPreMovementValidator());
    GameResult result = game.makeMove(new GameMove(fromAlgebraic("a3"), fromAlgebraic("b4")));
    assertEquals(new ValidPlay(), result.moveResult());
  }

  @Test
  public void shouldTakeEnemy() {
    game =
        new BoardGame(
            game.getBoard(),
            game.getWinConditions(),
            game.getPromoter(),
            game.getTurnSelector().changeTurn(null, game.getBoard(), new ValidPlay()),
            game.getPreMovementValidator());
    BoardGameResult result = makeMove(game, "a3 -> b4");
    assertEquals(new ValidPlay(), result.moveResult());
    assertEquals(Color.BLACK, result.game().getCurrentTurn());
    result = makeMove(result.game(), "a3 -> b4");
    assertEquals(new InvalidPlay(""), result.moveResult());
    result = makeMove(result.game(), "d6 -> c5");
    assertEquals(new ValidPlay(), result.moveResult());
    assertEquals(Color.RED, result.game().getCurrentTurn());
    assertEquals(CheckersType.MAN, result.game().getBoard().pieceAt(fromAlgebraic("c5")).getType());
    assertEquals(CheckersType.MAN, result.game().getBoard().pieceAt(fromAlgebraic("b4")).getType());
    assertNotEquals(
        result.game().getBoard().pieceAt(fromAlgebraic("c5")).getPieceColour(),
        result.game().getBoard().pieceAt(fromAlgebraic("b4")).getPieceColour());
    assertTrue(
        new PieceBetweenIsAnEnemy()
            .isValidMove(moveFromAlgebraic("b4 -> d6"), result.game().getBoard()));
    result = makeMove(result.game(), "b4 -> d6");
    assertEquals(23, result.game().getBoard().getPiecesAndPositions().size());
    assertEquals(new PieceTaken(), result.moveResult());
    assertEquals(
        Color.BLACK,
        result
            .game()
            .getCurrentTurn()); // Should change turn whenever no more attacking moves can be done
  }

  @Test
  public void promotionShouldWork() {
    game =
        new BoardGame(
            game.getBoard(),
            game.getWinConditions(),
            game.getPromoter(),
            game.getTurnSelector().changeTurn(null, game.getBoard(), new ValidPlay()),
            game.getPreMovementValidator());
    GameResult result;
    Board board =
        new MapBoard(
            Map.of(
                fromAlgebraic("a7"),
                pieceProvider.provideCheckersPiece(Color.RED, CheckersType.MAN)));
    game =
        new BoardGame(
            board,
            game.getWinConditions(),
            game.getPromoter(),
            game.getTurnSelector(),
            game.getPreMovementValidator());
    result = game.makeMove(new GameMove(fromAlgebraic("a7"), fromAlgebraic("b8")));
    assertEquals(new GameWon(Color.RED), result.moveResult());
    assertEquals(
        CheckersType.KING,
        ((BoardGame) result.game()).getBoard().pieceAt(fromAlgebraic("b8")).getType());
  }

  @Test
  public void noMoveAvailableShouldResultInWinning() {
    game =
        new BoardGame(
            game.getBoard(),
            game.getWinConditions(),
            game.getPromoter(),
            game.getTurnSelector().changeTurn(null, game.getBoard(), new ValidPlay()),
            game.getPreMovementValidator());
    CheckersPieceProvider provider = new CheckersPieceProvider();
    Board board =
        new MapBoard(
            Map.of(
                new BoardPosition(7, 7), provider.provideCheckersPiece(Color.RED, CheckersType.MAN),
                new BoardPosition(1, 2),
                    provider.provideCheckersPiece(Color.BLACK, CheckersType.MAN)));
    BoardGame current =
        new BoardGame(
            board,
            game.getWinConditions(),
            game.getPromoter(),
            game.getTurnSelector().changeTurn(null, game.getBoard(), new ValidPlay()),
            game.getPreMovementValidator());
    assertEquals(Color.BLACK, current.getCurrentTurn());
    BoardGameResult result =
        current.makeMove(new GameMove(new BoardPosition(1, 2), new BoardPosition(0, 1)));
    assertEquals(new GameWon(Color.BLACK), result.moveResult());
    assertTrue(new NoAvailableMoves(Color.RED).isValidRule(result.game().getBoard()));
  }

  @Test
  public void onlyOneMovementShouldBeAbleToBeDone() {
    BoardGameResult result = game.makeMove(moveFromAlgebraic("b6 -> c5"));
    assertEquals(new ValidPlay(), result.moveResult());
    result = result.game().makeMove(moveFromAlgebraic("c3 -> d4"));
    assertEquals(new ValidPlay(), result.moveResult());
    result = result.game().makeMove(moveFromAlgebraic("d6 -> e5"));
    assertEquals(new ValidPlay(), result.moveResult());
    assertEquals(
        1,
        result
            .game()
            .getBoard()
            .pieceAt(fromAlgebraic("g3"))
            .getPlay(moveFromAlgebraic("g3 -> h4"), result.game().getBoard())
            .size());
    result = result.game().makeMove(moveFromAlgebraic("g3 -> h4"));
    assertEquals(new InvalidPlay(""), result.moveResult());
  }
}
