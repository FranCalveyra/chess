package edu.austral.dissis.chess.engine;

import static edu.austral.dissis.chess.engine.updated.utils.TestFunctions.makeMove;
import static edu.austral.dissis.chess.utils.ChessMoveResult.BLACK_WIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.providers.GameProvider;
import edu.austral.dissis.chess.rules.CheckMate;
import edu.austral.dissis.chess.rules.DefaultCheck;
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.utils.ChessPosition;
import edu.austral.dissis.chess.utils.GameResult;
import edu.austral.dissis.chess.utils.GameType;
import java.awt.Color;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ChessTest {
  // Setup

  private ChessGame game =
      new GameProvider().provide(GameType.DEFAULT);

  // Tests
  @Test
  public void foolsMate() {
    final Piece blackPawn = game.getBoard().pieceAt(new ChessPosition(6, 4));
    final Piece whitePawn = game.getBoard().pieceAt(new ChessPosition(1, 5));
    final Piece whitePawn2 =game.getBoard().pieceAt(new ChessPosition(1, 6));
    final Piece blackQueen =game.getBoard().pieceAt(new ChessPosition(7, 3));
    assertEquals(blackPawn.getPieceColour(), Color.BLACK);
    assertEquals(whitePawn.getPieceColour(), Color.WHITE);
    assertEquals(whitePawn2.getPieceColour(), Color.WHITE);
    assertEquals(blackQueen.getPieceColour(), Color.BLACK);
    assertEquals(PieceType.PAWN, blackPawn.getType());
    assertEquals(PieceType.PAWN, whitePawn.getType());
    assertEquals(PieceType.PAWN, whitePawn2.getType());
    assertEquals(PieceType.QUEEN, blackQueen.getType());
    game = makeMove(game, "f2 -> f3").game();
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new ChessPosition(2, 5)).getType());
    game = makeMove(game, "e7 -> e5").game();
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new ChessPosition(4, 4)).getType());
    game = makeMove(game, "g2 -> g4").game();
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new ChessPosition(3, 6)).getType());
    GameResult finishingMove = makeMove(game, "d8 -> h4");
    game = finishingMove.game();
    assertEquals(BLACK_WIN, finishingMove.message());
    assertEquals(PieceType.QUEEN, game.getBoard().pieceAt(new ChessPosition(3, 7)).getType());
    assertTrue(new DefaultCheck(Color.WHITE).isValidRule(game.getBoard()));
    assertFalse(new DefaultCheck(Color.BLACK).isValidRule(game.getBoard()));
    System.out.println(game.getBoard());
    assertTrue(new CheckMate(Color.WHITE).isValidRule(game.getBoard()));
  }

  @Test
  public void validatePromotion() {
    assertEquals(Color.WHITE, game.getCurrentTurn());
    game = makeMove(game, "a2 -> a4").game();
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
