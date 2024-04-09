package edu.austral.dissis.chess.engine;

import static edu.austral.dissis.chess.engine.ChessTest.getPiecePosition;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.piece.movement.Castling;
import edu.austral.dissis.chess.promoter.StandardPromoter;
import edu.austral.dissis.chess.provider.ChessPieceMapProvider;
import edu.austral.dissis.chess.rule.CheckMate;
import edu.austral.dissis.chess.rule.DefaultCheck;
import edu.austral.dissis.chess.rule.Stalemate;
import edu.austral.dissis.chess.rule.WinCondition;
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
  private final Board board = new Board(pieces, new StandardTurnSelector(), new StandardPromoter());
  private final List<WinCondition> rules =
      new ArrayList<>(
          List.of(
              new DefaultCheck(Color.BLACK),
              new DefaultCheck(Color.WHITE),
              new CheckMate(Color.BLACK),
              new CheckMate(Color.WHITE),
              new Stalemate()));
  private ChessGame game = new ChessGame(board, rules);

  PieceMovementTest() {
    game = game.startGame();
  }

  @Test
  public void validateKnightMovement() throws UnallowedMoveException {
    Piece whiteLeftKnight = board.pieceAt(new Position(0, 1));
    assertEquals(whiteLeftKnight.getPieceColour(), Color.WHITE);
    game = game.makeMove(new Position(0, 1), new Position(2, 0));
    assertEquals(PieceType.KNIGHT, game.getBoard().pieceAt(new Position(2, 0)).getType());
    game = game.makeMove(new Position(6, 1), new Position(4, 1));

    game = game.makeMove(new Position(2, 0), new Position(4, 1));
    assertEquals(PieceType.KNIGHT, game.getBoard().pieceAt(new Position(4, 1)).getType());
  }

  @Test
  public void validateBishopMovement() throws UnallowedMoveException {
    // Initial validations and initializations
    Piece whiteBishop = board.pieceAt(new Position(0, 2));
    assertEquals(whiteBishop.getPieceColour(), Color.WHITE);
    assertEquals(whiteBishop.getType(), PieceType.BISHOP);

    Piece whitePawn = board.pieceAt(new Position(1, 3));
    assertEquals(whitePawn.getPieceColour(), Color.WHITE);
    assertEquals(whitePawn.getType(), PieceType.PAWN);
    Piece blackPawn = board.pieceAt(new Position(6, 2));
    assertEquals(blackPawn.getPieceColour(), Color.BLACK);
    assertEquals(blackPawn.getType(), PieceType.PAWN);
    // First movement
    game = game.makeMove(new Position(1, 3), new Position(3, 3));
    game = game.makeMove(new Position(6, 2), new Position(4, 2));
    game = game.makeMove(new Position(3, 3), new Position(4, 2));
    game = game.makeMove(new Position(0, 2), new Position(2, 4));
    game = game.makeMove(new Position(6, 0), new Position(5, 0));
    game = game.makeMove(new Position(2, 4), new Position(4, 2));
    assertEquals(31, game.getBoard().getPiecesAndPositions().size());
  }

  @Test
  public void validatePawnMovement() throws UnallowedMoveException {
    Piece whitePawn = game.getBoard().pieceAt(new Position(1, 0));
    assertEquals(whitePawn.getPieceColour(), Color.WHITE);
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new Position(1, 0)).getType());
    game = game.makeMove(new Position(1, 0), new Position(2, 0));
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new Position(2, 0)).getType());

    Piece blackPawn = game.getBoard().pieceAt(new Position(6, 0));
    assertEquals(blackPawn.getPieceColour(), Color.BLACK);
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new Position(6, 0)).getType());
    game = game.makeMove(new Position(6, 0), new Position(4, 0));
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new Position(4, 0)).getType());

    Piece otherWhitePawn = game.getBoard().pieceAt(new Position(1, 1));
    assertEquals(otherWhitePawn.getPieceColour(), Color.WHITE);
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new Position(1, 1)).getType());

    game = game.makeMove(new Position(1, 1), new Position(3, 1));
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new Position(3, 1)).getType());
    assertEquals(Color.BLACK, game.getBoard().getCurrentTurn());
    game = game.makeMove(new Position(4, 0), new Position(3, 1));
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new Position(3, 1)).getType());

    Piece newWhitePawn = game.getBoard().pieceAt(new Position(1, 4));
    assertEquals(newWhitePawn.getPieceColour(), Color.WHITE);
    assertEquals(PieceType.PAWN, game.getBoard().pieceAt(new Position(1, 4)).getType());
    assertEquals(
        getPiecePosition(newWhitePawn, game.getBoard().getPiecesAndPositions()),
        new Position(1, 4));
    List<Position> pawnMoveSet = newWhitePawn.getMoveSet(new Position(1, 4), game.getBoard());
    assertEquals(pawnMoveSet.size(), 2);
  }

  @Test
  public void validateRookMovement() throws UnallowedMoveException {
    game = game.makeMove(new Position(1, 0), new Position(3, 0));
    game = game.makeMove(new Position(6, 1), new Position(4, 1));
    game = game.makeMove(new Position(3, 0), new Position(4, 1));
    assertEquals(Color.BLACK, game.getBoard().getCurrentTurn());
    assertThrows(
        UnallowedMoveException.class, () -> game.makeMove(new Position(7, 7), new Position(3, 1)));
    assertEquals(31, game.getBoard().getPiecesAndPositions().size());
    game = game.makeMove(new Position(6, 6), new Position(5, 6));
    List<Position> rookMoveSet =
        game.getBoard().pieceAt(new Position(0, 0)).getMoveSet(new Position(0, 0), game.getBoard());
    System.out.println(rookMoveSet);
    assertEquals(5, rookMoveSet.size());
    game = game.makeMove(new Position(0, 0), new Position(4, 0));
    System.out.println(game.getBoard());
    assertEquals(PieceType.ROOK, game.getBoard().pieceAt(new Position(4, 0)).getType());
  }

  @Test
  public void validateQueenMovement() throws UnallowedMoveException {
    Piece whiteQueen = board.pieceAt(new Position(0, 3));
    assertEquals(whiteQueen.getType(), PieceType.QUEEN);
    game = game.makeMove(new Position(1, 3), new Position(3, 3));
    List<Position> whiteQueenMoveSet =
        game.getBoard().pieceAt(new Position(0, 3)).getMoveSet(new Position(0, 3), game.getBoard());
    assertEquals(2, whiteQueenMoveSet.size()); // Need to fix it
    game = game.makeMove(new Position(6, 4), new Position(4, 4));
    game = game.makeMove(new Position(3, 3), new Position(4, 4));
    game = game.makeMove(new Position(6, 3), new Position(5, 3));
    System.out.println(game.getBoard());
    assertEquals(31, game.getBoard().getPiecesAndPositions().size());
    whiteQueenMoveSet =
        game.getBoard().pieceAt(new Position(0, 3)).getMoveSet(new Position(0, 3), game.getBoard());
    System.out.println(whiteQueenMoveSet);
    assertEquals(4, whiteQueenMoveSet.size());
  }

  @Test
  public void validateRightCastling() throws UnallowedMoveException {
    assertRightCastling();
  }

  @Test
  public void validateLeftCastling() throws UnallowedMoveException {
    assertLeftCastling();
  }

  private void assertLeftCastling() throws UnallowedMoveException {
    game = game.makeMove(new Position(1, 1), new Position(3, 1));
    assertEquals(Color.BLACK, game.getBoard().getCurrentTurn());
    game = game.makeMove(new Position(6, 1), new Position(4, 1));
    assertEquals(Color.WHITE, game.getBoard().getCurrentTurn());
    game = game.makeMove(new Position(1, 2), new Position(3, 2));
    assertEquals(Color.BLACK, game.getBoard().getCurrentTurn());
    game = game.makeMove(new Position(6, 2), new Position(4, 2));
    assertEquals(Color.WHITE, game.getBoard().getCurrentTurn());
    game = game.makeMove(new Position(1, 3), new Position(3, 3));
    assertEquals(Color.BLACK, game.getBoard().getCurrentTurn());
    game = game.makeMove(new Position(6, 3), new Position(4, 3));
    assertEquals(Color.WHITE, game.getBoard().getCurrentTurn());
    game = game.makeMove(new Position(0, 1), new Position(2, 2));
    assertEquals(Color.BLACK, game.getBoard().getCurrentTurn());
    game = game.makeMove(new Position(6, 6), new Position(5, 6));
    assertEquals(Color.WHITE, game.getBoard().getCurrentTurn());
    game = game.makeMove(new Position(0, 2), new Position(2, 0));
    game = game.makeMove(new Position(6, 0), new Position(4, 0));
    System.out.println(game.getBoard());
    game = game.makeMove(new Position(0, 3), new Position(1, 3));
    game = game.makeMove(new Position(6, 5), new Position(4, 5));
    assertTrue(new Castling().isValidMove(new Position(0, 0), new Position(0, 4), game.getBoard()));
  }

  private void assertRightCastling() throws UnallowedMoveException {
    game = game.makeMove(new Position(1, 5), new Position(3, 5));
    assertEquals(Color.BLACK, game.getBoard().getCurrentTurn());
    game = game.makeMove(new Position(6, 1), new Position(4, 1));
    assertEquals(Color.WHITE, game.getBoard().getCurrentTurn());
    game = game.makeMove(new Position(1, 6), new Position(3, 6));
    assertEquals(Color.BLACK, game.getBoard().getCurrentTurn());
    game = game.makeMove(new Position(6, 2), new Position(4, 2));
    assertEquals(Color.WHITE, game.getBoard().getCurrentTurn());
    game = game.makeMove(new Position(1, 7), new Position(3, 7));
    assertEquals(Color.BLACK, game.getBoard().getCurrentTurn());
    game = game.makeMove(new Position(6, 3), new Position(4, 3));
    assertEquals(Color.WHITE, game.getBoard().getCurrentTurn());
    game = game.makeMove(new Position(0, 6), new Position(2, 5));
    assertEquals(Color.BLACK, game.getBoard().getCurrentTurn());
    game = game.makeMove(new Position(6, 6), new Position(5, 6));
    assertEquals(Color.WHITE, game.getBoard().getCurrentTurn());
    game = game.makeMove(new Position(0, 6), new Position(2, 7));
    game = game.makeMove(new Position(6, 0), new Position(4, 0));
    game = game.makeMove(new Position(0, 5), new Position(1, 6));
    assertTrue(new Castling().isValidMove(new Position(0, 4), new Position(0, 7), game.getBoard()));
  }
}
