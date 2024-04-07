package edu.austral.dissis.chess.engine;

import static edu.austral.dissis.chess.engine.ChessTest.getPiecePosition;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.provider.ChessPieceMapProvider;
import edu.austral.dissis.chess.rule.CheckMate;
import edu.austral.dissis.chess.rule.DefaultCheck;
import edu.austral.dissis.chess.rule.GameRule;
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

public class PieceMovementTest {

  private final Map<Position, Piece> pieces = new ChessPieceMapProvider().provide(GameType.DEFAULT);
  private final Board board = new Board(pieces, new StandardTurnSelector());
  private final List<GameRule> rules =
      new ArrayList<>(
          List.of(
              new DefaultCheck(Color.BLACK),
              new DefaultCheck(Color.WHITE),
              new CheckMate(Color.BLACK),
              new CheckMate(Color.WHITE),
              new Stalemate()));
  private final ChessGame game = new ChessGame(board, rules);

  PieceMovementTest() {
    game.startGame();
  }

  @Test
  public void validateKnightMovement() throws UnallowedMoveException {
    Piece whiteLeftKnight = board.pieceAt(new Position(0, 1));
    assertEquals(whiteLeftKnight.getPieceColour(), Color.WHITE);
    game.makeMove(whiteLeftKnight, new Position(2, 0));
    assertEquals(new Position(2, 0), getPiecePosition(whiteLeftKnight, pieces));
    Piece blackPawn = board.pieceAt(new Position(6, 1));
    game.makeMove(blackPawn, new Position(4, 1));

    game.makeMove(whiteLeftKnight, new Position(4, 1));
    assertEquals(new Position(4, 1), getPiecePosition(whiteLeftKnight, pieces));
    assertFalse(blackPawn.isActiveInBoard());
  }

  @Test
  public void validateBishopMovement() throws UnallowedMoveException {
    // Initial validations and initializations
    Piece whiteBishop = board.pieceAt(new Position(0, 2));
    assertEquals(whiteBishop.getPieceColour(), Color.WHITE);
    Piece whitePawn = board.pieceAt(new Position(1, 3));
    assertEquals(whitePawn.getPieceColour(), Color.WHITE);
    Piece blackPawn = board.pieceAt(new Position(6, 2));
    assertEquals(blackPawn.getPieceColour(), Color.BLACK);
    // First movement
    game.makeMove(whitePawn, new Position(2, 3));
    assertEquals(new Position(2, 3), getPiecePosition(whitePawn, pieces));
    // Move black pawn
    game.makeMove(blackPawn, new Position(5, 2));
    assertEquals(new Position(5, 2), getPiecePosition(blackPawn, pieces));
    // Assert illegal bishop move
    assertThrows(
        UnallowedMoveException.class, () -> game.makeMove(whiteBishop, new Position(2, 3)));
    // Move bishop legally
    game.makeMove(whiteBishop, new Position(2, 4));
    assertEquals(new Position(2, 4), getPiecePosition(whiteBishop, pieces));
    // Put black pawn in a position where it can be taken
    game.makeMove(blackPawn, new Position(4, 2));
    game.makeMove(whiteBishop, new Position(4, 2));
    assertFalse(blackPawn.isActiveInBoard());
    assertEquals(31, pieces.size());
  }

  @Test
  public void validatePawnMovement() throws UnallowedMoveException {
    Piece whitePawn = board.pieceAt(new Position(1, 0));
    assertEquals(whitePawn.getPieceColour(), Color.WHITE);
    assertEquals(getPiecePosition(whitePawn, pieces), new Position(1, 0));
    game.makeMove(whitePawn, new Position(2, 0));
    assertEquals(new Position(2, 0), getPiecePosition(whitePawn, pieces));

    Piece otherWhitePawn = board.pieceAt(new Position(1, 1));
    assertEquals(otherWhitePawn.getPieceColour(), Color.WHITE);
    assertEquals(getPiecePosition(otherWhitePawn, pieces), new Position(1, 1));
    game.makeMove(otherWhitePawn, new Position(3, 1));
    assertNotEquals(new Position(3, 1), getPiecePosition(otherWhitePawn, pieces));

    Piece blackPawn = board.pieceAt(new Position(6, 0));
    assertEquals(blackPawn.getPieceColour(), Color.BLACK);
    assertEquals(getPiecePosition(blackPawn, pieces), new Position(6, 0));
    game.makeMove(blackPawn, new Position(4, 0));
    assertEquals(new Position(4, 0), getPiecePosition(blackPawn, pieces));

    game.makeMove(otherWhitePawn, new Position(3, 1));
    assertEquals(new Position(3, 1), getPiecePosition(otherWhitePawn, pieces));
    assertEquals(board.getCurrentTurn(), Color.BLACK);
    game.makeMove(blackPawn, new Position(3, 1));
    assertEquals(new Position(3, 1), getPiecePosition(blackPawn, pieces));
    assertFalse(otherWhitePawn.isActiveInBoard());
  }
}
