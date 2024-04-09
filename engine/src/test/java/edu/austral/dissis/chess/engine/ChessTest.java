package edu.austral.dissis.chess.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.promoter.StandardPromoter;
import edu.austral.dissis.chess.provider.ChessPieceMapProvider;
import edu.austral.dissis.chess.rule.BorderGameRule;
import edu.austral.dissis.chess.rule.CheckMate;
import edu.austral.dissis.chess.rule.DefaultCheck;
import edu.austral.dissis.chess.rule.Stalemate;
import edu.austral.dissis.chess.turn.StandardTurnSelector;
import edu.austral.dissis.chess.utils.GameType;
import edu.austral.dissis.chess.utils.Position;
import edu.austral.dissis.chess.utils.UnallowedMoveException;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ChessTest {
  // Setup
  private final Map<Position, Piece> pieces = new ChessPieceMapProvider().provide(GameType.DEFAULT);
  private final Board board = new Board(pieces, new StandardTurnSelector(), new StandardPromoter());
  private final List<BorderGameRule> rules =
      new ArrayList<>(
          List.of(
              new DefaultCheck(Color.BLACK),
              new DefaultCheck(Color.WHITE),
              new CheckMate(Color.BLACK),
              new CheckMate(Color.WHITE),
              new Stalemate()));
  private ChessGame game = new ChessGame(board, rules);

  ChessTest() {
    game = game.startGame();
  }

  // Tests
  @Test
  public void putWhiteKingInCheck() throws UnallowedMoveException {
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
    game = game.makeMove(new Position(1, 5), new Position(2, 5));
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new Position(2, 5)).getType());
    game = game.makeMove(new Position(6, 4), new Position(4, 4));
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new Position(4, 4)).getType());
    game = game.makeMove(new Position(1, 6), new Position(3, 6));
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new Position(3, 6)).getType());
    game = game.makeMove(new Position(7, 3), new Position(3, 7));
    assertEquals(PieceType.QUEEN, game.getBoard().pieceAt(new Position(3, 7)).getType());
    assertTrue(new DefaultCheck(Color.WHITE).isValidRule(game.getBoard()));
    assertFalse(new DefaultCheck(Color.BLACK).isValidRule(game.getBoard()));
    assertTrue(new CheckMate(Color.WHITE).isValidRule(game.getBoard()));
  }

  @Test
  public void validateStalemate() throws UnallowedMoveException {
    // TODO
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
}
