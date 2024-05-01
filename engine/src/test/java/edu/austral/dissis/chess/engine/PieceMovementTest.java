package edu.austral.dissis.chess.engine;

import static edu.austral.dissis.chess.engine.ChessTest.getPiecePosition;
import static edu.austral.dissis.chess.engine.updated.utils.TestFunctions.makeMove;
import static edu.austral.dissis.chess.utils.ChessMoveResult.INVALID_MOVE;
import static edu.austral.dissis.chess.utils.ChessPosition.fromAlgebraic;
import static java.awt.Color.WHITE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.piece.movement.Castling;
import edu.austral.dissis.chess.providers.GameProvider;
import edu.austral.dissis.chess.utils.ChessPosition;
import edu.austral.dissis.chess.utils.GameType;
import java.awt.Color;
import java.util.List;
import org.junit.jupiter.api.Test;

public class PieceMovementTest {
  // Movements based on the board added to utils

  private ChessGame game = new GameProvider().provide(GameType.DEFAULT);

  @Test
  public void validateKnightMovement() {
    Piece whiteLeftKnight = game.getBoard().pieceAt(fromAlgebraic("b1"));
    assertEquals(whiteLeftKnight.getPieceColour(), WHITE);
    game = makeMove(game, "b1 -> a3").game();
    assertEquals(PieceType.KNIGHT, game.getBoard().pieceAt(fromAlgebraic("a3")).getType());
    game = makeMove(game, "b7 -> b5").game();
    game = makeMove(game, "a3 -> b5").game();
    assertEquals(PieceType.KNIGHT, game.getBoard().pieceAt(fromAlgebraic("b5")).getType());
  }

  @Test
  public void validateBishopMovement() {
    // Initial validations and initializations
    Piece whiteBishop = game.getBoard().pieceAt(fromAlgebraic("c1"));
    assertEquals(whiteBishop.getPieceColour(), WHITE);
    assertEquals(whiteBishop.getType(), PieceType.BISHOP);

    Piece whitePawn = game.getBoard().pieceAt(fromAlgebraic("d2"));
    assertEquals(whitePawn.getPieceColour(), WHITE);
    assertEquals(whitePawn.getType(), PieceType.PAWN);

    Piece blackPawn = game.getBoard().pieceAt(fromAlgebraic("c7"));
    assertEquals(blackPawn.getPieceColour(), Color.BLACK);
    assertEquals(blackPawn.getType(), PieceType.PAWN);
    game = makeMove(game, "d2 -> d4").game();
    game = makeMove(game, "c7 -> c5").game();
    game = makeMove(game, "d4 -> c5").game();
    game = makeMove(game, "d7 -> d5").game();
    game = makeMove(game, "c1 -> e3").game();
    game = makeMove(game, "a7 -> a6").game();
    game = makeMove(game, "e3 -> c5").game();
    assertEquals(31, game.getBoard().getPiecesAndPositions().size());
  }

