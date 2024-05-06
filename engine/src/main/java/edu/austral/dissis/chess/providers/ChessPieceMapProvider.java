package edu.austral.dissis.chess.providers;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.utils.move.ChessPosition;
import edu.austral.dissis.chess.utils.type.GameType;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ChessPieceMapProvider {
  public Map<ChessPosition, Piece> provide(GameType type, int rows, int columns) {
    Map<ChessPosition, Piece> result = new HashMap<>();
    PieceProvider provider = new PieceProvider();
    if (type == GameType.DEFAULT) {
      putDefaultPieces(result, rows, columns, provider);
      return result;
    } else if (type == GameType.CAPABLANCA) {
      // Special Pieces
      putCapablancaPieces(result, rows, columns, provider);
      return result;
    } else if (type == GameType.SPECIAL) {
      putCapablancaPieces(result, rows, columns, provider);
      List<ChessPosition> positions = new ArrayList<>(result.keySet().stream().toList());
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
      Map<ChessPosition, Piece> result, int rows, int columns, PieceProvider provider) {
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
      Map<ChessPosition, Piece> result, int rows, int columns, PieceProvider provider) {
    fillSecondRowsWithPawns(result, rows, columns, provider);
    addRooks(result, rows, columns, provider);
    addKnights(result, rows, columns, provider);
    addKingAndQueen(result, rows, columns - 1, provider);
    addBishops(result, rows, columns - 1, provider);
    result.put(new ChessPosition(0, 2), provider.provide(Color.WHITE, PieceType.ARCHBISHOP));
    result.put(
        new ChessPosition(0, columns - 3), provider.provide(Color.WHITE, PieceType.CHANCELLOR));
    result.put(new ChessPosition(rows - 1, 2), provider.provide(Color.BLACK, PieceType.ARCHBISHOP));
    result.put(
        new ChessPosition(rows - 1, columns - 3),
        provider.provide(Color.BLACK, PieceType.CHANCELLOR));
    result.put(new ChessPosition(0, 3), provider.provide(Color.WHITE, PieceType.BISHOP));
    result.put(new ChessPosition(rows - 1, 3), provider.provide(Color.BLACK, PieceType.BISHOP));
  }

  private static void fillSecondRowsWithPawns(
      Map<ChessPosition, Piece> result, int rows, int columns, PieceProvider provider) {
    for (int j = 0; j < columns; j++) {
      result.put(new ChessPosition(1, j), provider.provide(Color.WHITE, PieceType.PAWN));
      result.put(new ChessPosition(rows - 2, j), provider.provide(Color.BLACK, PieceType.PAWN));
    }
  }

  private static void addRooks(
      Map<ChessPosition, Piece> result, int rows, int columns, PieceProvider provider) {
    result.put(new ChessPosition(0, 0), provider.provide(Color.WHITE, PieceType.ROOK));
    result.put(new ChessPosition(0, columns - 1), provider.provide(Color.WHITE, PieceType.ROOK));
    result.put(
        new ChessPosition(rows - 1, columns - 1), provider.provide(Color.BLACK, PieceType.ROOK));
    result.put(new ChessPosition(rows - 1, 0), provider.provide(Color.BLACK, PieceType.ROOK));
  }

  private static void addKnights(
      Map<ChessPosition, Piece> result, int rows, int columns, PieceProvider provider) {
    result.put(new ChessPosition(0, 1), provider.provide(Color.WHITE, PieceType.KNIGHT));
    result.put(new ChessPosition(0, columns - 2), provider.provide(Color.WHITE, PieceType.KNIGHT));
    result.put(new ChessPosition(rows - 1, 1), provider.provide(Color.BLACK, PieceType.KNIGHT));
    result.put(
        new ChessPosition(rows - 1, columns - 2), provider.provide(Color.BLACK, PieceType.KNIGHT));
  }

  private static void addKingAndQueen(
      Map<ChessPosition, Piece> result, int rows, int columns, PieceProvider provider) {
    addQueens(result, rows, columns, provider);
    addKings(result, rows, columns, provider);
  }

  private static void addKings(
      Map<ChessPosition, Piece> result, int rows, int columns, PieceProvider provider) {
    result.put(new ChessPosition(0, columns - 4), provider.provide(Color.WHITE, PieceType.KING));
    result.put(
        new ChessPosition(rows - 1, columns - 4), provider.provide(Color.BLACK, PieceType.KING));
  }

  private static void addQueens(
      Map<ChessPosition, Piece> result, int rows, int columns, PieceProvider provider) {
    result.put(new ChessPosition(0, columns - 5), provider.provide(Color.WHITE, PieceType.QUEEN));
    result.put(
        new ChessPosition(rows - 1, columns - 5), provider.provide(Color.BLACK, PieceType.QUEEN));
  }

  private static void addBishops(
      Map<ChessPosition, Piece> result, int rows, int columns, PieceProvider provider) {
    result.put(new ChessPosition(0, 2), provider.provide(Color.WHITE, PieceType.BISHOP));
    result.put(new ChessPosition(0, columns - 3), provider.provide(Color.WHITE, PieceType.BISHOP));
    result.put(new ChessPosition(rows - 1, 2), provider.provide(Color.BLACK, PieceType.BISHOP));
    result.put(
        new ChessPosition(rows - 1, columns - 3), provider.provide(Color.BLACK, PieceType.BISHOP));
  }
}
