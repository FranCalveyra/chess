package edu.austral.dissis.chess.providers;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.utils.move.ChessPosition;
import edu.austral.dissis.chess.utils.type.GameType;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class ChessPieceMapProvider {
  public Map<ChessPosition, Piece> provide(GameType type, int rows, int columns) {
    Map<ChessPosition, Piece> result = new HashMap<>();
    PieceProvider provider = new PieceProvider();
    if (type == GameType.DEFAULT) {
      putDefaultPieces(result, rows, columns, provider);
      return result;
    } else if (type == GameType.SPECIAL) {
      // Special Pieces
      result.put(new ChessPosition(0, 3), provider.provide(Color.WHITE, PieceType.ARCHBISHOP));
      result.put(new ChessPosition(0, 4), provider.provide(Color.WHITE, PieceType.CHANCELLOR));
      result.put(new ChessPosition(9, 3), provider.provide(Color.BLACK, PieceType.ARCHBISHOP));
      result.put(new ChessPosition(9, 4), provider.provide(Color.BLACK, PieceType.CHANCELLOR));
      putDefaultPieces(result, rows, columns, provider);
      return result;
    }
    return null;
  }

  private void putDefaultPieces(Map<ChessPosition, Piece> result, int rows, int columns, PieceProvider provider) {
    for (int j = 0; j < columns; j++) {
      result.put(new ChessPosition(1, j), provider.provide(Color.WHITE, PieceType.PAWN));
      result.put(new ChessPosition(rows-2, j), provider.provide(Color.BLACK, PieceType.PAWN));
    }
    // Rooks
    result.put(new ChessPosition(0, 0), provider.provide(Color.WHITE, PieceType.ROOK));
    result.put(new ChessPosition(0, columns-1), provider.provide(Color.WHITE, PieceType.ROOK));
    result.put(new ChessPosition(rows-1, columns-1), provider.provide(Color.BLACK, PieceType.ROOK));
    result.put(new ChessPosition(rows-1, 0), provider.provide(Color.BLACK, PieceType.ROOK));
    // Knights
    result.put(new ChessPosition(0, 1), provider.provide(Color.WHITE, PieceType.KNIGHT));
    result.put(new ChessPosition(0, columns-2), provider.provide(Color.WHITE, PieceType.KNIGHT));
    result.put(new ChessPosition(rows-1, 1), provider.provide(Color.BLACK, PieceType.KNIGHT));
    result.put(new ChessPosition(rows-1, columns-2), provider.provide(Color.BLACK, PieceType.KNIGHT));
    // Bishops
    result.put(new ChessPosition(0, 2), provider.provide(Color.WHITE, PieceType.BISHOP));
    result.put(new ChessPosition(0, columns-3), provider.provide(Color.WHITE, PieceType.BISHOP));
    result.put(new ChessPosition(rows-1, 2), provider.provide(Color.BLACK, PieceType.BISHOP));
    result.put(new ChessPosition(rows-1, columns-3), provider.provide(Color.BLACK, PieceType.BISHOP));
    // King and Queen
    result.put(new ChessPosition(0, columns-5), provider.provide(Color.WHITE, PieceType.QUEEN));
    result.put(new ChessPosition(0, columns-4), provider.provide(Color.WHITE, PieceType.KING));
    result.put(new ChessPosition(rows-1, columns-5), provider.provide(Color.BLACK, PieceType.QUEEN));
    result.put(new ChessPosition(rows-1, columns-4), provider.provide(Color.BLACK, PieceType.KING));
  }
}
