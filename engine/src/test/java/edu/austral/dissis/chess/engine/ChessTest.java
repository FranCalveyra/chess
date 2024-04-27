package edu.austral.dissis.chess.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.promoters.StandardPromoter;
import edu.austral.dissis.chess.providers.ChessPieceMapProvider;
import edu.austral.dissis.chess.providers.RuleProvider;
import edu.austral.dissis.chess.rules.Check;
import edu.austral.dissis.chess.rules.CheckMate;
import edu.austral.dissis.chess.rules.DefaultCheck;
import edu.austral.dissis.chess.rules.WinCondition;
import edu.austral.dissis.chess.selectors.StandardTurnSelector;
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.utils.GameType;
import edu.austral.dissis.chess.utils.ChessPosition;
import java.awt.Color;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ChessTest {
  // Setup
  private final Map<ChessPosition, Piece> pieces = new ChessPieceMapProvider().provide(GameType.DEFAULT);
  private final Board board = new Board(pieces);
  private final List<WinCondition> rules = new RuleProvider().provide(GameType.DEFAULT);
  private final List<Check> checks =
      List.of(new DefaultCheck(Color.BLACK), new DefaultCheck(Color.WHITE));
  private ChessGame game =
      ChessGame.createChessGame(
          board, rules, checks, new StandardPromoter(), new StandardTurnSelector(), Color.WHITE, 0);

  // Tests
  @Test
  public void putWhiteKingInCheck() {
    final Piece blackPawn = board.pieceAt(new ChessPosition(6, 4));
    final Piece whitePawn = board.pieceAt(new ChessPosition(1, 5));
    final Piece whitePawn2 = board.pieceAt(new ChessPosition(1, 6));
    final Piece blackQueen = board.pieceAt(new ChessPosition(7, 3));
    assertEquals(blackPawn.getPieceColour(), Color.BLACK);
    assertEquals(whitePawn.getPieceColour(), Color.WHITE);
    assertEquals(whitePawn2.getPieceColour(), Color.WHITE);
    assertEquals(blackQueen.getPieceColour(), Color.BLACK);
    assertEquals(PieceType.PAWN, blackPawn.getType());
    assertEquals(PieceType.PAWN, whitePawn.getType());
    assertEquals(PieceType.PAWN, whitePawn2.getType());
    assertEquals(PieceType.QUEEN, blackQueen.getType());
    game = game.makeMove(new ChessMove(new ChessPosition(1, 5), new ChessPosition(2, 5))).game();
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new ChessPosition(2, 5)).getType());
    game = game.makeMove(new ChessMove(new ChessPosition(6, 4), new ChessPosition(4, 4))).game();
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new ChessPosition(4, 4)).getType());
    game = game.makeMove(new ChessMove(new ChessPosition(1, 6), new ChessPosition(3, 6))).game();
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new ChessPosition(3, 6)).getType());
    game = game.makeMove(new ChessMove(new ChessPosition(7, 3), new ChessPosition(3, 7))).game();
    assertEquals(PieceType.QUEEN, game.getBoard().pieceAt(new ChessPosition(3, 7)).getType());
    assertTrue(new DefaultCheck(Color.WHITE).isValidRule(game.getBoard()));
    assertFalse(new DefaultCheck(Color.BLACK).isValidRule(game.getBoard()));
    System.out.println(game.getBoard());
    assertTrue(new CheckMate(Color.WHITE).isValidRule(game.getBoard()));
  }

  @Test
  public void validatePromotion() {
    assertEquals(Color.WHITE, game.getCurrentTurn());
    game = game.makeMove(new ChessMove(new ChessPosition(1, 0), new ChessPosition(3, 0))).game();
    game = game.makeMove(new ChessMove(new ChessPosition(6, 0), new ChessPosition(4, 0))).game();
    assertPositionType(game, PieceType.PAWN, 3, 0);
    assertPositionType(game, PieceType.PAWN, 4, 0);
    game = game.makeMove(new ChessMove(new ChessPosition(1, 1), new ChessPosition(3, 1))).game();
    assertPositionType(game, PieceType.PAWN, 3, 1);
    game = game.makeMove(new ChessMove(new ChessPosition(7, 0), new ChessPosition(5, 0))).game();
    assertPositionType(game, PieceType.ROOK, 5, 0);

    game = game.makeMove(new ChessMove(new ChessPosition(3, 1), new ChessPosition(4, 0))).game();
    assertPositionType(game, PieceType.PAWN, 4, 0);

    game = game.makeMove(new ChessMove(new ChessPosition(5, 0), new ChessPosition(4, 0))).game();
    assertPositionType(game, PieceType.ROOK, 4, 0);
    assertEquals(30, game.getBoard().getPiecesAndPositions().size());
    game = game.makeMove(new ChessMove(new ChessPosition(0, 1), new ChessPosition(2, 2))).game();
    assertPositionType(game, PieceType.KNIGHT, 2, 2);
    game = game.makeMove(new ChessMove(new ChessPosition(4, 0), new ChessPosition(4, 2))).game();
    assertPositionType(game, PieceType.ROOK, 4, 2);
    game = game.makeMove(new ChessMove(new ChessPosition(3, 0), new ChessPosition(4, 0))).game();
    assertPositionType(game, PieceType.PAWN, 4, 0);
    game = game.makeMove(new ChessMove(new ChessPosition(4, 2), new ChessPosition(2, 2))).game();
    assertPositionType(game, PieceType.ROOK, 2, 2);
    assertEquals(29, game.getBoard().getPiecesAndPositions().size());

    game = game.makeMove(new ChessMove(new ChessPosition(4, 0), new ChessPosition(5, 0))).game();
    assertPositionType(game, PieceType.PAWN, 5, 0);
    game = game.makeMove(new ChessMove(new ChessPosition(2, 2), new ChessPosition(4, 2))).game();
    assertPositionType(game, PieceType.ROOK, 4, 2);
    game = game.makeMove(new ChessMove(new ChessPosition(5, 0), new ChessPosition(6, 0))).game();
    assertPositionType(game, PieceType.PAWN, 6, 0);
    game = game.makeMove(new ChessMove(new ChessPosition(4, 2), new ChessPosition(2, 2))).game();
    assertPositionType(game, PieceType.ROOK, 2, 2);
    game = game.makeMove(new ChessMove(new ChessPosition(6, 0), new ChessPosition(7, 0))).game();
    // Once promoted
    assertPositionType(game, PieceType.QUEEN, 7, 0);
    assertEquals(Color.WHITE, game.getBoard().pieceAt(new ChessPosition(7, 0)).getPieceColour());
  }

  // Private stuff
  protected static ChessPosition getPiecePosition(Piece piece, Map<ChessPosition, Piece> pieces) {
    for (Map.Entry<ChessPosition, Piece> entry : pieces.entrySet()) {
      if (entry.getValue() == piece) {
        return entry.getKey();
      }
    }
    return null;
  }

  private void assertPositionType(ChessGame currentGame, PieceType pieceType, int i, int j) {
    assertEquals(pieceType, currentGame.getBoard().pieceAt(new ChessPosition(i, j)).getType());
  }
}
