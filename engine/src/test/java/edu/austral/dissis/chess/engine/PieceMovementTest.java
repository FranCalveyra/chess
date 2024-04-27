package edu.austral.dissis.chess.engine;

import static edu.austral.dissis.chess.engine.ChessTest.getPiecePosition;
import static edu.austral.dissis.chess.utils.ChessMoveResult.INVALID_MOVE;
import static java.awt.Color.WHITE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.piece.movement.Castling;
import edu.austral.dissis.chess.promoters.StandardPromoter;
import edu.austral.dissis.chess.providers.ChessPieceMapProvider;
import edu.austral.dissis.chess.rules.Check;
import edu.austral.dissis.chess.rules.CheckMate;
import edu.austral.dissis.chess.rules.DefaultCheck;
import edu.austral.dissis.chess.rules.WinCondition;
import edu.austral.dissis.chess.selectors.StandardTurnSelector;
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.utils.ChessPosition;
import edu.austral.dissis.chess.utils.GameType;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class PieceMovementTest {

  private final Map<ChessPosition, Piece> pieces =
      new ChessPieceMapProvider().provide(GameType.DEFAULT);
  private final Board board = new Board(pieces);
  private final List<WinCondition> rules =
      new ArrayList<>(List.of(new CheckMate(Color.BLACK), new CheckMate(WHITE)));
  private final List<Check> checks =
      List.of(new DefaultCheck(Color.BLACK), new DefaultCheck(WHITE));
  private ChessGame game =
      ChessGame.createChessGame(
          board, rules, checks, new StandardPromoter(), new StandardTurnSelector(), WHITE, 0);

  @Test
  public void validateKnightMovement() {
    Piece whiteLeftKnight = board.pieceAt(new ChessPosition(0, 1));
    assertEquals(whiteLeftKnight.getPieceColour(), WHITE);
    game = game.makeMove(new ChessMove(new ChessPosition(0, 1), new ChessPosition(2, 0))).game();
    assertEquals(PieceType.KNIGHT, game.getBoard().pieceAt(new ChessPosition(2, 0)).getType());
    game = game.makeMove(new ChessMove(new ChessPosition(6, 1), new ChessPosition(4, 1))).game();

    game = game.makeMove(new ChessMove(new ChessPosition(2, 0), new ChessPosition(4, 1))).game();
    assertEquals(PieceType.KNIGHT, game.getBoard().pieceAt(new ChessPosition(4, 1)).getType());
  }

  @Test
  public void validateBishopMovement() {
    // Initial validations and initializations
    Piece whiteBishop = board.pieceAt(new ChessPosition(0, 2));
    assertEquals(whiteBishop.getPieceColour(), WHITE);
    assertEquals(whiteBishop.getType(), PieceType.BISHOP);

    Piece whitePawn = board.pieceAt(new ChessPosition(1, 3));
    assertEquals(whitePawn.getPieceColour(), WHITE);
    assertEquals(whitePawn.getType(), PieceType.PAWN);
    Piece blackPawn = board.pieceAt(new ChessPosition(6, 2));
    assertEquals(blackPawn.getPieceColour(), Color.BLACK);
    assertEquals(blackPawn.getType(), PieceType.PAWN);
    // First movement
    game = game.makeMove(new ChessMove(new ChessPosition(1, 3), new ChessPosition(3, 3))).game();
    game = game.makeMove(new ChessMove(new ChessPosition(6, 2), new ChessPosition(4, 2))).game();
    game = game.makeMove(new ChessMove(new ChessPosition(3, 3), new ChessPosition(4, 2))).game();
    game = game.makeMove(new ChessMove(new ChessPosition(0, 2), new ChessPosition(2, 4))).game();
    game = game.makeMove(new ChessMove(new ChessPosition(6, 0), new ChessPosition(5, 0))).game();
    game = game.makeMove(new ChessMove(new ChessPosition(2, 4), new ChessPosition(4, 2))).game();
    assertEquals(31, game.getBoard().getPiecesAndPositions().size());
  }

  @Test
  public void validatePawnMovement() {
    Piece whitePawn = game.getBoard().pieceAt(new ChessPosition(1, 0));
    assertEquals(whitePawn.getPieceColour(), WHITE);
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new ChessPosition(1, 0)).getType());
    game = game.makeMove(new ChessMove(new ChessPosition(1, 0), new ChessPosition(2, 0))).game();
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new ChessPosition(2, 0)).getType());

    Piece blackPawn = game.getBoard().pieceAt(new ChessPosition(6, 0));
    assertEquals(blackPawn.getPieceColour(), Color.BLACK);
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new ChessPosition(6, 0)).getType());
    game = game.makeMove(new ChessMove(new ChessPosition(6, 0), new ChessPosition(4, 0))).game();
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new ChessPosition(4, 0)).getType());

    Piece otherWhitePawn = game.getBoard().pieceAt(new ChessPosition(1, 1));
    assertEquals(otherWhitePawn.getPieceColour(), WHITE);
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new ChessPosition(1, 1)).getType());

    game = game.makeMove(new ChessMove(new ChessPosition(1, 1), new ChessPosition(3, 1))).game();
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new ChessPosition(3, 1)).getType());
    assertEquals(Color.BLACK, game.getCurrentTurn());
    game = game.makeMove(new ChessMove(new ChessPosition(4, 0), new ChessPosition(3, 1))).game();
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new ChessPosition(3, 1)).getType());

    Piece newWhitePawn = game.getBoard().pieceAt(new ChessPosition(1, 4));
    assertEquals(newWhitePawn.getPieceColour(), WHITE);
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new ChessPosition(1, 4)).getType());
    assertEquals(
        getPiecePosition(newWhitePawn, game.getBoard().getPiecesAndPositions()),
        new ChessPosition(1, 4));
    List<ChessPosition> pawnMoveSet =
        newWhitePawn.getMoveSet(new ChessPosition(1, 4), game.getBoard());
    assertEquals(pawnMoveSet.size(), 2);
  }

  @Test
  public void validateRookMovement() {
    game = game.makeMove(new ChessMove(new ChessPosition(1, 0), new ChessPosition(3, 0))).game();
    game = game.makeMove(new ChessMove(new ChessPosition(6, 1), new ChessPosition(4, 1))).game();
    game = game.makeMove(new ChessMove(new ChessPosition(3, 0), new ChessPosition(4, 1))).game();
    assertEquals(Color.BLACK, game.getCurrentTurn());
    assertEquals(
        INVALID_MOVE,
        game.makeMove(new ChessMove(new ChessPosition(7, 7), new ChessPosition(3, 1))).message());
    assertEquals(31, game.getBoard().getPiecesAndPositions().size());
    game = game.makeMove(new ChessMove(new ChessPosition(6, 6), new ChessPosition(5, 6))).game();
    List<ChessPosition> rookMoveSet =
        game.getBoard()
            .pieceAt(new ChessPosition(0, 0))
            .getMoveSet(new ChessPosition(0, 0), game.getBoard());
    assertEquals(5, rookMoveSet.size());
    game = game.makeMove(new ChessMove(new ChessPosition(0, 0), new ChessPosition(4, 0))).game();
    assertEquals(PieceType.ROOK, game.getBoard().pieceAt(new ChessPosition(4, 0)).getType());
  }

  @Test
  public void validateQueenMovement() {
    Piece whiteQueen = board.pieceAt(new ChessPosition(0, 3));
    assertEquals(whiteQueen.getType(), PieceType.QUEEN);
    game = game.makeMove(new ChessMove(new ChessPosition(1, 3), new ChessPosition(3, 3))).game();
    List<ChessPosition> whiteQueenMoveSet =
        game.getBoard()
            .pieceAt(new ChessPosition(0, 3))
            .getMoveSet(new ChessPosition(0, 3), game.getBoard());
    assertEquals(2, whiteQueenMoveSet.size()); // Need to fix it
    game = game.makeMove(new ChessMove(new ChessPosition(6, 4), new ChessPosition(4, 4))).game();
    game = game.makeMove(new ChessMove(new ChessPosition(3, 3), new ChessPosition(4, 4))).game();
    game = game.makeMove(new ChessMove(new ChessPosition(6, 3), new ChessPosition(5, 3))).game();
    assertEquals(31, game.getBoard().getPiecesAndPositions().size());
    whiteQueenMoveSet =
        game.getBoard()
            .pieceAt(new ChessPosition(0, 3))
            .getMoveSet(new ChessPosition(0, 3), game.getBoard());
    assertEquals(4, whiteQueenMoveSet.size());
  }

  @Test
  public void assertLeftCastling() {
    game = game.makeMove(new ChessMove(new ChessPosition(1, 1), new ChessPosition(3, 1))).game();
    assertEquals(Color.BLACK, game.getCurrentTurn());
    game = game.makeMove(new ChessMove(new ChessPosition(6, 1), new ChessPosition(4, 1))).game();
    assertEquals(WHITE, game.getCurrentTurn());
    game = game.makeMove(new ChessMove(new ChessPosition(1, 2), new ChessPosition(3, 2))).game();
    assertEquals(Color.BLACK, game.getCurrentTurn());
    game = game.makeMove(new ChessMove(new ChessPosition(6, 2), new ChessPosition(4, 2))).game();
    assertEquals(WHITE, game.getCurrentTurn());
    game = game.makeMove(new ChessMove(new ChessPosition(1, 3), new ChessPosition(3, 3))).game();
    assertEquals(Color.BLACK, game.getCurrentTurn());
    game = game.makeMove(new ChessMove(new ChessPosition(6, 3), new ChessPosition(4, 3))).game();
    assertEquals(WHITE, game.getCurrentTurn());
    game = game.makeMove(new ChessMove(new ChessPosition(0, 1), new ChessPosition(2, 2))).game();
    assertEquals(Color.BLACK, game.getCurrentTurn());
    game = game.makeMove(new ChessMove(new ChessPosition(6, 6), new ChessPosition(5, 6))).game();
    assertEquals(WHITE, game.getCurrentTurn());
    game = game.makeMove(new ChessMove(new ChessPosition(0, 2), new ChessPosition(2, 0))).game();
    game = game.makeMove(new ChessMove(new ChessPosition(6, 0), new ChessPosition(4, 0))).game();
    game = game.makeMove(new ChessMove(new ChessPosition(0, 3), new ChessPosition(1, 3))).game();
    game = game.makeMove(new ChessMove(new ChessPosition(6, 5), new ChessPosition(4, 5))).game();
    assertTrue(
        new Castling()
            .isValidMove(new ChessPosition(0, 4), new ChessPosition(0, 2), game.getBoard()));
    game = game.makeMove(new ChessMove(new ChessPosition(0, 4), new ChessPosition(0, 2))).game();
    System.out.println(game.getBoard());
    assertEquals(PieceType.KING, game.getBoard().pieceAt(new ChessPosition(0, 2)).getType());
    assertEquals(PieceType.ROOK, game.getBoard().pieceAt(new ChessPosition(0, 3)).getType());
  }

  @Test
  public void assertRightCastling() {
    game = game.makeMove(new ChessMove(new ChessPosition(1, 5), new ChessPosition(3, 5))).game();
    assertEquals(Color.BLACK, game.getCurrentTurn());
    game = game.makeMove(new ChessMove(new ChessPosition(6, 1), new ChessPosition(4, 1))).game();
    assertEquals(WHITE, game.getCurrentTurn());
    game = game.makeMove(new ChessMove(new ChessPosition(1, 6), new ChessPosition(3, 6))).game();
    assertEquals(Color.BLACK, game.getCurrentTurn());
    game = game.makeMove(new ChessMove(new ChessPosition(6, 2), new ChessPosition(4, 2))).game();
    assertEquals(WHITE, game.getCurrentTurn());
    game = game.makeMove(new ChessMove(new ChessPosition(1, 7), new ChessPosition(3, 7))).game();
    assertEquals(Color.BLACK, game.getCurrentTurn());
    game = game.makeMove(new ChessMove(new ChessPosition(6, 3), new ChessPosition(4, 3))).game();
    assertEquals(WHITE, game.getCurrentTurn());
    game = game.makeMove(new ChessMove(new ChessPosition(0, 6), new ChessPosition(2, 5))).game();
    assertEquals(Color.BLACK, game.getCurrentTurn());
    game = game.makeMove(new ChessMove(new ChessPosition(6, 6), new ChessPosition(5, 6))).game();
    assertEquals(WHITE, game.getCurrentTurn());
    game = game.makeMove(new ChessMove(new ChessPosition(0, 6), new ChessPosition(2, 7))).game();
    game = game.makeMove(new ChessMove(new ChessPosition(6, 0), new ChessPosition(4, 0))).game();
    game = game.makeMove(new ChessMove(new ChessPosition(0, 5), new ChessPosition(1, 6))).game();
    game = game.makeMove(new ChessMove(new ChessPosition(4, 1), new ChessPosition(3, 1))).game();
    assertTrue(
        new Castling()
            .isValidMove(new ChessPosition(0, 4), new ChessPosition(0, 6), game.getBoard()));
    System.out.println(game.getBoard());
    game = game.makeMove(new ChessMove(new ChessPosition(0, 4), new ChessPosition(0, 6))).game();
    assertEquals(PieceType.KING, game.getBoard().pieceAt(new ChessPosition(0, 6)).getType());
    assertEquals(PieceType.ROOK, game.getBoard().pieceAt(new ChessPosition(0, 5)).getType());
  }
}
