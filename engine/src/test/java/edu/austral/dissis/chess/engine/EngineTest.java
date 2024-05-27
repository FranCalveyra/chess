package edu.austral.dissis.chess.engine;

import static edu.austral.dissis.chess.engine.cases.ClassicChessTest.assertPositionType;
import static edu.austral.dissis.common.utils.AuxStaticMethods.getPiecesList;
import static edu.austral.dissis.common.utils.AuxStaticMethods.getPlayerColor;
import static edu.austral.dissis.common.utils.AuxStaticMethods.makeMove;
import static edu.austral.dissis.common.utils.AuxStaticMethods.mapMove;
import static edu.austral.dissis.common.utils.AuxStaticMethods.moveFromAlgebraic;
import static edu.austral.dissis.common.utils.move.BoardPosition.fromAlgebraic;
import static java.awt.Color.WHITE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import edu.austral.dissis.chess.gui.BoardSize;
import edu.austral.dissis.chess.gui.GameEngine;
import edu.austral.dissis.chess.gui.GameOver;
import edu.austral.dissis.chess.gui.InitialState;
import edu.austral.dissis.chess.gui.InvalidMove;
import edu.austral.dissis.chess.gui.MoveResult;
import edu.austral.dissis.chess.gui.NewGameState;
import edu.austral.dissis.chess.gui.PlayerColor;
import edu.austral.dissis.chess.gui.UndoState;
import edu.austral.dissis.chess.piece.movement.type.ChessPieceType;
import edu.austral.dissis.chess.providers.GameProvider;
import edu.austral.dissis.common.game.BoardGame;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.ui.gameengine.BoardGameEngine;
import edu.austral.dissis.common.utils.enums.GameType;
import edu.austral.dissis.common.utils.result.gameresult.BoardGameResult;
import edu.austral.dissis.common.utils.result.playresult.PromotedPiece;
import edu.austral.dissis.common.utils.result.playresult.ValidPlay;
import java.awt.Color;
import org.junit.jupiter.api.Test;

public class EngineTest {
  BoardGame game = new GameProvider().provide(GameType.DEFAULT_CHESS);
  GameEngine engine = new BoardGameEngine(game);

  @Test
  public void firstMoveShouldBeValid() {
    BoardGame updatedGame = makeMove(game, "a2 -> a4").game();
    MoveResult afterMove = engine.applyMove(mapMove(moveFromAlgebraic("a2 -> a4")));
    MoveResult beforeMove =
        new NewGameState(
            getPiecesList(game),
            getPlayerColor(game.getCurrentTurn()),
            new UndoState(false, false));
    MoveResult undone =
        new NewGameState(
            getPiecesList(game), getPlayerColor(game.getCurrentTurn()), new UndoState(false, true));
    // Assertions
    assertNotEquals(beforeMove, afterMove);
    assertEquals(
        new NewGameState(
            getPiecesList(updatedGame),
            getPlayerColor(updatedGame.getCurrentTurn()),
            new UndoState(true, false)),
        afterMove);
    assertEquals(undone, engine.undo());
    assertEquals(afterMove, engine.redo());
  }

  @Test
  public void initTest() {
    InitialState initialState =
        new InitialState(
            new BoardSize(8, 8), getPiecesList(game), getPlayerColor(game.getCurrentTurn()));
    assertEquals(initialState, engine.init());
  }

  @Test
  public void moveResultTest() {
    // GameOver
    simulateFoolsMate();
    GameEngine gameEngine = new BoardGameEngine(game);
    MoveResult result = gameEngine.applyMove(mapMove(moveFromAlgebraic("d8 -> h4")));
    assertEquals(new GameOver(PlayerColor.BLACK), result);
  }

  @Test
  public void invalidPlayTest() {
    // InvalidPlay
    BoardGame otherGame = new GameProvider().provide(GameType.DEFAULT_CHECKERS);
    engine = new BoardGameEngine(otherGame);
    MoveResult result = engine.applyMove(mapMove(moveFromAlgebraic("h4 -> h10")));
    assertEquals(new InvalidMove("No piece at that position"), result);
  }

  @Test
  public void pieceTakenTest() {
    // PieceTaken
    game = new GameProvider().provide(GameType.CAPABLANCA_CHESS);
    BoardGameResult gameResult = makeMove(game, "c1 -> d3");
    assertEquals(new ValidPlay(), gameResult.moveResult());
    gameResult = makeMove(gameResult.game(), "c8 -> d6");
    assertEquals(new ValidPlay(), gameResult.moveResult());
    gameResult = makeMove(gameResult.game(), "d3 -> b4");
    assertEquals(new ValidPlay(), gameResult.moveResult());

    BoardGame currentGame = gameResult.game();

    engine = new BoardGameEngine(currentGame);
    BoardGameResult result = currentGame.makeMove(moveFromAlgebraic("d6 -> b4"));
    MoveResult pieceTaken = engine.applyMove(mapMove(moveFromAlgebraic("d6 -> b4")));
    assertEquals(
        new NewGameState(
            getPiecesList(result.game()),
            getPlayerColor(result.game().getCurrentTurn()),
            new UndoState(true, false)),
        pieceTaken);
  }

  @Test
  public void promotionTest() {
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

    engine = new BoardGameEngine(game);
    BoardGameResult lastResult = makeMove(game, "a7 -> a8");
    game = lastResult.game();
    assertEquals(new PromotedPiece(), lastResult.moveResult());
    MoveResult result = engine.applyMove(mapMove(moveFromAlgebraic("a7 -> a8")));
    assertEquals(
        new NewGameState(
            getPiecesList(game), getPlayerColor(game.getCurrentTurn()), new UndoState(true, false)),
        result);
    // Once promoted:
    assertPositionType(game, ChessPieceType.QUEEN, 7, 0);
    assertEquals(WHITE, game.getBoard().pieceAt(fromAlgebraic("a8")).getPieceColour());
  }

  private void simulateFoolsMate() {
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
  }
}
