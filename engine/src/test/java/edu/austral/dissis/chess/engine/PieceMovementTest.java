package edu.austral.dissis.chess.engine;

import static edu.austral.dissis.chess.engine.ChessTest.getPiecePosition;
import static edu.austral.dissis.chess.utils.MoveResult.INVALID_MOVE;
import static java.awt.Color.WHITE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.piece.movement.Castling;
import edu.austral.dissis.chess.promoter.StandardPromoter;
import edu.austral.dissis.chess.provider.ChessPieceMapProvider;
import edu.austral.dissis.chess.rules.Check;
import edu.austral.dissis.chess.rules.CheckMate;
import edu.austral.dissis.chess.rules.DefaultCheck;
import edu.austral.dissis.chess.rules.WinCondition;
import edu.austral.dissis.chess.turn.StandardTurnSelector;
import edu.austral.dissis.chess.utils.GameType;
import edu.austral.dissis.chess.utils.Position;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class PieceMovementTest {

  private final Map<Position, Piece> pieces = new ChessPieceMapProvider().provide(GameType.DEFAULT);
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
    Piece whiteLeftKnight = board.pieceAt(new Position(0, 1));
    assertEquals(whiteLeftKnight.getPieceColour(), WHITE);
    game = game.makeMove(new Position(0, 1), new Position(2, 0)).game();
    assertEquals(PieceType.KNIGHT, game.getBoard().pieceAt(new Position(2, 0)).getType());
    game = game.makeMove(new Position(6, 1), new Position(4, 1)).game();

    game = game.makeMove(new Position(2, 0), new Position(4, 1)).game();
    assertEquals(PieceType.KNIGHT, game.getBoard().pieceAt(new Position(4, 1)).getType());
  }

  @Test
  public void validateBishopMovement() {
    // Initial validations and initializations
    Piece whiteBishop = board.pieceAt(new Position(0, 2));
    assertEquals(whiteBishop.getPieceColour(), WHITE);
    assertEquals(whiteBishop.getType(), PieceType.BISHOP);

    Piece whitePawn = board.pieceAt(new Position(1, 3));
    assertEquals(whitePawn.getPieceColour(), WHITE);
    assertEquals(whitePawn.getType(), PieceType.PAWN);
    Piece blackPawn = board.pieceAt(new Position(6, 2));
    assertEquals(blackPawn.getPieceColour(), Color.BLACK);
    assertEquals(blackPawn.getType(), PieceType.PAWN);
    // First movement
    game = game.makeMove(new Position(1, 3), new Position(3, 3)).game();
    game = game.makeMove(new Position(6, 2), new Position(4, 2)).game();
    game = game.makeMove(new Position(3, 3), new Position(4, 2)).game();
    game = game.makeMove(new Position(0, 2), new Position(2, 4)).game();
    game = game.makeMove(new Position(6, 0), new Position(5, 0)).game();
    game = game.makeMove(new Position(2, 4), new Position(4, 2)).game();
    assertEquals(31, game.getBoard().getPiecesAndPositions().size());
  }

  @Test
  public void validatePawnMovement() {
    Piece whitePawn = game.getBoard().pieceAt(new Position(1, 0));
    assertEquals(whitePawn.getPieceColour(), WHITE);
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new Position(1, 0)).getType());
    game = game.makeMove(new Position(1, 0), new Position(2, 0)).game();
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new Position(2, 0)).getType());

    Piece blackPawn = game.getBoard().pieceAt(new Position(6, 0));
    assertEquals(blackPawn.getPieceColour(), Color.BLACK);
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new Position(6, 0)).getType());
    game = game.makeMove(new Position(6, 0), new Position(4, 0)).game();
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new Position(4, 0)).getType());

    Piece otherWhitePawn = game.getBoard().pieceAt(new Position(1, 1));
    assertEquals(otherWhitePawn.getPieceColour(), WHITE);
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new Position(1, 1)).getType());

    game = game.makeMove(new Position(1, 1), new Position(3, 1)).game();
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new Position(3, 1)).getType());
    assertEquals(Color.BLACK, game.getCurrentTurn());
    game = game.makeMove(new Position(4, 0), new Position(3, 1)).game();
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new Position(3, 1)).getType());

    Piece newWhitePawn = game.getBoard().pieceAt(new Position(1, 4));
    assertEquals(newWhitePawn.getPieceColour(), WHITE);
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new Position(1, 4)).getType());
    assertEquals(
        getPiecePosition(newWhitePawn, game.getBoard().getPiecesAndPositions()),
        new Position(1, 4));
    List<Position> pawnMoveSet = newWhitePawn.getMoveSet(new Position(1, 4), game.getBoard());
    assertEquals(pawnMoveSet.size(), 2);
  }

  @Test
  public void validateRookMovement() {
    game = game.makeMove(new Position(1, 0), new Position(3, 0)).game();
    game = game.makeMove(new Position(6, 1), new Position(4, 1)).game();
    game = game.makeMove(new Position(3, 0), new Position(4, 1)).game();
    assertEquals(Color.BLACK, game.getCurrentTurn());
    assertEquals(INVALID_MOVE, game.makeMove(new Position(7, 7), new Position(3, 1)).message());
    assertEquals(31, game.getBoard().getPiecesAndPositions().size());
    game = game.makeMove(new Position(6, 6), new Position(5, 6)).game();
    List<Position> rookMoveSet =
        game.getBoard().pieceAt(new Position(0, 0)).getMoveSet(new Position(0, 0), game.getBoard());
    assertEquals(5, rookMoveSet.size());
    game = game.makeMove(new Position(0, 0), new Position(4, 0)).game();
    assertEquals(PieceType.ROOK, game.getBoard().pieceAt(new Position(4, 0)).getType());
  }

  @Test
  public void validateQueenMovement() {
    Piece whiteQueen = board.pieceAt(new Position(0, 3));
    assertEquals(whiteQueen.getType(), PieceType.QUEEN);
    game = game.makeMove(new Position(1, 3), new Position(3, 3)).game();
    List<Position> whiteQueenMoveSet =
        game.getBoard().pieceAt(new Position(0, 3)).getMoveSet(new Position(0, 3), game.getBoard());
    assertEquals(2, whiteQueenMoveSet.size()); // Need to fix it
    game = game.makeMove(new Position(6, 4), new Position(4, 4)).game();
    game = game.makeMove(new Position(3, 3), new Position(4, 4)).game();
    game = game.makeMove(new Position(6, 3), new Position(5, 3)).game();
    assertEquals(31, game.getBoard().getPiecesAndPositions().size());
    whiteQueenMoveSet =
        game.getBoard().pieceAt(new Position(0, 3)).getMoveSet(new Position(0, 3), game.getBoard());
    assertEquals(4, whiteQueenMoveSet.size());
  }

  @Test
  public void validateRightCastling() {
    assertRightCastling();
  }

  @Test
  public void validateLeftCastling() {
    assertLeftCastling();
  }

  private void assertLeftCastling() {
    game = game.makeMove(new Position(1, 1), new Position(3, 1)).game();
    assertEquals(Color.BLACK, game.getCurrentTurn());
    game = game.makeMove(new Position(6, 1), new Position(4, 1)).game();
    assertEquals(WHITE, game.getCurrentTurn());
    game = game.makeMove(new Position(1, 2), new Position(3, 2)).game();
    assertEquals(Color.BLACK, game.getCurrentTurn());
    game = game.makeMove(new Position(6, 2), new Position(4, 2)).game();
    assertEquals(WHITE, game.getCurrentTurn());
    game = game.makeMove(new Position(1, 3), new Position(3, 3)).game();
    assertEquals(Color.BLACK, game.getCurrentTurn());
    game = game.makeMove(new Position(6, 3), new Position(4, 3)).game();
    assertEquals(WHITE, game.getCurrentTurn());
    game = game.makeMove(new Position(0, 1), new Position(2, 2)).game();
    assertEquals(Color.BLACK, game.getCurrentTurn());
    game = game.makeMove(new Position(6, 6), new Position(5, 6)).game();
    assertEquals(WHITE, game.getCurrentTurn());
    game = game.makeMove(new Position(0, 2), new Position(2, 0)).game();
    game = game.makeMove(new Position(6, 0), new Position(4, 0)).game();
    game = game.makeMove(new Position(0, 3), new Position(1, 3)).game();
    game = game.makeMove(new Position(6, 5), new Position(4, 5)).game();
    assertTrue(new Castling().isValidMove(new Position(0, 0), new Position(0, 4), game.getBoard()));
  }

  private void assertRightCastling() {
    game = game.makeMove(new Position(1, 5), new Position(3, 5)).game();
    assertEquals(Color.BLACK, game.getCurrentTurn());
    game = game.makeMove(new Position(6, 1), new Position(4, 1)).game();
    assertEquals(WHITE, game.getCurrentTurn());
    game = game.makeMove(new Position(1, 6), new Position(3, 6)).game();
    assertEquals(Color.BLACK, game.getCurrentTurn());
    game = game.makeMove(new Position(6, 2), new Position(4, 2)).game();
    assertEquals(WHITE, game.getCurrentTurn());
    game = game.makeMove(new Position(1, 7), new Position(3, 7)).game();
    assertEquals(Color.BLACK, game.getCurrentTurn());
    game = game.makeMove(new Position(6, 3), new Position(4, 3)).game();
    assertEquals(WHITE, game.getCurrentTurn());
    game = game.makeMove(new Position(0, 6), new Position(2, 5)).game();
    assertEquals(Color.BLACK, game.getCurrentTurn());
    game = game.makeMove(new Position(6, 6), new Position(5, 6)).game();
    assertEquals(WHITE, game.getCurrentTurn());
    game = game.makeMove(new Position(0, 6), new Position(2, 7)).game();
    game = game.makeMove(new Position(6, 0), new Position(4, 0)).game();
    game = game.makeMove(new Position(0, 5), new Position(1, 6)).game();
    assertTrue(new Castling().isValidMove(new Position(0, 4), new Position(0, 7), game.getBoard()));
    // TODO: Implement Castling movement in the game, may need to create a Move class
  }
}
