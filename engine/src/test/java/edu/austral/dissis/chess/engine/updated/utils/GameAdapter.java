package edu.austral.dissis.chess.engine.updated.utils;

import static edu.austral.dissis.chess.piece.PieceType.BISHOP;
import static edu.austral.dissis.chess.piece.PieceType.KING;
import static edu.austral.dissis.chess.piece.PieceType.KNIGHT;
import static edu.austral.dissis.chess.piece.PieceType.PAWN;
import static edu.austral.dissis.chess.piece.PieceType.QUEEN;
import static edu.austral.dissis.chess.piece.PieceType.ROOK;
import static java.awt.Color.BLACK;
import static java.awt.Color.WHITE;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.provider.PieceProvider;
import edu.austral.dissis.chess.test.TestBoard;
import edu.austral.dissis.chess.test.TestPiece;
import edu.austral.dissis.chess.test.TestPosition;
import edu.austral.dissis.chess.utils.ChessPosition;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/** Static Test Classes mapper. */
public class GameAdapter {

  public static Board mapBoard(TestBoard board) {
    int rows = board.component1().getRows();
    int cols = board.component1().getCols();
    Map<ChessPosition, Piece> map = mapPieces(board.component2());
    return new Board(map, rows, cols);
  }

  public static ChessPosition mapPosition(TestPosition position) {
    return new ChessPosition(position.getRow() - 1, position.getCol() - 1);
  }

  public static Piece mapPiece(TestPiece piece) {
    return new PieceProvider().get(mapColour(piece.component2()), mapPieceType(piece));
  }

  private static Color mapColour(char c) {
    return switch (c) {
      case 'B' -> BLACK;
      case 'W' -> WHITE;
      default -> throw new IllegalStateException("Unexpected value: " + c);
    };
  }

  private static PieceType mapPieceType(TestPiece piece) {
    return switch (piece.component1()) {
      case 'K' -> KING;
      case 'P' -> PAWN;
      case 'B' -> BISHOP;
      case 'R' -> ROOK;
      case 'N' -> KNIGHT;
      case 'Q' -> QUEEN;
      default -> throw new IllegalStateException("Unexpected value: " + piece.component1());
    };
  }

  private static Map<ChessPosition, Piece> mapPieces(Map<TestPosition, TestPiece> map) {
    Map<ChessPosition, Piece> pieceMap = new HashMap<>();
    for (Map.Entry<TestPosition, TestPiece> entry : map.entrySet()) {
      TestPosition position = entry.getKey();
      TestPiece piece = entry.getValue();
      pieceMap.put(mapPosition(position), mapPiece(piece));
    }
    return pieceMap;
  }
}
