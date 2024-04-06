package edu.austral.dissis.chess.engine;

import static org.junit.jupiter.api.Assertions.*;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.rule.*;
import edu.austral.dissis.chess.provider.ChessPieceMapProvider;
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
  private final List<GameRule> rules =
      new ArrayList<>(List.of(new Check(), new CheckMate(), new Stalemate()));
  private final ChessGame game = new ChessGame(board, rules);

  ChessTest() {
    game.startGame();
  }

  // Tests
  @Test
  public void pawnShouldMoveOneOrTwoTilesOnFirstMove() {
    Piece whitePawn = pieces.get(new Position(1, 0));
    assertEquals(whitePawn.getPieceColour(), Color.WHITE);
    assertEquals(getPiecePosition(whitePawn), new Position(1, 0));
    game.makeMove(whitePawn, new Position(2, 0));
    assertEquals(new Position(2, 0), getPiecePosition(whitePawn));

    Piece otherWhitePawn = pieces.get(new Position(1, 1));
    assertEquals(otherWhitePawn.getPieceColour(), Color.WHITE);
    assertEquals(getPiecePosition(otherWhitePawn), new Position(1, 1));
    game.makeMove(otherWhitePawn, new Position(3, 1));
    assertNotEquals(new Position(3, 1), getPiecePosition(otherWhitePawn));

    Piece blackPawn = pieces.get(new Position(6, 0));
    assertEquals(blackPawn.getPieceColour(), Color.BLACK);
    assertEquals(getPiecePosition(blackPawn), new Position(6, 0));
    game.makeMove(blackPawn, new Position(4, 0));
    assertEquals(new Position(4, 0), getPiecePosition(blackPawn));

    game.makeMove(otherWhitePawn, new Position(3, 1));
    assertEquals(new Position(3, 1), getPiecePosition(otherWhitePawn));
    assertEquals(board.getCurrentTurn(), Color.BLACK);
    game.makeMove(blackPawn, new Position(3,1));
    assertEquals(new Position(3, 1), getPiecePosition(blackPawn));
    assertFalse(otherWhitePawn.isActiveInBoard());
  }

  @Test
  public void validateKnightMovement() {
    Piece whiteLeftKnight = pieces.get(new Position(0, 1));
    assertEquals(whiteLeftKnight.getPieceColour(), Color.WHITE);
    game.makeMove(whiteLeftKnight, new Position(2, 0));
    assertEquals(new Position(2, 0), getPiecePosition(whiteLeftKnight));
    Piece blackPawn = pieces.get(new Position(6, 1));
    game.makeMove(blackPawn, new Position(4, 1));

    game.makeMove(whiteLeftKnight, new Position(4, 1));
    assertEquals(new Position(4, 1), getPiecePosition(whiteLeftKnight));
    assertFalse(blackPawn.isActiveInBoard());
  }

  private Position getPiecePosition(Piece piece) {
    for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
      if (entry.getValue() == piece) return entry.getKey();
    }
    return null;
  }
}
