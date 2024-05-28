package edu.austral.dissis.chess.engine.updated.utils;

import edu.austral.dissis.chess.piece.movement.type.ChessPieceType;
import edu.austral.dissis.chess.test.TestBoard;
import edu.austral.dissis.chess.test.TestPiece;
import edu.austral.dissis.chess.test.TestPieceSymbols;
import edu.austral.dissis.chess.test.TestPosition;
import edu.austral.dissis.chess.test.TestSize;
import edu.austral.dissis.common.game.BoardGame;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.piece.PieceType;
import edu.austral.dissis.common.utils.move.BoardPosition;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class TestAdapter {
  private TestAdapter() {}

  public static TestBoard mapTestBoard(BoardGame game) {
    return new TestBoard(
        new TestSize(game.getBoard().getRows(), game.getBoard().getColumns()),
        getTestMap(game.getBoard().getPiecesAndPositions()));
  }

  public static Map<TestPosition, TestPiece> getTestMap(Map<BoardPosition, Piece> pieceMap) {
    Map<TestPosition, TestPiece> testMap = new HashMap<>();
    pieceMap.forEach(
        (currentPosition, value) ->
            testMap.put(
                TestPosition.Companion.fromZeroBased(
                    currentPosition.getRow(), currentPosition.getColumn()),
                mapPiece(value)));
    return testMap;
  }

  public static TestPiece mapPiece(Piece piece) {
    char color =
        piece.getPieceColour() == Color.BLACK ? TestPieceSymbols.BLACK : TestPieceSymbols.WHITE;
    return new TestPiece(mapPieceType(piece.getType()), color);
  }

  public static char mapPieceType(PieceType pieceType) {
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
}
