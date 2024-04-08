package edu.austral.dissis.chess.engine;

import static edu.austral.dissis.chess.engine.ChessTest.getPiecePosition;
import static edu.austral.dissis.chess.engine.ChessTest.updateGame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
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

public class PieceMovementTest {

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

  PieceMovementTest() {
    game = game.startGame();
  }

  @Test
  public void validateKnightMovement() throws UnallowedMoveException {
    Piece whiteLeftKnight = board.pieceAt(new Position(0, 1));
    assertEquals(whiteLeftKnight.getPieceColour(), Color.WHITE);
    game = updateGame(game,whiteLeftKnight, new Position(2, 0));
    assertEquals(new Position(2, 0), getPiecePosition(whiteLeftKnight, game.getBoard().getActivePiecesAndPositions()));
    Piece blackPawn = board.pieceAt(new Position(6, 1));
    game = updateGame(game,blackPawn, new Position(4, 1));

    game = updateGame(game,whiteLeftKnight, new Position(4, 1));
    assertEquals(new Position(4, 1), getPiecePosition(whiteLeftKnight, game.getBoard().getActivePiecesAndPositions()));
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
    game = updateGame(game,whitePawn, new Position(2, 3));
    assertEquals(new Position(2, 3), getPiecePosition(whitePawn, game.getBoard().getActivePiecesAndPositions()));
    // Move black pawn
    game = updateGame(game,blackPawn, new Position(5, 2));
    assertEquals(new Position(5, 2), getPiecePosition(blackPawn, game.getBoard().getActivePiecesAndPositions()));
    // Assert illegal bishop move
    assertThrows(
        UnallowedMoveException.class, () -> game.makeMove(whiteBishop, new Position(2, 3)));
    // Move bishop legally
    game = updateGame(game,whiteBishop, new Position(2, 4));
    assertEquals(new Position(2, 4), getPiecePosition(whiteBishop, game.getBoard().getActivePiecesAndPositions()));
    // Put black pawn in a position where it can be taken
    game = updateGame(game,blackPawn, new Position(4, 2));
    game = updateGame(game,whiteBishop, new Position(4, 2));
    assertFalse(blackPawn.isActiveInBoard());
    assertEquals(31, game.getBoard().getActivePiecesAndPositions().size());
  }

  @Test
  public void validatePawnMovement() throws UnallowedMoveException {
    Piece whitePawn = board.pieceAt(new Position(1, 0));
    assertEquals(whitePawn.getPieceColour(), Color.WHITE);
    assertEquals(getPiecePosition(whitePawn, game.getBoard().getActivePiecesAndPositions()), new Position(1, 0));
    game = updateGame(game,whitePawn, new Position(2, 0));
    assertEquals(new Position(2, 0), getPiecePosition(whitePawn, game.getBoard().getActivePiecesAndPositions()));

    Piece otherWhitePawn = board.pieceAt(new Position(1, 1));
    assertEquals(otherWhitePawn.getPieceColour(), Color.WHITE);
    assertEquals(getPiecePosition(otherWhitePawn, game.getBoard().getActivePiecesAndPositions()), new Position(1, 1));
    game = updateGame(game,otherWhitePawn, new Position(3, 1));
    assertNotEquals(new Position(3, 1), getPiecePosition(otherWhitePawn, game.getBoard().getActivePiecesAndPositions()));

    Piece blackPawn = board.pieceAt(new Position(6, 0));
    assertEquals(blackPawn.getPieceColour(), Color.BLACK);
    assertEquals(getPiecePosition(blackPawn, game.getBoard().getActivePiecesAndPositions()), new Position(6, 0));
    game = updateGame(game,blackPawn, new Position(4, 0));
    assertEquals(new Position(4, 0), getPiecePosition(blackPawn, game.getBoard().getActivePiecesAndPositions()));

    game = updateGame(game,otherWhitePawn, new Position(3, 1));
    assertEquals(new Position(3, 1), getPiecePosition(otherWhitePawn, game.getBoard().getActivePiecesAndPositions()));
    assertEquals(Color.BLACK, game.getBoard().getCurrentTurn());
    game = updateGame(game,blackPawn, new Position(3, 1));
    assertEquals(new Position(3, 1), getPiecePosition(blackPawn, game.getBoard().getActivePiecesAndPositions()));
    assertFalse(otherWhitePawn.isActiveInBoard());

    Piece newWhitePawn = board.pieceAt(new Position(1, 4));
    assertEquals(newWhitePawn.getPieceColour(), Color.WHITE);
    assertEquals(getPiecePosition(newWhitePawn, game.getBoard().getActivePiecesAndPositions()), new Position(1, 4));
    List<Position> pawnMoveSet = newWhitePawn.getMoveSet(new Position(1, 4), game.getBoard());
    assertEquals(pawnMoveSet.size(), 2);
  }
  @Test
  public void validateRookMovement() throws UnallowedMoveException {
    Piece whiteRook = board.pieceAt(new Position(0, 0));
    Piece whitePawn = board.pieceAt(new Position(1, 0));
    Piece blackPawn = board.pieceAt(new Position(6, 1));
    game = updateGame(game,whitePawn, new Position(3,0));
    game = updateGame(game,blackPawn, new Position(4,1));
    game = updateGame(game,whitePawn, new Position(4,1));
    assertEquals(Color.BLACK, game.getBoard().getCurrentTurn());
    assertThrows(UnallowedMoveException.class, () -> updateGame(game,game.getBoard().pieceAt(new Position(7,7)), new Position(3,1)));
    game = updateGame(game,game.getBoard().pieceAt(new Position(6,2)), new Position(5,2));
    game = updateGame(game,whiteRook, new Position(3,0));
    assertEquals(PieceType.ROOK, game.getBoard().pieceAt(new Position(3,0)).getType());
    game = updateGame(game,whiteRook, new Position(3,1));
    System.out.println(game.getBoard());
  }

  @Test
  public void validateQueenMovement() throws UnallowedMoveException {
    Piece whiteQueen = board.pieceAt(new Position(0, 3));
    Piece whitePawn = board.pieceAt(new Position(1, 3));
    Piece blackPawn = board.pieceAt(new Position(6, 4));
    assertEquals(whiteQueen.getType(), PieceType.QUEEN);
    game = updateGame(game,whitePawn, new Position(3,3));
    System.out.println(game.getBoard());
    List<Position> whiteQueenMoveSet = whiteQueen.getMoveSet(new Position(0,3), game.getBoard());
    System.out.println(whiteQueenMoveSet);
    assertEquals(whiteQueenMoveSet.size(), 1);
    game = updateGame(game,blackPawn, new Position(4,4));
    game = updateGame(game,whitePawn, new Position(4,4));
    game = updateGame(game,game.getBoard().pieceAt(new Position(6,3)), new Position(5,3));
  }



}