  @Test
  public void validatePawnMovement() {
    Piece whitePawn = game.getBoard().pieceAt(fromAlgebraic("a2"));
    assertEquals(whitePawn.getPieceColour(), WHITE);
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(fromAlgebraic("a2")).getType());
    game = makeMove(game, "a2 -> a3").game();
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(fromAlgebraic("a3")).getType());

    Piece blackPawn = game.getBoard().pieceAt(fromAlgebraic("a7"));
    assertEquals(blackPawn.getPieceColour(), Color.BLACK);
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(fromAlgebraic("a7")).getType());
    game = makeMove(game, "a7 -> a5").game();
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(fromAlgebraic("a5")).getType());

    Piece otherWhitePawn = game.getBoard().pieceAt(fromAlgebraic("b2"));
    assertEquals(otherWhitePawn.getPieceColour(), WHITE);
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(fromAlgebraic("b2")).getType());

    game = makeMove(game, "b2 -> b4").game();
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(fromAlgebraic("b4")).getType());
    assertEquals(Color.BLACK, game.getCurrentTurn());
    game = makeMove(game, "a5 -> b4").game();
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(fromAlgebraic("b4")).getType());

    Piece newWhitePawn = game.getBoard().pieceAt(fromAlgebraic("e2"));
    assertEquals(newWhitePawn.getPieceColour(), WHITE);
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(fromAlgebraic("e2")).getType());
    assertEquals(
        getPiecePosition(newWhitePawn, game.getBoard().getPiecesAndPositions()),
        fromAlgebraic("e2"));
    List<ChessPosition> pawnMoveSet = newWhitePawn.getMoveSet(fromAlgebraic("e2"), game.getBoard());
    assertEquals(pawnMoveSet.size(), 2);
  }

  @Test
  public void validatePawnTaking() {
    game = makeMove(game, "b2 -> b4").game();
    game = makeMove(game, "a7 -> a5").game();
    game = makeMove(game, "b4 -> b5").game();
    assertEquals(INVALID_MOVE, makeMove(game, "b7 -> b5").moveResult());
  }

  @Test
  public void validateRookMovement() {
    game = makeMove(game, "a2 -> a4").game();
    game = makeMove(game, "b7 -> b5").game();
    game = makeMove(game, "a4 -> b5").game();
    assertEquals(Color.BLACK, game.getCurrentTurn());
    assertEquals(INVALID_MOVE, makeMove(game, "a8 -> b4").moveResult());
    assertEquals(31, game.getBoard().getPiecesAndPositions().size());
    game = makeMove(game, "g7 -> g6").game();
    List<ChessPosition> rookMoveSet =
        game.getBoard()
            .pieceAt(fromAlgebraic("a1"))
            .getMoveSet(fromAlgebraic("a1"), game.getBoard());
    assertEquals(6, rookMoveSet.size());
    game = makeMove(game, "a1 -> a5").game();
    assertEquals(PieceType.ROOK, game.getBoard().pieceAt(fromAlgebraic("a5")).getType());
  }

  @Test
  public void validateQueenMovement() {
    Piece whiteQueen = game.getBoard().pieceAt(fromAlgebraic("d1"));
    assertEquals(whiteQueen.getType(), PieceType.QUEEN);
    game = makeMove(game, "d2 -> d4").game();
    List<ChessPosition> whiteQueenMoveSet =
        game.getBoard()
            .pieceAt(fromAlgebraic("d1"))
            .getMoveSet(fromAlgebraic("d1"), game.getBoard());
    assertEquals(2, whiteQueenMoveSet.size());
    game = makeMove(game, "e7 -> e5").game();
    game = makeMove(game, "d4 -> e5").game();
    game = makeMove(game, "d7 -> d6").game();
    assertEquals(31, game.getBoard().getPiecesAndPositions().size());
    whiteQueenMoveSet =
        game.getBoard()
            .pieceAt(fromAlgebraic("d1"))
            .getMoveSet(fromAlgebraic("d1"), game.getBoard());
    assertEquals(5, whiteQueenMoveSet.size());
  }

  @Test
  public void assertLeftCastling() {
    game = makeMove(game, "b2 -> b4").game();
    assertEquals(Color.BLACK, game.getCurrentTurn());
    game = makeMove(game, "b7 -> b5").game();
    assertEquals(WHITE, game.getCurrentTurn());
    game = makeMove(game, "c2 -> c4").game();
    assertEquals(Color.BLACK, game.getCurrentTurn());
    game = makeMove(game, "c7 -> c5").game();
    assertEquals(WHITE, game.getCurrentTurn());
    game = makeMove(game, "d2 -> d4").game();
    assertEquals(Color.BLACK, game.getCurrentTurn());
    game = makeMove(game, "d7 -> d5").game();
    assertEquals(WHITE, game.getCurrentTurn());
    game = makeMove(game, "b1 -> c3").game();
    assertEquals(Color.BLACK, game.getCurrentTurn());
    game = makeMove(game, "g7 -> g6").game();
    assertEquals(WHITE, game.getCurrentTurn());
    game = makeMove(game, "c1 -> a3").game();
    game = makeMove(game, "a7 -> a5").game();
    game = makeMove(game, "d1 -> d2").game();
    game = makeMove(game, "f7 -> f5").game();
    assertTrue(
        new Castling().isValidMove(fromAlgebraic("e1"), fromAlgebraic("c1"), game.getBoard()));
    game = makeMove(game, "e1 -> c1").game();
    assertEquals(PieceType.KING, game.getBoard().pieceAt(fromAlgebraic("c1")).getType());
    assertEquals(PieceType.ROOK, game.getBoard().pieceAt(fromAlgebraic("d1")).getType());
  }

  @Test
  public void assertRightCastling() {
    game = makeMove(game, "e2 -> e4").game();
    assertEquals(Color.BLACK, game.getCurrentTurn());
    game = makeMove(game, "b7 -> b5").game();
    assertEquals(WHITE, game.getCurrentTurn());
    game = makeMove(game, "g2 -> g4").game();
    assertEquals(Color.BLACK, game.getCurrentTurn());
    game = makeMove(game, "c7 -> c5").game();
    assertEquals(WHITE, game.getCurrentTurn());
    game = makeMove(game, "h2 -> h4").game();
    assertEquals(Color.BLACK, game.getCurrentTurn());
    game = makeMove(game, "d7 -> d5").game();
    assertEquals(WHITE, game.getCurrentTurn());
    game = makeMove(game, "g1 -> f3").game();
    assertEquals(Color.BLACK, game.getCurrentTurn());
    game = makeMove(game, "g7 -> g6").game();
    assertEquals(WHITE, game.getCurrentTurn());
    game = makeMove(game, "f1 -> g2").game();
    game = makeMove(game, "a7 -> a5").game();
    assertTrue(
        new Castling().isValidMove(fromAlgebraic("e1"), fromAlgebraic("g1"), game.getBoard()));
    game = makeMove(game, "e1 -> g1").game();
    assertEquals(PieceType.KING, game.getBoard().pieceAt(fromAlgebraic("g1")).getType());
    assertEquals(PieceType.ROOK, game.getBoard().pieceAt(fromAlgebraic("f1")).getType());
  }
}
