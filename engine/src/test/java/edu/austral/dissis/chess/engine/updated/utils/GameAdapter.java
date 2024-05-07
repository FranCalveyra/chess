package edu.austral.dissis.chess.engine.updated.utils;

import static edu.austral.dissis.common.piece.PieceType.BISHOP;
import static edu.austral.dissis.common.piece.PieceType.KING;
import static edu.austral.dissis.common.piece.PieceType.KNIGHT;
import static edu.austral.dissis.common.piece.PieceType.PAWN;
import static edu.austral.dissis.common.piece.PieceType.QUEEN;
import static edu.austral.dissis.common.piece.PieceType.ROOK;
import static java.awt.Color.BLACK;
import static java.awt.Color.WHITE;

import edu.austral.dissis.chess.providers.ChessPieceProvider;
import edu.austral.dissis.chess.test.TestBoard;
import edu.austral.dissis.chess.test.TestPiece;
import edu.austral.dissis.chess.test.TestPosition;
import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.board.MapBoard;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.piece.PieceType;
import edu.austral.dissis.common.utils.move.BoardPosition;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/** Static Test Classes mapper. */
public class GameAdapter {

  public static Board mapBoard(TestBoard board) {
    int rows = board.getSize().getRows();
    int cols = board.getSize().getCols();
    Map<BoardPosition, Piece> map = mapPieces(board.getPieces());
    return new MapBoard(map, rows, cols);
  }

  public static BoardPosition mapPosition(TestPosition position) {
    return new BoardPosition(position.getRow() - 1, position.getCol() - 1);
  }

  public static Piece mapPiece(TestPiece piece) {
    return new ChessPieceProvider()
        .provide(mapColour(piece.getPlayerColorSymbol()), mapPieceType(piece));
  }

  private static Color mapColour(char c) {
    switch (c) {
      case 'B':
        return BLACK;
      case 'W':
        return WHITE;
      default:
        throw new IllegalStateException("Unexpected value: " + c);
    }
  }

  private static PieceType mapPieceType(TestPiece piece) {
    switch (piece.component1()) {
      case 'K':
        return KING;
      case 'P':
        return PAWN;
      case 'B':
        return BISHOP;
      case 'R':
        return ROOK;
      case 'N':
        return KNIGHT;
      case 'Q':
        return QUEEN;
      default:
        throw new IllegalStateException("Unexpected value: " + piece.component1());
    }
  }

  private static Map<BoardPosition, Piece> mapPieces(Map<TestPosition, TestPiece> map) {
    Map<BoardPosition, Piece> pieceMap = new HashMap<>();
    map.forEach((position, piece) -> pieceMap.put(mapPosition(position), mapPiece(piece)));
    return pieceMap;
  }
}
