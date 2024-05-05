package edu.austral.dissis.chess.engine;

import static edu.austral.dissis.chess.engine.updated.utils.TestFunctions.makeMove;
import static edu.austral.dissis.chess.utils.move.ChessPosition.fromAlgebraic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.providers.GameProvider;
import edu.austral.dissis.chess.providers.PieceProvider;
import edu.austral.dissis.chess.rules.Extinction;
import edu.austral.dissis.chess.rules.WinCondition;
import edu.austral.dissis.chess.turn.IncrementalTurnSelector;
import edu.austral.dissis.chess.utils.move.ChessMove;
import edu.austral.dissis.chess.utils.result.GameResult;
import edu.austral.dissis.chess.utils.result.GameWon;
import edu.austral.dissis.chess.utils.result.InvalidMove;
import edu.austral.dissis.chess.utils.result.PieceTaken;
import edu.austral.dissis.chess.utils.result.ValidMove;
import edu.austral.dissis.chess.utils.type.GameType;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class SpecialCasesTest {
  private ChessGame game = new GameProvider().provide(GameType.DEFAULT);
  private final PieceProvider pieceProvider = new PieceProvider();

  @Test
  public void extinctionTest() {
    WinCondition blackExtinction = new Extinction(Color.BLACK);
    WinCondition whiteExtinction = new Extinction(Color.WHITE);

    assertFalse(
        blackExtinction.isValidRule(game.getBoard())); // Should be false for a default chess game
    assertFalse(
        whiteExtinction.isValidRule(game.getBoard())); // Should be false for a default chess game

    Board board =
        new MapBoard(
            Map.of(
                fromAlgebraic("e4"), pieceProvider.provide(Color.BLACK, PieceType.KING),
                fromAlgebraic("g4"), pieceProvider.provide(Color.WHITE, PieceType.QUEEN),
                fromAlgebraic("a8"), pieceProvider.provide(Color.WHITE, PieceType.QUEEN),
                fromAlgebraic("b7"), pieceProvider.provide(Color.WHITE, PieceType.QUEEN),
                fromAlgebraic("c1"), pieceProvider.provide(Color.WHITE, PieceType.QUEEN),
                fromAlgebraic("d2"), pieceProvider.provide(Color.WHITE, PieceType.QUEEN)));
    game =
        new ChessGame(
            board,
            game.getWinConditions(),
            game.getCheckConditions(),
            game.getPromoter(),
            game.getTurnSelector(),
            game.getPreMovementValidator());
    assertTrue(blackExtinction.isValidRule(game.getBoard()));

    Board newBoard =
        board.addPieceAt(fromAlgebraic("a7"), pieceProvider.provide(Color.BLACK, PieceType.QUEEN));
    // Add one more piece
    assertFalse(blackExtinction.isValidRule(newBoard));
    game =
        new ChessGame(
            newBoard,
            new ArrayList<>(List.of(blackExtinction, whiteExtinction)),
            game.getCheckConditions(),
            game.getPromoter(),
            game.getTurnSelector(),
            game.getPreMovementValidator());
    assertEquals(7, game.getBoard().getPiecesAndPositions().size());
    GameResult result = game.makeMove(new ChessMove(fromAlgebraic("a8"), fromAlgebraic("a7")));
    assertEquals(6, result.game().getBoard().getPiecesAndPositions().size());
    assertEquals(new GameWon(Color.WHITE), result.moveResult());
  }

  @Test
  public void chancellorTest() {
    Board board =
        new MapBoard(
            Map.of(
                fromAlgebraic("e1"), pieceProvider.provide(Color.WHITE, PieceType.KING),
                fromAlgebraic("h1"), pieceProvider.provide(Color.WHITE, PieceType.CHANCELLOR),
                fromAlgebraic("a1"), pieceProvider.provide(Color.WHITE, PieceType.ROOK),
                fromAlgebraic("b1"), pieceProvider.provide(Color.WHITE, PieceType.ARCHBISHOP),
                fromAlgebraic("b8"), pieceProvider.provide(Color.BLACK, PieceType.KING),
                fromAlgebraic("b7"), pieceProvider.provide(Color.BLACK, PieceType.QUEEN),
                fromAlgebraic("a8"), pieceProvider.provide(Color.BLACK, PieceType.ROOK)));
    game =
        new ChessGame(
            board,
            game.getWinConditions(),
            game.getCheckConditions(),
            game.getPromoter(),
            game.getTurnSelector(),
            game.getPreMovementValidator());
    GameResult result = game.makeMove(new ChessMove(fromAlgebraic("e1"), fromAlgebraic("g1")));
    assertEquals(7, result.game().getBoard().getPiecesAndPositions().size());
    assertEquals(new ValidMove(), result.moveResult());
    assertEquals(PieceType.KING, result.game().getBoard().pieceAt(fromAlgebraic("g1")).getType());
    assertEquals(
        PieceType.CHANCELLOR, result.game().getBoard().pieceAt(fromAlgebraic("f1")).getType());

    result = result.game().makeMove(new ChessMove(fromAlgebraic("a8"), fromAlgebraic("a7")));
    assertEquals(new ValidMove(), result.moveResult());

    result = result.game().makeMove(new ChessMove(fromAlgebraic("f1"), fromAlgebraic("g3")));
    assertEquals(new ValidMove(), result.moveResult());

    assertEquals(new ValidMove(), result.moveResult());
    assertEquals(
        PieceType.CHANCELLOR, result.game().getBoard().pieceAt(fromAlgebraic("g3")).getType());

    result = result.game().makeMove(new ChessMove(fromAlgebraic("a7"), fromAlgebraic("a6")));
    assertEquals(new ValidMove(), result.moveResult());

    result = result.game().makeMove(new ChessMove(fromAlgebraic("g3"), fromAlgebraic("e1")));
    assertEquals(new InvalidMove(""), result.moveResult());
    assertEquals(
        PieceType.CHANCELLOR, result.game().getBoard().pieceAt(fromAlgebraic("g3")).getType());

    result = result.game().makeMove(new ChessMove(fromAlgebraic("g3"), fromAlgebraic("g8")));
    assertEquals(new ValidMove(), result.moveResult());
    assertEquals(
        PieceType.CHANCELLOR, result.game().getBoard().pieceAt(fromAlgebraic("g8")).getType());
  }

  @Test
  public void incrementalTurnTest() {
    game =
        new ChessGame(
            game.getBoard(),
            game.getWinConditions(),
            game.getCheckConditions(),
            game.getPromoter(),
            new IncrementalTurnSelector(),
            game.getPreMovementValidator());
    GameResult result = makeMove(game, "a2 -> a4");
    assertEquals(new ValidMove(), result.moveResult());
    assertEquals(Color.BLACK, result.game().getCurrentTurn());

    result = makeMove(result.game(), "a2 -> a4");
    assertEquals(new InvalidMove(""), result.moveResult());

    result = makeMove(result.game(), "b7 -> b5");
    assertEquals(Color.BLACK, result.game().getCurrentTurn());

    result = makeMove(result.game(), "b5 -> a4");
    assertEquals(new PieceTaken(null), result.moveResult());

    assertEquals(Color.WHITE, result.game().getCurrentTurn());
  }

  @Test
  public void resizeTest() {
    game =
        new ChessGame(
            new MapBoard(game.getBoard().getPiecesAndPositions(), 10, 10),
            game.getWinConditions(),
            game.getCheckConditions(),
            game.getPromoter(),
            game.getTurnSelector(),
            game.getPreMovementValidator());
    assertEquals(10, game.getBoard().getColumns());
    assertEquals(10, game.getBoard().getRows());
    GameResult result = makeMove(game, "h1 -> i1");
    assertEquals(new ValidMove(), result.moveResult());
    assertEquals(Color.BLACK, result.game().getCurrentTurn());
    System.out.println(game.getBoard());
  }
}
