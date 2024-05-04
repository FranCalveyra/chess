package edu.austral.dissis.chess.engine.updated.runners;

import static edu.austral.dissis.chess.engine.updated.utils.GameAdapter.mapBoard;
import static edu.austral.dissis.chess.engine.updated.utils.GameAdapter.mapPosition;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
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
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.utils.ChessPosition;
import edu.austral.dissis.chess.utils.GameResult;
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
    GameResult gameResult = game.makeMove(new ChessMove(mapPosition(from), mapPosition(to)));
    game = gameResult.game();
    return getTestMoveResult(gameResult);
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

  private Map<TestPosition, TestPiece> getTestMap(Map<ChessPosition, Piece> pieceMap) {
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

  private @NotNull TestMoveResult getTestMoveResult(GameResult gameResult) {
    switch (gameResult.moveResult()) {
      case INVALID_MOVE:
        return new TestMoveFailure(getBoard());
      case WHITE_WIN:
        return new WhiteCheckMate(getBoard());
      case BLACK_WIN:
        return new BlackCheckMate(getBoard());
      case VALID_MOVE:
      case PIECE_TAKEN:
        return new TestMoveSuccess(this);
      default:
        throw new IllegalArgumentException();
    }
  }
}
