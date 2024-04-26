package edu.austral.dissis.chess.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.promoter.StandardPromoter;
import edu.austral.dissis.chess.provider.ChessPieceMapProvider;
import edu.austral.dissis.chess.provider.PieceProvider;
import edu.austral.dissis.chess.rule.*;
import edu.austral.dissis.chess.turn.StandardTurnSelector;
import edu.austral.dissis.chess.utils.GameType;
import edu.austral.dissis.chess.utils.Position;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ChessTest {
  // Setup
  private final Map<Position, Piece> pieces = new ChessPieceMapProvider().provide(GameType.DEFAULT);
  private final Board board = new Board(pieces);
  private final List<WinCondition> rules =
      new ArrayList<>(
          List.of(
              new CheckMate(Color.BLACK),
              new CheckMate(Color.WHITE),
              new Stalemate(Color.BLACK),
              new Stalemate(Color.WHITE)));
  private final List<Check> checks = List.of(
          new DefaultCheck(Color.BLACK),
          new DefaultCheck(Color.WHITE));
  private ChessGame game =
          ChessGame.createChessGame(board, rules, checks,new StandardPromoter(), new StandardTurnSelector(), Color.WHITE);

  // Tests
  @Test
  public void putWhiteKingInCheck() {
    final Piece blackPawn = board.pieceAt(new Position(6, 4));
    final Piece whitePawn = board.pieceAt(new Position(1, 5));
    final Piece whitePawn2 = board.pieceAt(new Position(1, 6));
    final Piece blackQueen = board.pieceAt(new Position(7, 3));
    assertEquals(blackPawn.getPieceColour(), Color.BLACK);
    assertEquals(whitePawn.getPieceColour(), Color.WHITE);
    assertEquals(whitePawn2.getPieceColour(), Color.WHITE);
    assertEquals(blackQueen.getPieceColour(), Color.BLACK);
    assertEquals(PieceType.PAWN, blackPawn.getType());
    assertEquals(PieceType.PAWN, whitePawn.getType());
    assertEquals(PieceType.PAWN, whitePawn2.getType());
    assertEquals(PieceType.QUEEN, blackQueen.getType());
    game = game.makeMove(new Position(1, 5), new Position(2, 5)).getGame();
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new Position(2, 5)).getType());
    game = game.makeMove(new Position(6, 4), new Position(4, 4)).getGame();
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new Position(4, 4)).getType());
    game = game.makeMove(new Position(1, 6), new Position(3, 6)).getGame();
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new Position(3, 6)).getType());
    game = game.makeMove(new Position(7, 3), new Position(3, 7)).getGame();
    assertEquals(PieceType.QUEEN, game.getBoard().pieceAt(new Position(3, 7)).getType());
    assertTrue(new DefaultCheck(Color.WHITE).isValidRule(game.getBoard()));
    assertFalse(new DefaultCheck(Color.BLACK).isValidRule(game.getBoard()));
    assertTrue(new CheckMate(Color.WHITE).isValidRule(game.getBoard()));
  }

  @Test
  public void validateStalemate() {
    PieceProvider pieceProvider = new PieceProvider();
    Map<Position, Piece> stalematePieces =
        Map.of(
            new Position(0, 0),
            pieceProvider.get(Color.WHITE, PieceType.KING),
            new Position(5, 6),
            pieceProvider.get(Color.WHITE, PieceType.QUEEN),
            new Position(7, 7),
            pieceProvider.get(Color.BLACK, PieceType.KING));
    ChessGame newGame =
            ChessGame.createChessGame(
                new Board(stalematePieces),
                game.getRules(),
                game.getCheckConditions(),
                game.getPromoter(),
                game.getSelector(),
                Color.WHITE);
    assertTrue(new Stalemate(Color.WHITE).isValidRule(newGame.getBoard()));
  }

  @Test
  public void validatePromotion() {
    assertEquals(Color.WHITE, game.getCurrentTurn());
    game = game.makeMove(new Position(1, 0), new Position(3, 0)).getGame();
    game = game.makeMove(new Position(6, 0), new Position(4, 0)).getGame();
    assertPositionType(game, PieceType.PAWN, 3, 0);
    assertPositionType(game, PieceType.PAWN, 4, 0);
    game = game.makeMove(new Position(1, 1), new Position(3, 1)).getGame();
    assertPositionType(game, PieceType.PAWN, 3, 1);
    game = game.makeMove(new Position(7, 0), new Position(5, 0)).getGame();
    assertPositionType(game, PieceType.ROOK, 5, 0);

    game = game.makeMove(new Position(3, 1), new Position(4, 0)).getGame();
    assertPositionType(game, PieceType.PAWN, 4, 0);

    game = game.makeMove(new Position(5, 0), new Position(4, 0)).getGame();
    assertPositionType(game, PieceType.ROOK, 4, 0);
    assertEquals(30, game.getBoard().getPiecesAndPositions().size());
    game = game.makeMove(new Position(0, 1), new Position(2, 2)).getGame();
    assertPositionType(game, PieceType.KNIGHT, 2, 2);
    game = game.makeMove(new Position(4, 0), new Position(4, 2)).getGame();
    assertPositionType(game, PieceType.ROOK, 4, 2);
    game = game.makeMove(new Position(3, 0), new Position(4, 0)).getGame();
    assertPositionType(game, PieceType.PAWN, 4, 0);
    game = game.makeMove(new Position(4, 2), new Position(2, 2)).getGame();
    assertPositionType(game, PieceType.ROOK, 2, 2);
    assertEquals(29, game.getBoard().getPiecesAndPositions().size());

    game = game.makeMove(new Position(4, 0), new Position(5, 0)).getGame();
    assertPositionType(game, PieceType.PAWN, 5, 0);
    game = game.makeMove(new Position(2, 2), new Position(4, 2)).getGame();
    assertPositionType(game, PieceType.ROOK, 4, 2);
    game = game.makeMove(new Position(5, 0), new Position(6, 0)).getGame();
    assertPositionType(game, PieceType.PAWN, 6, 0);
    game = game.makeMove(new Position(4, 2), new Position(2, 2)).getGame();
    assertPositionType(game, PieceType.ROOK, 2, 2);
    game = game.makeMove(new Position(6, 0), new Position(7, 0)).getGame();
    // Once promoted
    assertPositionType(game, PieceType.QUEEN, 7, 0);
    assertEquals(Color.WHITE, game.getBoard().pieceAt(new Position(7, 0)).getPieceColour());
  }

  // Private stuff
  protected static Position getPiecePosition(Piece piece, Map<Position, Piece> pieces) {
    for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
      if (entry.getValue() == piece) {
        return entry.getKey();
      }
    }
    return null;
  }

  private void assertPositionType(ChessGame currentGame, PieceType pieceType, int i, int j) {
    assertEquals(pieceType, currentGame.getBoard().pieceAt(new Position(i, j)).getType());
  }
}
