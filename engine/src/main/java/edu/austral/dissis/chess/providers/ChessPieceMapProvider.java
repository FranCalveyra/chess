package edu.austral.dissis.chess.providers;

import edu.austral.dissis.chess.piece.movement.type.ChessPieceType;
import edu.austral.dissis.chess.utils.enums.GameType;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.utils.move.BoardPosition;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ChessPieceMapProvider {
  public Map<BoardPosition, Piece> provide(GameType type, int rows, int columns) {
    Map<BoardPosition, Piece> result = new HashMap<>();
    ChessPieceProvider provider = new ChessPieceProvider();
    if (type == GameType.DEFAULT_CHESS) {
      putDefaultPieces(result, rows, columns, provider);
      return result;
    } else if (type == GameType.CAPABLANCA_CHESS) {
      // Special Pieces
      putCapablancaPieces(result, rows, columns, provider);
      return result;
    } else if (type == GameType.SPECIAL_CHESS) {
      putCapablancaPieces(result, rows, columns, provider);
      List<BoardPosition> positions = new ArrayList<>(result.keySet().stream().toList());
      List<Piece> pieces = new ArrayList<>(result.values().stream().toList());
      Collections.shuffle(positions);
      Collections.shuffle(pieces);
      return IntStream.range(0, positions.size())
          .boxed()
          .collect(Collectors.toMap(positions::get, pieces::get, (a, b) -> b));
    }
    return null;
  }

  private void putDefaultPieces(
      Map<BoardPosition, Piece> result, int rows, int columns, ChessPieceProvider provider) {
    fillSecondRowsWithPawns(result, rows, columns, provider);
    // Rooks
    addRooks(result, rows, columns, provider);
    // Knights
    addKnights(result, rows, columns, provider);
    // Bishops
    addBishops(result, rows, columns, provider);
    // King and Queen
    addKingAndQueen(result, rows, columns, provider);
  }

  private void putCapablancaPieces(
      Map<BoardPosition, Piece> result, int rows, int columns, ChessPieceProvider provider) {
    fillSecondRowsWithPawns(result, rows, columns, provider);
    addRooks(result, rows, columns, provider);
    addKnights(result, rows, columns, provider);
    addKingAndQueen(result, rows, columns - 1, provider);
    addBishops(result, rows, columns - 1, provider);
    result.put(new BoardPosition(0, 2), provider.provide(Color.WHITE, ChessPieceType.ARCHBISHOP));
    result.put(
        new BoardPosition(0, columns - 3),
        provider.provide(Color.WHITE, ChessPieceType.CHANCELLOR));
    result.put(
        new BoardPosition(rows - 1, 2), provider.provide(Color.BLACK, ChessPieceType.ARCHBISHOP));
    result.put(
        new BoardPosition(rows - 1, columns - 3),
        provider.provide(Color.BLACK, ChessPieceType.CHANCELLOR));
    result.put(new BoardPosition(0, 3), provider.provide(Color.WHITE, ChessPieceType.BISHOP));
    result.put(
        new BoardPosition(rows - 1, 3), provider.provide(Color.BLACK, ChessPieceType.BISHOP));
  }

  private static void fillSecondRowsWithPawns(
      Map<BoardPosition, Piece> result, int rows, int columns, ChessPieceProvider provider) {
    for (int j = 0; j < columns; j++) {
      result.put(new BoardPosition(1, j), provider.provide(Color.WHITE, ChessPieceType.PAWN));
      result.put(
          new BoardPosition(rows - 2, j), provider.provide(Color.BLACK, ChessPieceType.PAWN));
    }
  }

  private static void addRooks(
      Map<BoardPosition, Piece> result, int rows, int columns, ChessPieceProvider provider) {
    result.put(new BoardPosition(0, 0), provider.provide(Color.WHITE, ChessPieceType.ROOK));
    result.put(
        new BoardPosition(0, columns - 1), provider.provide(Color.WHITE, ChessPieceType.ROOK));
    result.put(
        new BoardPosition(rows - 1, columns - 1),
        provider.provide(Color.BLACK, ChessPieceType.ROOK));
    result.put(new BoardPosition(rows - 1, 0), provider.provide(Color.BLACK, ChessPieceType.ROOK));
  }

  private static void addKnights(
      Map<BoardPosition, Piece> result, int rows, int columns, ChessPieceProvider provider) {
    result.put(new BoardPosition(0, 1), provider.provide(Color.WHITE, ChessPieceType.KNIGHT));
    result.put(
        new BoardPosition(0, columns - 2), provider.provide(Color.WHITE, ChessPieceType.KNIGHT));
    result.put(
        new BoardPosition(rows - 1, 1), provider.provide(Color.BLACK, ChessPieceType.KNIGHT));
    result.put(
        new BoardPosition(rows - 1, columns - 2),
        provider.provide(Color.BLACK, ChessPieceType.KNIGHT));
  }

  private static void addKingAndQueen(
      Map<BoardPosition, Piece> result, int rows, int columns, ChessPieceProvider provider) {
    addQueens(result, rows, columns, provider);
    addKings(result, rows, columns, provider);
  }

  private static void addKings(
      Map<BoardPosition, Piece> result, int rows, int columns, ChessPieceProvider provider) {
    result.put(
        new BoardPosition(0, columns - 4), provider.provide(Color.WHITE, ChessPieceType.KING));
    result.put(
        new BoardPosition(rows - 1, columns - 4),
        provider.provide(Color.BLACK, ChessPieceType.KING));
  }

  private static void addQueens(
      Map<BoardPosition, Piece> result, int rows, int columns, ChessPieceProvider provider) {
    result.put(
        new BoardPosition(0, columns - 5), provider.provide(Color.WHITE, ChessPieceType.QUEEN));
    result.put(
        new BoardPosition(rows - 1, columns - 5),
        provider.provide(Color.BLACK, ChessPieceType.QUEEN));
  }

  private static void addBishops(
      Map<BoardPosition, Piece> result, int rows, int columns, ChessPieceProvider provider) {
    result.put(new BoardPosition(0, 2), provider.provide(Color.WHITE, ChessPieceType.BISHOP));
    result.put(
        new BoardPosition(0, columns - 3), provider.provide(Color.WHITE, ChessPieceType.BISHOP));
    result.put(
        new BoardPosition(rows - 1, 2), provider.provide(Color.BLACK, ChessPieceType.BISHOP));
    result.put(
        new BoardPosition(rows - 1, columns - 3),
        provider.provide(Color.BLACK, ChessPieceType.BISHOP));
  }
}
