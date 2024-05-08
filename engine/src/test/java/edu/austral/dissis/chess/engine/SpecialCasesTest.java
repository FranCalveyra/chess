package edu.austral.dissis.chess.engine;

import static edu.austral.dissis.chess.utils.AuxStaticMethods.makeMove;
import static edu.austral.dissis.common.utils.move.BoardPosition.fromAlgebraic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.austral.dissis.chess.piece.movement.type.ChessPieceType;
import edu.austral.dissis.chess.providers.ChessGameProvider;
import edu.austral.dissis.chess.providers.ChessPieceProvider;
import edu.austral.dissis.chess.utils.enums.GameType;
import edu.austral.dissis.chess.utils.result.ChessGameResult;
import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.board.MapBoard;
import edu.austral.dissis.common.rules.winconds.Extinction;
import edu.austral.dissis.common.rules.winconds.WinCondition;
import edu.austral.dissis.common.turn.IncrementalTurnSelector;
import edu.austral.dissis.common.utils.move.GameMove;
import edu.austral.dissis.common.utils.result.GameWon;
import edu.austral.dissis.common.utils.result.InvalidPlay;
import edu.austral.dissis.common.utils.result.PieceTaken;
import edu.austral.dissis.common.utils.result.ValidPlay;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class SpecialCasesTest {
  private ChessGame game = new ChessGameProvider().provide(GameType.DEFAULT);
  private final ChessPieceProvider chessPieceProvider = new ChessPieceProvider();

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
                fromAlgebraic("e4"), chessPieceProvider.provide(Color.BLACK, ChessPieceType.KING),
                fromAlgebraic("g4"), chessPieceProvider.provide(Color.WHITE, ChessPieceType.QUEEN),
                fromAlgebraic("a8"), chessPieceProvider.provide(Color.WHITE, ChessPieceType.QUEEN),
                fromAlgebraic("b7"), chessPieceProvider.provide(Color.WHITE, ChessPieceType.QUEEN),
                fromAlgebraic("c1"), chessPieceProvider.provide(Color.WHITE, ChessPieceType.QUEEN),
                fromAlgebraic("d2"),
                    chessPieceProvider.provide(Color.WHITE, ChessPieceType.QUEEN)));
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
        board.addPieceAt(
            fromAlgebraic("a7"), chessPieceProvider.provide(Color.BLACK, ChessPieceType.QUEEN));
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
    ChessGameResult result = game.makeMove(new GameMove(fromAlgebraic("a8"), fromAlgebraic("a7")));
    assertEquals(6, result.game().getBoard().getPiecesAndPositions().size());
    assertEquals(new GameWon(Color.WHITE), result.moveResult());
  }

  @Test
  public void chancellorTest() {
    Board board =
        new MapBoard(
            Map.of(
                fromAlgebraic("e1"), chessPieceProvider.provide(Color.WHITE, ChessPieceType.KING),
                fromAlgebraic("h1"),
                    chessPieceProvider.provide(Color.WHITE, ChessPieceType.CHANCELLOR),
                fromAlgebraic("a1"), chessPieceProvider.provide(Color.WHITE, ChessPieceType.ROOK),
                fromAlgebraic("b1"),
                    chessPieceProvider.provide(Color.WHITE, ChessPieceType.ARCHBISHOP),
                fromAlgebraic("b8"), chessPieceProvider.provide(Color.BLACK, ChessPieceType.KING),
                fromAlgebraic("b7"), chessPieceProvider.provide(Color.BLACK, ChessPieceType.QUEEN),
                fromAlgebraic("a8"), chessPieceProvider.provide(Color.BLACK, ChessPieceType.ROOK)));
    game =
        new ChessGame(
            board,
            game.getWinConditions(),
            game.getCheckConditions(),
            game.getPromoter(),
            game.getTurnSelector(),
            game.getPreMovementValidator());
    ChessGameResult result = game.makeMove(new GameMove(fromAlgebraic("e1"), fromAlgebraic("g1")));
    assertEquals(7, result.game().getBoard().getPiecesAndPositions().size());
    assertEquals(new ValidPlay(), result.moveResult());
    assertEquals(
        ChessPieceType.KING, result.game().getBoard().pieceAt(fromAlgebraic("g1")).getType());
    assertEquals(
        ChessPieceType.CHANCELLOR, result.game().getBoard().pieceAt(fromAlgebraic("f1")).getType());

    result = result.game().makeMove(new GameMove(fromAlgebraic("a8"), fromAlgebraic("a7")));
    assertEquals(new ValidPlay(), result.moveResult());

    result = result.game().makeMove(new GameMove(fromAlgebraic("f1"), fromAlgebraic("g3")));
    assertEquals(new ValidPlay(), result.moveResult());

    assertEquals(new ValidPlay(), result.moveResult());
    assertEquals(
        ChessPieceType.CHANCELLOR, result.game().getBoard().pieceAt(fromAlgebraic("g3")).getType());

    result = result.game().makeMove(new GameMove(fromAlgebraic("a7"), fromAlgebraic("a6")));
    assertEquals(new ValidPlay(), result.moveResult());

    result = result.game().makeMove(new GameMove(fromAlgebraic("g3"), fromAlgebraic("e1")));
    assertEquals(new InvalidPlay(""), result.moveResult());
    assertEquals(
        ChessPieceType.CHANCELLOR, result.game().getBoard().pieceAt(fromAlgebraic("g3")).getType());

    result = result.game().makeMove(new GameMove(fromAlgebraic("g3"), fromAlgebraic("g8")));
    assertEquals(new ValidPlay(), result.moveResult());
    assertEquals(
        ChessPieceType.CHANCELLOR, result.game().getBoard().pieceAt(fromAlgebraic("g8")).getType());
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
    ChessGameResult result = makeMove(game, "a2 -> a4");
    assertEquals(new ValidPlay(), result.moveResult());
    assertEquals(Color.BLACK, result.game().getCurrentTurn());

    result = makeMove(result.game(), "a2 -> a4");
    assertEquals(new InvalidPlay(""), result.moveResult());

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
    ChessGameResult result = makeMove(game, "h1 -> i1");
    assertEquals(new ValidPlay(), result.moveResult());
    assertEquals(Color.BLACK, result.game().getCurrentTurn());
  }
}
