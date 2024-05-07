package edu.austral.dissis.chess.engine.updated.runners;

import static edu.austral.dissis.chess.engine.updated.utils.GameAdapter.mapBoard;
import static edu.austral.dissis.chess.engine.updated.utils.GameAdapter.mapPosition;

import edu.austral.dissis.chess.engine.ChessGame;
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
import edu.austral.dissis.chess.utils.result.CheckState;
import edu.austral.dissis.chess.utils.result.ChessGameResult;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.piece.PieceType;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;
import edu.austral.dissis.common.utils.result.*;
import edu.austral.dissis.common.utils.result.InvalidPlay;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class ChessGameRunner implements TestGameRunner {

  private ChessGame game;

  public ChessGameRunner(ChessGame game) {
    this.game = game;
  }

  @NotNull
  @Override
  public TestMoveResult executeMove(@NotNull TestPosition from, @NotNull TestPosition to) {
    ChessGameResult chessGameResult = game.makeMove(new GameMove(mapPosition(from), mapPosition(to)));
    game = chessGameResult.game();
    return getTestMoveResult(chessGameResult);
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
        new ChessGame(
            mapBoard(testBoard),
            game.getWinConditions(),
            game.getCheckConditions(),
            game.getPromoter(),
            game.getTurnSelector(),
            game.getPreMovementValidator()));
  }

  private TestBoard mapTestBoard(ChessGame game) {
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
    switch (pieceType) {
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
      default:
        throw new IllegalArgumentException();
    }
  }

  private @NotNull TestMoveResult getTestMoveResult(ChessGameResult chessGameResult) {
    PlayResult playResult = chessGameResult.moveResult();
    switch (playResult) {
      case GameWon g:
        return g.getWinner() == Color.BLACK
            ? new BlackCheckMate(getBoard())
            : new WhiteCheckMate(getBoard());
      case InvalidPlay invalidMove:
        return new TestMoveFailure(getBoard());
      case ValidPlay validMove:
        return new TestMoveSuccess(this);
      case CheckState checkState:
        return new TestMoveSuccess(this);
      case PieceTaken pieceTaken:
        return new TestMoveSuccess(this);
      default:
        throw new IllegalStateException();
    }
  }
}
