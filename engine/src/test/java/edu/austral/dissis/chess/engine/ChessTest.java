package edu.austral.dissis.chess.engine;

import static edu.austral.dissis.chess.engine.updated.utils.TestFunctions.makeMove;
import static edu.austral.dissis.chess.utils.move.ChessPosition.fromAlgebraic;
import static java.awt.Color.BLACK;
import static java.awt.Color.WHITE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.providers.GameProvider;
import edu.austral.dissis.chess.providers.PieceProvider;
import edu.austral.dissis.chess.rules.CheckMate;
import edu.austral.dissis.chess.rules.DefaultCheck;
import edu.austral.dissis.chess.utils.move.ChessPosition;
import edu.austral.dissis.chess.utils.result.GameResult;
import edu.austral.dissis.chess.utils.result.GameWon;
import edu.austral.dissis.chess.utils.result.ValidMove;
import edu.austral.dissis.chess.utils.type.GameType;
import java.awt.Color;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ChessTest {
  // Setup
  private ChessGame game = new GameProvider().provide(GameType.DEFAULT);

  // Tests
  @Test
  public void foolsMate() {
    final Piece blackPawn = game.getBoard().pieceAt(fromAlgebraic("e7"));
    final Piece whitePawn = game.getBoard().pieceAt(fromAlgebraic("f2"));
    final Piece whitePawn2 = game.getBoard().pieceAt(fromAlgebraic("g2"));
    final Piece blackQueen = game.getBoard().pieceAt(fromAlgebraic("d8"));
    assertEquals(blackPawn.getPieceColour(), Color.BLACK);
    assertEquals(whitePawn.getPieceColour(), WHITE);
    assertEquals(whitePawn2.getPieceColour(), WHITE);
    assertEquals(blackQueen.getPieceColour(), Color.BLACK);
    assertEquals(PieceType.PAWN, blackPawn.getType());
    assertEquals(PieceType.PAWN, whitePawn.getType());
    assertEquals(PieceType.PAWN, whitePawn2.getType());
    assertEquals(PieceType.QUEEN, blackQueen.getType());
    game = makeMove(game, "f2 -> f3").game();
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(fromAlgebraic("f3")).getType());
    game = makeMove(game, "e7 -> e5").game();
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(fromAlgebraic("e5")).getType());
    game = makeMove(game, "g2 -> g4").game();
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(fromAlgebraic("g4")).getType());
    GameResult finishingMove = makeMove(game, "d8 -> h4");
    game = finishingMove.game();
    System.out.println(game.getBoard());
    assertEquals(new GameWon(BLACK), finishingMove.moveResult());
    assertEquals(PieceType.QUEEN, game.getBoard().pieceAt(fromAlgebraic("h4")).getType());
    assertTrue(new DefaultCheck(WHITE).isValidRule(game.getBoard()));
    assertFalse(new DefaultCheck(Color.BLACK).isValidRule(game.getBoard()));
    assertTrue(new CheckMate(WHITE).isValidRule(game.getBoard()));
  }

  @Test
  public void validatePromotion() {
    assertEquals(WHITE, game.getCurrentTurn());
    GameResult firstResult = makeMove(game, "a2 -> a4");
    game = firstResult.game();
    assertEquals(new ValidMove(), firstResult.moveResult());
    game = makeMove(game, "a7 -> a5").game();
    assertPositionType(game, PieceType.PAWN, 3, 0);
    assertPositionType(game, PieceType.PAWN, 4, 0);
    game = makeMove(game, "b2 -> b4").game();
    assertPositionType(game, PieceType.PAWN, 3, 1);
    game = makeMove(game, "a8 -> a6").game();
    assertPositionType(game, PieceType.ROOK, 5, 0);

    game = makeMove(game, "b4 -> a5").game();
    assertPositionType(game, PieceType.PAWN, 4, 0);

    game = makeMove(game, "a6 -> a5").game();
    assertPositionType(game, PieceType.ROOK, 4, 0);
    assertEquals(30, game.getBoard().getPiecesAndPositions().size());
    game = makeMove(game, "b1 -> c3").game();
    assertPositionType(game, PieceType.KNIGHT, 2, 2);
    game = makeMove(game, "a5 -> c5").game();
    assertPositionType(game, PieceType.ROOK, 4, 2);
    game = makeMove(game, "a4 -> a5").game();
    assertPositionType(game, PieceType.PAWN, 4, 0);
    game = makeMove(game, "c5 -> c3").game();
    assertPositionType(game, PieceType.ROOK, 2, 2);
    assertEquals(29, game.getBoard().getPiecesAndPositions().size());

    game = makeMove(game, "a5 -> a6").game();
    assertPositionType(game, PieceType.PAWN, 5, 0);
    game = makeMove(game, "c3 -> c5").game();
    assertPositionType(game, PieceType.ROOK, 4, 2);
    game = makeMove(game, "a6 -> a7").game();
    assertPositionType(game, PieceType.PAWN, 6, 0);
    game = makeMove(game, "c5 -> c3").game();
    assertPositionType(game, PieceType.ROOK, 2, 2);
    game = makeMove(game, "a7 -> a8").game();

    // Once promoted:
    assertPositionType(game, PieceType.QUEEN, 7, 0);
    assertEquals(WHITE, game.getBoard().pieceAt(fromAlgebraic("a8")).getPieceColour());
  }

  @Test
  public void possibleCheckmateSaving() {
    PieceProvider provider = new PieceProvider();
    Map<ChessPosition, Piece> situation =
        Map.of(
            fromAlgebraic("d1"),
            provider.provide(BLACK, PieceType.QUEEN),
            fromAlgebraic("e1"),
            provider.provide(BLACK, PieceType.KING),
            fromAlgebraic("f1"),
            provider.provide(BLACK, PieceType.BISHOP),
            fromAlgebraic("d2"),
            provider.provide(BLACK, PieceType.QUEEN),
            fromAlgebraic("a5"),
            provider.provide(WHITE, PieceType.QUEEN));
    MapBoard currentBoard = new MapBoard(situation);
    game =
        new ChessGame(
            currentBoard,
            game.getWinConditions(),
            game.getCheckConditions(),
            game.getPromoter(),
            game.getTurnSelector(),
            game.getPreMovementValidator());
    assertFalse(new CheckMate(BLACK).isValidRule(game.getBoard()));
    game = makeMove(game, "a5 -> d2").game();
    assertFalse(new CheckMate(BLACK).isValidRule(game.getBoard()));
  }

  // Private stuff
  protected static ChessPosition getPiecePosition(Piece piece, Map<ChessPosition, Piece> pieces) {
    return pieces.entrySet().stream()
        .filter(entry -> entry.getValue() == piece)
        .findFirst()
        .map(Map.Entry::getKey)
        .orElse(null);
  }

  private void assertPositionType(ChessGame currentGame, PieceType pieceType, int i, int j) {
    assertEquals(pieceType, currentGame.getBoard().pieceAt(new ChessPosition(i, j)).getType());
  }
}
