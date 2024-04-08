package edu.austral.dissis.chess.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.austral.dissis.chess.piece.Piece;
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
  private final Board board = new Board(pieces, new StandardTurnSelector());
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
    game = updateGame(game, whitePawn, new Position(2, 5));
    assertEquals(
        new Position(2, 5),
        getPiecePosition(whitePawn, game.getBoard().getActivePiecesAndPositions()));
    game = updateGame(game, blackPawn, new Position(4, 4));
    assertEquals(
        new Position(4, 4),
        getPiecePosition(blackPawn, game.getBoard().getActivePiecesAndPositions()));
    game = updateGame(game, whitePawn2, new Position(3, 6));
    assertEquals(
        new Position(3, 6),
        getPiecePosition(whitePawn2, game.getBoard().getActivePiecesAndPositions()));
    game = updateGame(game, blackQueen, new Position(3, 7));
    assertEquals(
        new Position(3, 7),
        getPiecePosition(blackQueen, game.getBoard().getActivePiecesAndPositions()));
    assertTrue(new DefaultCheck(Color.WHITE).isValidRule(game.getBoard()));
    assertFalse(new DefaultCheck(Color.BLACK).isValidRule(game.getBoard()));
    assertTrue(new CheckMate(Color.WHITE).isValidRule(game.getBoard())); //Need to fix checkmate because movement rules are not working
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

  protected static ChessGame updateGame(ChessGame game, Piece piece, Position position)
      throws UnallowedMoveException {
    return new ChessGame(game.makeMove(piece, position), game.getRules());
  }
}
