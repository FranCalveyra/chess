package edu.austral.dissis.chess.engine.updated.runners;

import static edu.austral.dissis.chess.engine.ChessGame.createChessGame;
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
import edu.austral.dissis.chess.utils.GameResult;
import edu.austral.dissis.chess.utils.ChessPosition;
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
    return switch (gameResult.message()) {
      case INVALID_MOVE -> new TestMoveFailure(mapTestBoard(game));
      case WHITE_WIN -> new WhiteCheckMate(mapTestBoard(game));
      case BLACK_WIN -> new BlackCheckMate(mapTestBoard(game));
      case VALID_MOVE, PIECE_TAKEN -> new TestMoveSuccess(withBoard(getBoard()));
    };
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
        createChessGame(
            mapBoard(testBoard),
            game.getWinConditions(),
            game.getCheckConditions(),
            game.getPromoter(),
            game.getSelector(),
            game.getCurrentTurn(),
            game.getTurnNumber()));
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
    return switch (pieceType) {
      case KNIGHT -> TestPieceSymbols.KNIGHT;
      case BISHOP -> TestPieceSymbols.BISHOP;
      case ROOK -> TestPieceSymbols.ROOK;
      case QUEEN -> TestPieceSymbols.QUEEN;
      case KING -> TestPieceSymbols.KING;
      case PAWN -> TestPieceSymbols.PAWN;
    };
  }
}
