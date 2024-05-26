package edu.austral.dissis.chess.engine.updated.runners;

import static edu.austral.dissis.chess.engine.updated.utils.GameAdapter.mapBoard;
import static edu.austral.dissis.chess.engine.updated.utils.GameAdapter.mapPosition;

import edu.austral.dissis.chess.piece.movement.type.ChessPieceType;
import edu.austral.dissis.chess.test.TestBoard;
import edu.austral.dissis.chess.test.TestPiece;
import edu.austral.dissis.chess.test.TestPieceSymbols;
import edu.austral.dissis.chess.test.TestPosition;
import edu.austral.dissis.chess.test.TestSize;
import edu.austral.dissis.chess.test.game.BlackCheckMate;
import edu.austral.dissis.chess.test.game.TestGameRunner;
import edu.austral.dissis.chess.test.game.TestMoveFailure;
import edu.austral.dissis.chess.test.game.TestMoveResult;
import edu.austral.dissis.chess.test.game.TestMoveSuccess;
import edu.austral.dissis.chess.test.game.WhiteCheckMate;
import edu.austral.dissis.common.game.BoardGame;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.piece.PieceType;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;
import edu.austral.dissis.common.utils.result.gameresult.BoardGameResult;
import edu.austral.dissis.common.utils.result.playresult.GameWon;
import edu.austral.dissis.common.utils.result.playresult.InvalidPlay;
import edu.austral.dissis.common.utils.result.playresult.PieceTaken;
import edu.austral.dissis.common.utils.result.playresult.PlayResult;
import edu.austral.dissis.common.utils.result.playresult.PromotedPiece;
import edu.austral.dissis.common.utils.result.playresult.ValidPlay;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import org.jetbrains.annotations.NotNull;

public class ChessGameRunner implements TestGameRunner {
  private final Stack<BoardGame> undo;
  private final Stack<BoardGame> redo;
  private BoardGameResult lastResult;

  private BoardGame game;

  public ChessGameRunner(BoardGame game) {
    this.game = game;
    undo = new Stack<>();
    redo = new Stack<>();
  }

  @NotNull
  @Override
  public TestMoveResult executeMove(@NotNull TestPosition from, @NotNull TestPosition to) {
    BoardGameResult boardGameResult =
        game.makeMove(new GameMove(mapPosition(from), mapPosition(to)));
    if (boardGameResult.moveResult().getClass() != InvalidPlay.class) {
      undo.push(game);
      lastResult = boardGameResult;
      game = boardGameResult.game();
      redo.removeAllElements();
      return getTestMoveResult(lastResult);
    }
    return new TestMoveFailure(getBoard());
  }

  @NotNull
  @Override
  public TestBoard getBoard() {
    return mapTestBoard(game);
  }

  @NotNull
  @Override
  public TestGameRunner withBoard(@NotNull TestBoard testBoard) {
    return new ChessGameRunner(
        new BoardGame(
            mapBoard(testBoard),
            game.getWinConditions(),
            game.getPromoter(),
            game.getTurnSelector(),
            game.getPreMovementValidator()));
  }

  private TestBoard mapTestBoard(BoardGame game) {
    return new TestBoard(
        new TestSize(game.getBoard().getRows(), game.getBoard().getColumns()),
        getTestMap(game.getBoard().getPiecesAndPositions()));
  }

  private Map<TestPosition, TestPiece> getTestMap(Map<BoardPosition, Piece> pieceMap) {
    Map<TestPosition, TestPiece> testMap = new HashMap<>();
    pieceMap.forEach(
        (currentPosition, value) ->
            testMap.put(
                TestPosition.Companion.fromZeroBased(
                    currentPosition.getRow(), currentPosition.getColumn()),
                mapPiece(value)));
    return testMap;
  }

  private TestPiece mapPiece(Piece piece) {
    char color =
        piece.getPieceColour() == Color.BLACK ? TestPieceSymbols.BLACK : TestPieceSymbols.WHITE;
    return new TestPiece(mapPieceType(piece.getType()), color);
  }

  private char mapPieceType(PieceType pieceType) {
    switch ((ChessPieceType) pieceType) {
      case KNIGHT:
        return TestPieceSymbols.KNIGHT;
      case BISHOP:
        return TestPieceSymbols.BISHOP;
      case ROOK:
        return TestPieceSymbols.ROOK;
      case QUEEN:
        return TestPieceSymbols.QUEEN;
      case KING:
        return TestPieceSymbols.KING;
      case PAWN:
        return TestPieceSymbols.PAWN;
      case ARCHBISHOP:
        return TestPieceSymbols.ARCHBISHOP;
      case CHANCELLOR:
        return TestPieceSymbols.CHANCELLOR;
      default:
        throw new IllegalArgumentException();
    }
  }

  private @NotNull TestMoveResult getTestMoveResult(BoardGameResult boardGameResult) {
    if (boardGameResult == null) {
      return new TestMoveFailure(getBoard());
    }
    PlayResult playResult = boardGameResult.moveResult();
    switch (playResult) {
      case GameWon g:
        return g.getWinner() == Color.BLACK
            ? new BlackCheckMate(getBoard())
            : new WhiteCheckMate(getBoard());
      case InvalidPlay invalidMove:
        return new TestMoveFailure(getBoard());
      case ValidPlay validMove:
        return new TestMoveSuccess(this);
      case PieceTaken pieceTaken:
        return new TestMoveSuccess(this);
      case PromotedPiece promotedPiece:
        return new TestMoveSuccess(this);
      default:
        throw new IllegalStateException();
    }
  }

  @NotNull
  @Override
  public TestMoveResult redo() {
    BoardGame redone = redo.pop();
    undo.push(game);
    game = redone;
    return getTestMoveResult(lastResult);
  }

  @NotNull
  @Override
  public TestMoveResult undo() {
    BoardGame undone = undo.pop();
    redo.push(game);
    game = undone;
    return getTestMoveResult(lastResult);
  }
}
