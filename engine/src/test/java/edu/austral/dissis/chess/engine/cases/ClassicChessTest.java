package edu.austral.dissis.chess.engine.cases;

import static edu.austral.dissis.common.utils.AuxStaticMethods.makeMove;
import static edu.austral.dissis.common.utils.move.BoardPosition.fromAlgebraic;
import static java.awt.Color.BLACK;
import static java.awt.Color.WHITE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.austral.dissis.chess.piece.movement.type.Castling;
import edu.austral.dissis.chess.piece.movement.type.ChessPieceType;
import edu.austral.dissis.chess.providers.ChessPieceProvider;
import edu.austral.dissis.chess.providers.GameProvider;
import edu.austral.dissis.chess.rules.winconds.CheckMate;
import edu.austral.dissis.chess.rules.winconds.StandardCheck;
import edu.austral.dissis.common.board.MapBoard;
import edu.austral.dissis.common.engine.BoardGame;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.piece.PieceType;
import edu.austral.dissis.common.utils.enums.GameType;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;
import edu.austral.dissis.common.utils.result.gameresult.BoardGameResult;
import edu.austral.dissis.common.utils.result.playresult.GameWon;
import edu.austral.dissis.common.utils.result.playresult.InvalidPlay;
import edu.austral.dissis.common.utils.result.playresult.PromotedPiece;
import edu.austral.dissis.common.utils.result.playresult.ValidPlay;
import java.awt.Color;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ClassicChessTest {
  // Setup
  private BoardGame game = new GameProvider().provide(GameType.DEFAULT_CHESS);

  // Tests
  @Test
  public void foolsMate() {
    final Piece blackPawn = game.getBoard().pieceAt(fromAlgebraic("e7"));
    final Piece whitePawn = game.getBoard().pieceAt(fromAlgebraic("f2"));
    final Piece whitePawn2 = game.getBoard().pieceAt(fromAlgebraic("g2"));
    final Piece blackQueen = game.getBoard().pieceAt(fromAlgebraic("d8"));
    assertEquals(blackPawn.getPieceColour(), Color.BLACK);
    assertEquals(whitePawn.getPieceColour(), WHITE);
    assertEquals(whitePawn2.getPieceColour(), WHITE);
    assertEquals(blackQueen.getPieceColour(), Color.BLACK);
    assertEquals(ChessPieceType.PAWN, blackPawn.getType());
    assertEquals(ChessPieceType.PAWN, whitePawn.getType());
    assertEquals(ChessPieceType.PAWN, whitePawn2.getType());
    assertEquals(ChessPieceType.QUEEN, blackQueen.getType());
    game = makeMove(game, "f2 -> f3").game();
    assertEquals(ChessPieceType.PAWN, game.getBoard().pieceAt(fromAlgebraic("f3")).getType());
    game = makeMove(game, "e7 -> e5").game();
    assertEquals(ChessPieceType.PAWN, game.getBoard().pieceAt(fromAlgebraic("e5")).getType());
    game = makeMove(game, "g2 -> g4").game();
    assertEquals(ChessPieceType.PAWN, game.getBoard().pieceAt(fromAlgebraic("g4")).getType());
    BoardGameResult finishingMove = makeMove(game, "d8 -> h4");
    game = finishingMove.game();
    assertEquals(new GameWon(BLACK), finishingMove.moveResult());
    assertEquals(ChessPieceType.QUEEN, game.getBoard().pieceAt(fromAlgebraic("h4")).getType());
    assertTrue(new StandardCheck(WHITE).isValidRule(game.getBoard()));
    assertFalse(new StandardCheck(Color.BLACK).isValidRule(game.getBoard()));
    assertTrue(new CheckMate(WHITE).isValidRule(game.getBoard()));
  }

  @Test
  public void validatePromotion() {
    assertEquals(WHITE, game.getCurrentTurn());
    BoardGameResult firstResult = makeMove(game, "a2 -> a4");
    game = firstResult.game();
    assertEquals(new ValidPlay(), firstResult.moveResult());
    game = makeMove(game, "a7 -> a5").game();
    assertPositionType(game, ChessPieceType.PAWN, 3, 0);
    assertPositionType(game, ChessPieceType.PAWN, 4, 0);
    game = makeMove(game, "b2 -> b4").game();
    assertPositionType(game, ChessPieceType.PAWN, 3, 1);
    game = makeMove(game, "a8 -> a6").game();
    assertPositionType(game, ChessPieceType.ROOK, 5, 0);

    game = makeMove(game, "b4 -> a5").game();
    assertPositionType(game, ChessPieceType.PAWN, 4, 0);

    game = makeMove(game, "a6 -> a5").game();
    assertPositionType(game, ChessPieceType.ROOK, 4, 0);
    assertEquals(30, game.getBoard().getPiecesAndPositions().size());
    game = makeMove(game, "b1 -> c3").game();
    assertPositionType(game, ChessPieceType.KNIGHT, 2, 2);
    game = makeMove(game, "a5 -> c5").game();
    assertPositionType(game, ChessPieceType.ROOK, 4, 2);
    game = makeMove(game, "a4 -> a5").game();
    assertPositionType(game, ChessPieceType.PAWN, 4, 0);
    game = makeMove(game, "c5 -> c3").game();
    assertPositionType(game, ChessPieceType.ROOK, 2, 2);
    assertEquals(29, game.getBoard().getPiecesAndPositions().size());

    game = makeMove(game, "a5 -> a6").game();
    assertPositionType(game, ChessPieceType.PAWN, 5, 0);
    game = makeMove(game, "c3 -> c5").game();
    assertPositionType(game, ChessPieceType.ROOK, 4, 2);
    game = makeMove(game, "a6 -> a7").game();
    assertPositionType(game, ChessPieceType.PAWN, 6, 0);
    game = makeMove(game, "c5 -> c3").game();
    assertPositionType(game, ChessPieceType.ROOK, 2, 2);
    BoardGameResult lastResult = makeMove(game, "a7 -> a8");
    game = lastResult.game();
    assertEquals(new PromotedPiece(), lastResult.moveResult());
    // Once promoted:
    assertPositionType(game, ChessPieceType.QUEEN, 7, 0);
    assertEquals(WHITE, game.getBoard().pieceAt(fromAlgebraic("a8")).getPieceColour());
  }

  @Test
  public void possibleCheckmateSaving() {
    ChessPieceProvider provider = new ChessPieceProvider();
    Map<BoardPosition, Piece> situation =
        Map.of(
            fromAlgebraic("d1"),
            provider.provide(BLACK, ChessPieceType.QUEEN),
            fromAlgebraic("e1"),
            provider.provide(BLACK, ChessPieceType.KING),
            fromAlgebraic("f1"),
            provider.provide(BLACK, ChessPieceType.BISHOP),
            fromAlgebraic("d2"),
            provider.provide(BLACK, ChessPieceType.QUEEN),
            fromAlgebraic("a5"),
            provider.provide(WHITE, ChessPieceType.QUEEN));
    MapBoard currentBoard = new MapBoard(situation);
    game =
        new BoardGame(
            currentBoard,
            game.getWinConditions(),
            game.getPromoter(),
            game.getTurnSelector(),
            game.getPreMovementValidator());
    assertFalse(new CheckMate(BLACK).isValidRule(game.getBoard()));
    game = makeMove(game, "a5 -> d2").game();
    assertFalse(new CheckMate(BLACK).isValidRule(game.getBoard()));
  }

  @Test
  public void validateKnightMovement() {
    Piece whiteLeftKnight = game.getBoard().pieceAt(fromAlgebraic("b1"));
    assertEquals(whiteLeftKnight.getPieceColour(), WHITE);
    game = makeMove(game, "b1 -> a3").game();
    assertEquals(ChessPieceType.KNIGHT, game.getBoard().pieceAt(fromAlgebraic("a3")).getType());
    game = makeMove(game, "b7 -> b5").game();
    game = makeMove(game, "a3 -> b5").game();
    assertEquals(ChessPieceType.KNIGHT, game.getBoard().pieceAt(fromAlgebraic("b5")).getType());
  }

  @Test
  public void validateBishopMovement() {
    // Initial validations and initializations
    Piece whiteBishop = game.getBoard().pieceAt(fromAlgebraic("c1"));
    assertEquals(whiteBishop.getPieceColour(), WHITE);
    assertEquals(whiteBishop.getType(), ChessPieceType.BISHOP);

    Piece whitePawn = game.getBoard().pieceAt(fromAlgebraic("d2"));
    assertEquals(whitePawn.getPieceColour(), WHITE);
    assertEquals(whitePawn.getType(), ChessPieceType.PAWN);

    Piece blackPawn = game.getBoard().pieceAt(fromAlgebraic("c7"));
    assertEquals(blackPawn.getPieceColour(), Color.BLACK);
    assertEquals(blackPawn.getType(), ChessPieceType.PAWN);
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
    assertEquals(ChessPieceType.PAWN, game.getBoard().pieceAt(fromAlgebraic("a2")).getType());
    game = makeMove(game, "a2 -> a3").game();
    assertEquals(ChessPieceType.PAWN, game.getBoard().pieceAt(fromAlgebraic("a3")).getType());

    Piece blackPawn = game.getBoard().pieceAt(fromAlgebraic("a7"));
    assertEquals(blackPawn.getPieceColour(), Color.BLACK);
    assertEquals(ChessPieceType.PAWN, game.getBoard().pieceAt(fromAlgebraic("a7")).getType());
    game = makeMove(game, "a7 -> a5").game();
    assertEquals(ChessPieceType.PAWN, game.getBoard().pieceAt(fromAlgebraic("a5")).getType());

    Piece otherWhitePawn = game.getBoard().pieceAt(fromAlgebraic("b2"));
    assertEquals(otherWhitePawn.getPieceColour(), WHITE);
    assertEquals(ChessPieceType.PAWN, game.getBoard().pieceAt(fromAlgebraic("b2")).getType());

    game = makeMove(game, "b2 -> b4").game();
    assertEquals(ChessPieceType.PAWN, game.getBoard().pieceAt(fromAlgebraic("b4")).getType());
    assertEquals(Color.BLACK, game.getCurrentTurn());
    game = makeMove(game, "a5 -> b4").game();
    assertEquals(ChessPieceType.PAWN, game.getBoard().pieceAt(fromAlgebraic("b4")).getType());

    Piece newWhitePawn = game.getBoard().pieceAt(fromAlgebraic("e2"));
    assertEquals(newWhitePawn.getPieceColour(), WHITE);
    assertEquals(ChessPieceType.PAWN, game.getBoard().pieceAt(fromAlgebraic("e2")).getType());
    assertEquals(
        getPiecePosition(newWhitePawn, game.getBoard().getPiecesAndPositions()),
        fromAlgebraic("e2"));
    List<BoardPosition> pawnMoveSet = newWhitePawn.getMoveSet(fromAlgebraic("e2"), game.getBoard());
    assertEquals(pawnMoveSet.size(), 2);
  }

  @Test
  public void validatePawnTaking() {
    game = makeMove(game, "b2 -> b4").game();
    game = makeMove(game, "a7 -> a5").game();
    game = makeMove(game, "b4 -> b5").game();
    assertEquals(new InvalidPlay(""), makeMove(game, "b7 -> b5").moveResult());
  }

  @Test
  public void validatePawnFirstMove() {
    ChessPieceProvider provider = new ChessPieceProvider();
    Map<BoardPosition, Piece> situation =
        Map.of(fromAlgebraic("d4"), provider.provide(BLACK, ChessPieceType.PAWN));
    MapBoard currentBoard = new MapBoard(situation);
    BoardGame newGame =
        new BoardGame(
            currentBoard,
            game.getWinConditions(),
            game.getPromoter(),
            game.getTurnSelector().changeTurn(null, currentBoard, new ValidPlay()),
            game.getPreMovementValidator());
    BoardGameResult game1 = makeMove(newGame, "d4 -> d2");
    BoardGameResult game2 = makeMove(newGame, "d4 -> d6");
    assertEquals(new ValidPlay(), game1.moveResult());
    assertEquals(new InvalidPlay(""), game2.moveResult());
  }

  @Test
  public void validateRookMovement() {
    game = makeMove(game, "a2 -> a4").game();
    game = makeMove(game, "b7 -> b5").game();
    game = makeMove(game, "a4 -> b5").game();
    assertEquals(Color.BLACK, game.getCurrentTurn());
    assertEquals(new InvalidPlay(""), makeMove(game, "a8 -> b4").moveResult());
    assertEquals(31, game.getBoard().getPiecesAndPositions().size());
    game = makeMove(game, "g7 -> g6").game();
    List<BoardPosition> rookMoveSet =
        game.getBoard()
            .pieceAt(fromAlgebraic("a1"))
            .getMoveSet(fromAlgebraic("a1"), game.getBoard());
    assertEquals(6, rookMoveSet.size());
    game = makeMove(game, "a1 -> a5").game();
    assertEquals(ChessPieceType.ROOK, game.getBoard().pieceAt(fromAlgebraic("a5")).getType());
  }

  @Test
  public void validateQueenMovement() {
    Piece whiteQueen = game.getBoard().pieceAt(fromAlgebraic("d1"));
    assertEquals(whiteQueen.getType(), ChessPieceType.QUEEN);
    game = makeMove(game, "d2 -> d4").game();
    List<BoardPosition> whiteQueenMoveSet =
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
        new Castling()
            .isValidMove(new GameMove(fromAlgebraic("e1"), fromAlgebraic("c1")), game.getBoard()));
    game = makeMove(game, "e1 -> c1").game();
    assertEquals(ChessPieceType.KING, game.getBoard().pieceAt(fromAlgebraic("c1")).getType());
    assertEquals(ChessPieceType.ROOK, game.getBoard().pieceAt(fromAlgebraic("d1")).getType());
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
        new Castling()
            .isValidMove(new GameMove(fromAlgebraic("e1"), fromAlgebraic("g1")), game.getBoard()));
    game = makeMove(game, "e1 -> g1").game();
    assertEquals(ChessPieceType.KING, game.getBoard().pieceAt(fromAlgebraic("g1")).getType());
    assertEquals(ChessPieceType.ROOK, game.getBoard().pieceAt(fromAlgebraic("f1")).getType());
  }

  // Private stuff
  protected static BoardPosition getPiecePosition(Piece piece, Map<BoardPosition, Piece> pieces) {
    return pieces.entrySet().stream()
        .filter(entry -> entry.getValue() == piece)
        .findFirst()
        .map(Map.Entry::getKey)
        .orElse(null);
  }

  private void assertPositionType(BoardGame currentGame, PieceType pieceType, int i, int j) {
    assertEquals(pieceType, currentGame.getBoard().pieceAt(new BoardPosition(i, j)).getType());
  }
}
